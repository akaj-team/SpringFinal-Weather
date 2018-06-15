package vn.asiantech.android.springfinalweather.kotlin.fragment

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.transition.ChangeBounds
import android.support.transition.ChangeImageTransform
import android.support.transition.TransitionManager
import android.support.transition.TransitionSet
import android.support.v4.app.Fragment
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import com.db.chart.model.LineSet
import com.db.chart.model.Point
import kotlinx.android.synthetic.main.fragment_weather_expand.*
import kotlinx.android.synthetic.main.fragment_weather_expand.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.asiantech.android.springfinalweather.R
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.`object`.Image
import vn.asiantech.android.springfinalweather.kotlin.adapter.RecyclerViewAdapter
import vn.asiantech.android.springfinalweather.kotlin.apiservice.ApiCityService
import vn.asiantech.android.springfinalweather.kotlin.layoutmanager.LinearLayoutDisableScroll
import vn.asiantech.android.springfinalweather.kotlin.model.*
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnCityHistoryWeatherAsyncListener
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnCityWeatherAsyncListener
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnLoadListHistoryWeather
import vn.asiantech.android.springfinalweather.kotlin.room.WeatherRepository
import java.text.DecimalFormat

class FragmentShowWeatherForecast : Fragment(), OnCityWeatherAsyncListener,
        OnCityHistoryWeatherAsyncListener, OnLoadListHistoryWeather {
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var dialogLoading: Dialog
    private lateinit var cityName: String
    private lateinit var date: String
    private lateinit var secondDate: String
    private lateinit var mainView: View
    private var sharedPreferences: SharedPreferences? = null
    private var listCityWeather: MutableList<CityWeather> = mutableListOf()
    private var listCityHistoryWeather: MutableList<CityHistoryWeather> = mutableListOf()
    private var isNewData = false
    private var count = 0
    private var isCollapse = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.fragment_weather_expand, container, false)
        initViews()
        initListener()
        initData()
        return mainView
    }

    private fun initViews() {
        dialogLoading = Dialog(activity, R.style.Dialog)
        dialogLoading.setContentView(R.layout.dialog_waiting)
        dialogLoading.setCanceledOnTouchOutside(false)
        dialogLoading.setCancelable(true)
    }

    private fun initListener() {
        val collapse = ConstraintSet()
        val expanded = ConstraintSet()
        val transition = TransitionSet()
        transition.addTransition(ChangeBounds())
        transition.addTransition(ChangeImageTransform())
        transition.interpolator = AccelerateDecelerateInterpolator()
        collapse.clone(activity, R.layout.fragment_weather_collapse)
        expanded.clone(mainView.clContent)
        val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                if (e2.y > e1.y) {
                    if (isCollapse) {
                        TransitionManager.beginDelayedTransition(clContent, transition)
                        changeTextSize(tvTemp, 35f, 45f)
                        changeTextSize(tvWind, 12f, 15f)
                        changeTextSize(tvCloud, 12f, 15f)
                        changeTextSize(tvHumidity, 12f, 15f)
                        changeTextSize(tvStatus, 16f, 25f)
                        expanded.applyTo(clContent)
                        isCollapse = false
                    }
                } else {
                    if (!isCollapse) {
                        TransitionManager.beginDelayedTransition(clContent, transition)
                        changeTextSize(tvTemp, 45f, 35f)
                        changeTextSize(tvWind, 15f, 12f)
                        changeTextSize(tvCloud, 15f, 12f)
                        changeTextSize(tvHumidity, 15f, 12f)
                        changeTextSize(tvStatus, 25f, 16f)
                        collapse.applyTo(clContent)
                        isCollapse = true
                    }
                }
                return true
            }
        })
        mainView.clContent.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }
    }

    private fun changeTextSize(view: TextView, from: Float, to: Float) {
        ObjectAnimator.ofFloat(view, "textSize", from, to).setDuration(1000).start()
    }

    @SuppressLint("SetTextI18n")
    private fun initData() {
        val bundle = arguments
        if (bundle != null) {
            val cityCollection: CityCollection = bundle.getParcelable(Constants.CITY_COLLECTION)
            sharedPreferences = activity?.getSharedPreferences(
                    getString(R.string.shared_preference_name),
                    Context.MODE_PRIVATE)
            if (sharedPreferences?.getInt(Constants.UNIT_OF_WIND_SPEED, 0) == 0) {
                mainView.tvWind.text = cityCollection.wind.toInt().toString() + " km/h"
            } else {
                mainView.tvWind.text = getMetrePerSecond(cityCollection.wind).toString() + " m/s"
            }
            val unitOfTemp = sharedPreferences?.getInt(Constants.UNIT_OF_TEMP, 0)
            if (unitOfTemp == 0) {
                mainView.tvTemp.text = cityCollection.temp.toInt().toString() + "°C"
            } else {
                mainView.tvTemp.text = getFahrenheitDegree(cityCollection.temp).toString() + "°F"
            }
            mainView.imgIcon.setImageResource(Image.getImage(
                    cityCollection.icon,
                    cityCollection.day
            ))
            mainView.tvStatus.text = cityCollection.description
            mainView.tvHumidity.text = cityCollection.humidity.toString() + "%"
            mainView.tvCloud.text = cityCollection.cloud.toString() + "%"
            cityName = cityCollection.cityName
            date = cityCollection.date
            secondDate = date.split(" ")[0]
            dialogLoading.show()
            val weatherRepository = activity?.applicationContext?.let { WeatherRepository(it) }
            if (isOnline() && !isNewData) {
                isNewData = true
                loadListWeatherFourDay(cityName)
                loadLineChartTemp(cityName)
            } else {
                weatherRepository?.getCityWeatherBy(cityName, this)
                weatherRepository?.getCityHistoryWeatherBy(cityName, this)
            }
            recyclerViewAdapter = RecyclerViewAdapter(listCityWeather, unitOfTemp)
            mainView.recyclerView.adapter = recyclerViewAdapter
            mainView.recyclerView.layoutManager = LinearLayoutDisableScroll(context)
        }
    }

    private fun reloadRecyclerView() {
        recyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onLoadCityWeatherList(listCityWeather: List<CityWeather>) {
        this.listCityWeather.clear()
        listCityWeather.forEach {
            this.listCityWeather.add(it)
        }
        reloadRecyclerView()
        count++
        countDialog()
    }

    override fun onLoadCityHistoryWeatherList(listCityHistoryWeather: List<CityHistoryWeather>) {
        val dataSet = LineSet()
        val decimalFormat = DecimalFormat("0")
        listCityHistoryWeather.forEach {
            val tempC = it.tempC
            val split = it.time.split(" ")[1].split(":")[0][0].toString()
            val time = if (split == "0") {
                it.time.split(" ")[1].split(":")[0][1].toString()
            } else {
                it.time.split(" ")[1].split(":")[0]
            }
            dataSet.addPoint(Point(time, tempC))
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity?.resources?.getColor(R.color.colorWhite, context?.theme)?.let { mainView.lineChartView.setLabelsColor(it) }
        }
        dataSet.color = Color.WHITE
        dataSet.isSmooth = true
        dataSet.thickness = 8f
        dataSet.setDotsRadius(5f)
        dataSet.setGradientFill(intArrayOf(Color.parseColor("#b1adad"), R.color.colorGray), null)
        if (dataSet.size() != 0) {
            mainView.lineChartView.setXAxis(false)
            mainView.lineChartView.setLabelsFormat(decimalFormat)
            mainView.lineChartView.addData(dataSet)
            mainView.lineChartView.show()
        }
        count++
        countDialog()
    }

    private fun loadListWeatherFourDay(cityName: String) {
        val apiServicesRecyclerView = ApiCityService()
        apiServicesRecyclerView.getCityApi().getFiveDayWeather(cityName).enqueue(object : Callback<InformationWeatherRecyclerView> {
            override fun onResponse(call: Call<InformationWeatherRecyclerView>?, response: Response<InformationWeatherRecyclerView>) {
                if (response.isSuccessful) {
                    response.body()?.let { saveNewCityWeather(it) }
                }
            }

            override fun onFailure(call: Call<InformationWeatherRecyclerView>?, t: Throwable?) {
                dialogLoading.dismiss()
            }
        })
    }

    private fun loadLineChartTemp(cityName: String) {
        val apiServicesHistoryInformationWeather = ApiCityService()
        apiServicesHistoryInformationWeather.getCityApi().getHistoryWeather(cityName, secondDate).enqueue(object : Callback<HistoryInformationWeather> {
            override fun onResponse(call: Call<HistoryInformationWeather>?, response: Response<HistoryInformationWeather>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        saveNewCityHistoryWeather(it)
                    }
                }
            }

            override fun onFailure(call: Call<HistoryInformationWeather>?, t: Throwable?) {
                dialogLoading.dismiss()
            }
        })
    }

    private fun saveNewCityWeather(informationWeatherRecyclerView: InformationWeatherRecyclerView) {
        val weatherRepository = activity?.applicationContext?.let { WeatherRepository(it) }
        weatherRepository?.deleteBy(cityName)
        informationWeatherRecyclerView.forecast.forecastDay.forEach {
            if (informationWeatherRecyclerView.forecast.forecastDay.indexOf(it) != 0) {
                val cityWeather = CityWeather()
                cityWeather.cityName = cityName
                cityWeather.date = it.date
                cityWeather.description = it.day.condition.description
                cityWeather.tempMax = it.day.maxTemp
                cityWeather.tempMin = it.day.minTemp
                cityWeather.icon = it.day.condition.icon
                weatherRepository?.insert(cityWeather)
            }
        }
        weatherRepository?.getCityWeatherBy(cityName, this)
    }

    private fun saveNewCityHistoryWeather(historyInformationWeather: HistoryInformationWeather) {
        val weatherRepository = activity?.applicationContext?.let { WeatherRepository(it) }
        weatherRepository?.deleteHistoryBy(cityName)
        listCityHistoryWeather.clear()
        historyInformationWeather.forecast.forecastDay[0].hour.forEach {
            val cityHistoryWeather = CityHistoryWeather()
            cityHistoryWeather.cityName = cityName
            cityHistoryWeather.date = secondDate
            cityHistoryWeather.tempC = it.tempC
            cityHistoryWeather.time = it.time
            listCityHistoryWeather.add(cityHistoryWeather)
        }
        weatherRepository?.insertHistory(listCityHistoryWeather, this)
    }

    override fun onLoadListHistoryWeather() {
        val weatherRepository = activity?.applicationContext?.let { WeatherRepository(it) }
        weatherRepository?.getCityHistoryWeatherBy(cityName, this)
    }

    private fun getMetrePerSecond(speed: Float): Int {
        val metre = speed * 5 / 18
        return metre.toInt()
    }

    private fun getFahrenheitDegree(fah: Float): Int {
        val fahrenheit = fah.times(9).div(5) + 32
        return fahrenheit.toInt()
    }

    private fun isOnline(): Boolean {
        val cm = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val netInfo = cm?.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    private fun countDialog() {
        if (count == 2) {
            dialogLoading.dismiss()
            count = 0
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialogLoading.dismiss()
    }
}
