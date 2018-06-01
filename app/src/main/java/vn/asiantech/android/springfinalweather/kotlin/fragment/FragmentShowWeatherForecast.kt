package vn.asiantech.android.springfinalweather.kotlin.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.asiantech.android.springfinalweather.R
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.`object`.Image
import vn.asiantech.android.springfinalweather.kotlin.adapter.RecyclerViewAdapter
import vn.asiantech.android.springfinalweather.kotlin.apiservice.ApiCityService
import vn.asiantech.android.springfinalweather.kotlin.model.CityWeather
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
    private var mListCityWeather: MutableList<CityWeather> = mutableListOf()
    private lateinit var mCityName: String
    private var mIsNewData = false

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

            if (mSharedPreferences?.getInt(Constants.UNIT_OF_TEMP, 0) == 0) {
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
            val weatherRepository = activity?.applicationContext?.let { WeatherRepository(it) }
            weatherRepository?.getCityWeatherBy(mCityName, this)
            mRecyclerViewAdapter = RecyclerViewAdapter(mListCityWeather)
            mRecyclerView.adapter = mRecyclerViewAdapter
            mRecyclerView.layoutManager = LinearLayoutManager(activity)
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
        if (isOnline() && !mIsNewData) {
            mIsNewData = true
            loadListWeatherFourDay(mCityName)
        }
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
                Toast.makeText(context, R.string.notification, Toast.LENGTH_SHORT).show()
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
}
