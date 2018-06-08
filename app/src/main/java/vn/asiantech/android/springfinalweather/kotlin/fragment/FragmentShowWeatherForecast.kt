package vn.asiantech.android.springfinalweather.kotlin.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.db.chart.model.LineSet
import com.db.chart.model.Point
import kotlinx.android.synthetic.main.fragment_show_weather_forecast.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.asiantech.android.springfinalweather.R
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.`object`.Image
import vn.asiantech.android.springfinalweather.kotlin.adapter.RecyclerViewAdapter
import vn.asiantech.android.springfinalweather.kotlin.apiservice.ApiCityService
import vn.asiantech.android.springfinalweather.kotlin.model.*
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnCityHistoryWeatherAsyncListener
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnCityWeatherAsyncListener
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnLoadListHistoryWeather
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnRefreshListener
import vn.asiantech.android.springfinalweather.kotlin.room.WeatherRepository
import java.text.DecimalFormat

class FragmentShowWeatherForecast : Fragment(), OnCityWeatherAsyncListener, OnCityHistoryWeatherAsyncListener, OnLoadListHistoryWeather {

    private lateinit var mRecyclerViewAdapter: RecyclerViewAdapter
    private lateinit var mDialogLoading: Dialog
    private lateinit var mCityName: String
    private lateinit var mDate: String
    private lateinit var mSecondDate: String
    private lateinit var mView: View
    private var mSharedPreferences: SharedPreferences? = null
    private var mListCityWeather: MutableList<CityWeather> = mutableListOf()
    private var mListCityHistoryWeather: MutableList<CityHistoryWeather> = mutableListOf()
    private var mIsNewData = false
    private var mCount = 0
    private var mListener: OnRefreshListener? = null

    fun setListener(listener: OnRefreshListener) {
        mListener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_show_weather_forecast, container, false)
        initViews()
        initListener()
        initData()
        return mView
    }

    private fun initViews() {
        mDialogLoading = Dialog(activity, R.style.Dialog)
        mDialogLoading.setContentView(R.layout.dialog_waiting)
        mDialogLoading.setCanceledOnTouchOutside(false)
        mDialogLoading.setCancelable(true)
    }

    private fun initListener() {
        mView.swipeRefresh.setOnRefreshListener {
            mListener?.onRefresh(mCityName, mView.swipeRefresh)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initData() {
        val bundle = arguments
        if (bundle != null) {
            val cityCollection: CityCollection = bundle.getParcelable(Constants.CITY_COLLECTION)
            mSharedPreferences = activity?.getSharedPreferences(
                    getString(R.string.shared_preference_name),
                    Context.MODE_PRIVATE)
            if (mSharedPreferences?.getInt(Constants.UNIT_OF_WIND_SPEED, 0) == 0) {
                mView.tvWind.text = cityCollection.wind.toString() + " km/h"
            } else {
                mView.tvWind.text = getMetrePerSecond(cityCollection.wind).toString() + " m/s"
            }
            val unitOfTemp = mSharedPreferences?.getInt(Constants.UNIT_OF_TEMP, 0)
            if (unitOfTemp == 0) {
                mView.tvTemp.text = cityCollection.temp.toString() + "°C"

            } else {
                mView.tvTemp.text = getFahrenheitDegree(cityCollection.temp).toString() + "°F"
            }
            mView.imgIcon.setImageResource(Image.getImage(
                    cityCollection.icon,
                    cityCollection.day
            ))
            mView.tvStatus.text = cityCollection.description
            mView.tvHumidity.text = cityCollection.humidity.toString() + "%"
            mView.tvCloud.text = cityCollection.cloud.toString() + "%"
            mCityName = cityCollection.cityName
            mDate = cityCollection.date
            mSecondDate = mDate.split(" ")[0]
            mDialogLoading.show()
            val weatherRepository = activity?.applicationContext?.let { WeatherRepository(it) }
            if (isOnline() && !mIsNewData) {
                mIsNewData = true
                loadListWeatherFourDay(mCityName)
                loadLineChartTemp(mCityName)
            } else {
                weatherRepository?.getCityWeatherBy(mCityName, this)
                weatherRepository?.getCityHistoryWeatherBy(mCityName, this)
            }
            mRecyclerViewAdapter = RecyclerViewAdapter(mListCityWeather, unitOfTemp)
            mView.recyclerView.adapter = mRecyclerViewAdapter
            mView.recyclerView.layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun reloadRecyclerView() {
        mRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onLoadCityWeatherList(listCityWeather: List<CityWeather>) {
        mListCityWeather.clear()
        listCityWeather.forEach {
            mListCityWeather.add(it)
        }
        reloadRecyclerView()
        mCount++
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
            activity?.resources?.getColor(R.color.colorWhite, context?.theme)?.let { mView.lineChartView.setLabelsColor(it) }
        }
        dataSet.color = Color.WHITE
        dataSet.isSmooth = true
        dataSet.thickness = 8f
        dataSet.setDotsRadius(5f)
        dataSet.setGradientFill(intArrayOf(Color.parseColor("#b1adad"), R.color.colorGray), null)
        if (dataSet.size() != 0) {
            mView.lineChartView.setXAxis(false)
            mView.lineChartView.setLabelsFormat(decimalFormat)
            mView.lineChartView.addData(dataSet)
            mView.lineChartView.show()
        }
        mCount++
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
                mDialogLoading.dismiss()
            }
        })
    }

    private fun loadLineChartTemp(cityName: String) {
        val apiServicesHistoryInformationWeather = ApiCityService()
        apiServicesHistoryInformationWeather.getCityApi().getHistoryWeather(cityName, mSecondDate).enqueue(object : Callback<HistoryInformationWeather> {
            override fun onResponse(call: Call<HistoryInformationWeather>?, response: Response<HistoryInformationWeather>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        saveNewCityHistoryWeather(it)
                    }
                }
            }

            override fun onFailure(call: Call<HistoryInformationWeather>?, t: Throwable?) {
                mDialogLoading.dismiss()
            }
        })
    }

    private fun saveNewCityWeather(informationWeatherRecyclerView: InformationWeatherRecyclerView) {
        val weatherRepository = activity?.applicationContext?.let { WeatherRepository(it) }
        weatherRepository?.deleteBy(mCityName)
        informationWeatherRecyclerView.forecast.forecastDay.forEach {
            if (informationWeatherRecyclerView.forecast.forecastDay.indexOf(it) != 0) {
                val cityWeather = CityWeather()
                cityWeather.cityName = mCityName
                cityWeather.date = it.date
                cityWeather.description = it.day.condition.description
                cityWeather.tempMax = it.day.maxTemp
                cityWeather.tempMin = it.day.minTemp
                cityWeather.icon = it.day.condition.icon
                weatherRepository?.insert(cityWeather)
            }
        }
        weatherRepository?.getCityWeatherBy(mCityName, this)
    }

    private fun saveNewCityHistoryWeather(historyInformationWeather: HistoryInformationWeather) {
        val weatherRepository = activity?.applicationContext?.let { WeatherRepository(it) }
        weatherRepository?.deleteHistoryBy(mCityName)
        mListCityHistoryWeather.clear()
        historyInformationWeather.forecast.forecastDay[0].hour.forEach {
            val cityHistoryWeather = CityHistoryWeather()
            cityHistoryWeather.cityName = mCityName
            cityHistoryWeather.date = mSecondDate
            cityHistoryWeather.tempC = it.tempC
            cityHistoryWeather.time = it.time
            mListCityHistoryWeather.add(cityHistoryWeather)
        }
        weatherRepository?.insertHistory(mListCityHistoryWeather, this)
    }

    override fun onLoadListHistoryWeather() {
        val weatherRepository = activity?.applicationContext?.let { WeatherRepository(it) }
        weatherRepository?.getCityHistoryWeatherBy(mCityName, this)
    }

    private fun getMetrePerSecond(speed: Float): Float {
        val metre = speed * 5 / 18
        val result = Math.round(metre.times(10)) / 10.0
        return result.toFloat()
    }

    private fun getFahrenheitDegree(fah: Float): Float {
        val fahrenheit = fah.times(9).div(5) + 32
        val result = Math.round(fahrenheit.times(10)) / 10.0
        return result.toFloat()
    }

    private fun isOnline(): Boolean {
        val cm = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val netInfo = cm?.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    private fun countDialog() {
        if (mCount == 2) {
            mDialogLoading.dismiss()
            mCount = 0
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mDialogLoading.dismiss()
    }
}
