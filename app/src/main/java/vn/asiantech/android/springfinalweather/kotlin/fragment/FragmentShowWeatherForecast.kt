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
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.db.chart.model.LineSet
import com.db.chart.model.Point
import com.db.chart.view.LineChartView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.asiantech.android.springfinalweather.R
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.`object`.Image
import vn.asiantech.android.springfinalweather.kotlin.adapter.RecyclerViewAdapter
import vn.asiantech.android.springfinalweather.kotlin.apiservice.ApiCityService
import vn.asiantech.android.springfinalweather.kotlin.model.CityWeather
import vn.asiantech.android.springfinalweather.kotlin.model.HistoryInformationWeather
import vn.asiantech.android.springfinalweather.kotlin.model.InformationWeatherRecyclerView
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnCityWeatherAsyncListener
import vn.asiantech.android.springfinalweather.kotlin.room.WeatherRepository

class FragmentShowWeatherForecast : Fragment(), OnCityWeatherAsyncListener {

    private var mSharedPreferences: SharedPreferences? = null
    private lateinit var mTvTemp: TextView
    private lateinit var mImgIcon: ImageView
    private lateinit var mTvStatus: TextView
    private lateinit var mTvHumidity: TextView
    private lateinit var mTvCloud: TextView
    private lateinit var mTvWind: TextView
    private lateinit var mRecyclerViewAdapter: RecyclerViewAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLineChartView: LineChartView
    private var mListCityWeather: MutableList<CityWeather> = mutableListOf()
    private lateinit var mCityName: String
    private lateinit var mDate: String
    private var mIsNewData = false
    private lateinit var mDialogLoading: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_show_weather_forecast, container, false)
        initViews(view)
        initData()
        return view
    }

    private fun initViews(view: View) {
        mTvTemp = view.findViewById(R.id.tvTemp)
        mImgIcon = view.findViewById(R.id.imgIcon)
        mTvStatus = view.findViewById(R.id.tvStatus)
        mTvHumidity = view.findViewById(R.id.tvHumidity)
        mTvCloud = view.findViewById(R.id.tvCloud)
        mTvWind = view.findViewById(R.id.tvWind)
        mRecyclerView = view.findViewById(R.id.recyclerView)
        mLineChartView = view.findViewById(R.id.lineChartView)
        mDialogLoading = Dialog(activity, R.style.Dialog)
        mDialogLoading.setContentView(R.layout.dialog_waiting)
        mDialogLoading.setCanceledOnTouchOutside(false)
    }

    @SuppressLint("SetTextI18n")
    private fun initData() {
        val bundle = arguments
        if (bundle != null) {
            mSharedPreferences = activity?.getSharedPreferences(
                    getString(R.string.shared_preference_name),
                    Context.MODE_PRIVATE)
            if (mSharedPreferences?.getInt(Constants.UNIT_OF_WIND_SPEED, 0) == 0) {
                mTvWind.text = bundle.getFloat(Constants.WIND).toString() + " km/h"
            } else {
                mTvWind.text = getMetrePerSecond(bundle.getFloat(Constants.WIND)).toString() + " m/s"
            }
            val unitOfTemp = mSharedPreferences?.getInt(Constants.UNIT_OF_TEMP, 0)
            if (unitOfTemp == 0) {
                mTvTemp.text = bundle.getFloat(Constants.TEMP).toString() + "°C"

            } else {
                mTvTemp.text = getFahrenheitDegree(bundle.getFloat(Constants.TEMP)).toString() + "°F"
            }
            mImgIcon.setImageResource(Image.getImage(
                    bundle.getString(Constants.ICON),
                    bundle.getInt(Constants.IS_DAY)
            ))
            mTvStatus.text = bundle.getString(Constants.DESCRIPTION)
            mTvHumidity.text = bundle.getInt(Constants.HUMIDITY).toString() + "%"
            mTvCloud.text = bundle.getInt(Constants.CLOUD).toString() + "%"
            mCityName = bundle.getString(Constants.CITY_NAME)
            mDialogLoading.show()
            val weatherRepository = activity?.applicationContext?.let { WeatherRepository(it) }
            if (isOnline() && !mIsNewData) {
                mIsNewData = true
                loadListWeatherFourDay(mCityName)
            } else {
                weatherRepository?.getCityWeatherBy(mCityName, this)
            }
            mRecyclerViewAdapter = RecyclerViewAdapter(mListCityWeather, unitOfTemp)
            mRecyclerView.adapter = mRecyclerViewAdapter
            mRecyclerView.layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun reloadRecyclerView() {
        mRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onLoadCityWeatherList(listCityWeather: List<CityWeather>) {
        mDialogLoading.dismiss()
        mListCityWeather.clear()
        listCityWeather.forEach {
            mListCityWeather.add(it)
        }
        reloadRecyclerView()
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
            } else {
                mDate = it.date
                setLineChartTemp(mCityName)
            }
        }
        weatherRepository?.getCityWeatherBy(mCityName, this)
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

    private fun setLineChartTemp(cityName: String) {
        val apiServicesHistoryInformationWeather = ApiCityService()
        apiServicesHistoryInformationWeather.getCityApi().getHistoryWeather(cityName, mDate).enqueue(object : Callback<HistoryInformationWeather> {
            override fun onResponse(call: Call<HistoryInformationWeather>?, response: Response<HistoryInformationWeather>) {
                if (response.isSuccessful) {
                    val dataSet = LineSet()
                    response.body()?.forecast?.forecastDay?.get(0)?.hour?.forEach {
                        dataSet.addPoint(Point(it.time.split(" ")[1].split(":")[0], it.tempC))
                    }
                    dataSet.color = Color.WHITE
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        activity?.resources?.getColor(R.color.colorPurplePastel, context?.theme)?.let { dataSet.setFill(it) }
                        activity?.resources?.getColor(R.color.colorWhite, context?.theme)?.let { mLineChartView.setLabelsColor(it) }
                    }
                    dataSet.thickness = 1f
                    mLineChartView.addData(dataSet)
                    mLineChartView.show()
                }
            }

            override fun onFailure(call: Call<HistoryInformationWeather>?, t: Throwable?) {
                Toast.makeText(context, R.string.notification, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mDialogLoading.dismiss()
    }
}
