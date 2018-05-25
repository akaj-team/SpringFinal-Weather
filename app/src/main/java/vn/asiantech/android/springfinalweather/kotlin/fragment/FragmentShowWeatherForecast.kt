package vn.asiantech.android.springfinalweather.kotlin.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
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
import vn.asiantech.android.springfinalweather.kotlin.adapter.RecyclerViewAdapter
import vn.asiantech.android.springfinalweather.kotlin.apiservice.ApiServicesRecyclerView
import vn.asiantech.android.springfinalweather.kotlin.model.InformationWeather
import vn.asiantech.android.springfinalweather.kotlin.model.InformationWeatherRecyclerView
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnCityCollectionChangeListener
import java.text.SimpleDateFormat
import java.util.*

class FragmentShowWeatherForecast : Fragment() {
    private var mSharedPreferences: SharedPreferences? = null
    private lateinit var mTvCurrentDay: TextView
    private lateinit var mTvCountryName: TextView
    private lateinit var mTvTemp: TextView
    private lateinit var mTvSunrise: TextView
    private lateinit var mTvSunset: TextView
    private lateinit var mImgIcon: ImageView
    private lateinit var mTvStatus: TextView
    private lateinit var mTvHumidity: TextView
    private lateinit var mTvCloud: TextView
    private lateinit var mTvWind: TextView
    private lateinit var mRecyclerViewAdapter: RecyclerViewAdapter
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_show_weather_forecast, container, false)
        initViews(view)
        initData()
        return view
    }

    private fun initViews(view: View) {
        mTvCurrentDay = view.findViewById(R.id.tvCurrentDay)
        mTvCountryName = view.findViewById(R.id.tvCountryName)
        mTvTemp = view.findViewById(R.id.tvTemp)
        mTvSunrise = view.findViewById(R.id.tvSunrise)
        mTvSunset = view.findViewById(R.id.tvSunset)
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
                mTvWind.text = getKilometer(bundle.getFloat(Constants.WIND)).toString() + " km/h"
            } else {
                mTvWind.text = bundle.getFloat(Constants.WIND).toString() + " m/s"
            }

            if (mSharedPreferences?.getInt(Constants.UNIT_OF_TEMP, 0) == 0) {
                mTvTemp.text = bundle.getFloat(Constants.TEMP).toString() + "°C"

            } else {
                mTvTemp.text = getFahrenheitDegree(bundle.getFloat(Constants.TEMP)).toString() + "°F"
            }
            mTvCurrentDay.text = bundle.getString(Constants.DATE)
            mTvSunrise.text = bundle.getString(Constants.SUNRISE)
            mTvSunset.text = bundle.getString(Constants.SUNSET)
            mTvCountryName.text = bundle.getString(Constants.CITY_NAME) + ", " + bundle.getString(Constants.COUNTRY_NAME)
            mImgIcon.setImageResource(getIcon(bundle.getString(Constants.ICON)))
            mTvStatus.text = bundle.getString(Constants.DESCRIPTION)
            mTvHumidity.text = bundle.getInt(Constants.HUMIDITY).toString() + "%"
            mTvCloud.text = bundle.getInt(Constants.CLOUD).toString() + "%"
        }
    }

    private fun loadListNextDay(cityName: String) {
        val apiServicesRecyclerView = ApiServicesRecyclerView()
        apiServicesRecyclerView.getIEventWeatherRecyclerView().getInformationWeatherRecyclerView(cityName, Constants.DAYS, Constants.KEY).enqueue(object : Callback<InformationWeatherRecyclerView> {
            override fun onResponse(call: Call<InformationWeatherRecyclerView>?, response: Response<InformationWeatherRecyclerView>?) {
                if (response?.body() != null) {
                    mRecyclerViewAdapter = RecyclerViewAdapter(response.body()!!.data)
                    mRecyclerView.adapter = mRecyclerViewAdapter
                    mRecyclerView.layoutManager = LinearLayoutManager(context)
                    mRecyclerViewAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<InformationWeatherRecyclerView>?, t: Throwable?) {
                Toast.makeText(context, R.string.notification, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun saveNewCityCollection(informationWeather: InformationWeather) {
        val weatherRepository = activity?.applicationContext?.let { WeatherRepository(it) }
        val cityCollection = CityCollection()
        cityCollection.cityName = informationWeather.data[0].cityName
        cityCollection.countryName = informationWeather.data[0].countryCode
        cityCollection.temp = informationWeather.data[0].temp
        cityCollection.appTemp = informationWeather.data[0].appTemp
        cityCollection.humidity = informationWeather.data[0].humidity
        cityCollection.wind = informationWeather.data[0].windSpeed
        cityCollection.cloud = informationWeather.data[0].clouds
        cityCollection.description = informationWeather.data[0].weather.description
        cityCollection.icon = informationWeather.data[0].weather.icon
        weatherRepository?.insert(cityCollection)
    }

    private fun saveNewCityCollection(informationWeather: InformationWeather) {
        val weatherRepository = activity?.applicationContext?.let { WeatherRepository(it) }
        val cityCollection = CityCollection()
        cityCollection.id = informationWeather.id
        cityCollection.name = informationWeather.name
        cityCollection.countryName = informationWeather.sys.country
        cityCollection.temp = informationWeather.main.temp
        cityCollection.tempMax = informationWeather.main.tempMax
        cityCollection.tempMin = informationWeather.main.tempMin
        cityCollection.humidity = informationWeather.main.humidity
        cityCollection.wind = informationWeather.wind.speed
        cityCollection.cloud = informationWeather.clouds.all
        cityCollection.description = informationWeather.weather[0].description
        cityCollection.icon = informationWeather.weather[0].icon
        weatherRepository?.insert(cityCollection)
    }

    @SuppressLint("SetTextI18n")
    private fun showInformationWeather(informationWeather: InformationWeather) {
        mSharedPreferences = activity?.getSharedPreferences(
                getString(R.string.shared_preference_name),
                Context.MODE_PRIVATE)
        if (mSharedPreferences?.getInt(Constants.UNIT_OF_WIND_SPEED, 0) == 0) {
            mTvWind.text = getKilometer(informationWeather.data[0].windSpeed).toString() + " km/h"
        } else {
            mTvWind.text = informationWeather.data[0].windSpeed.toString() + " m/s"
        }

        if (mSharedPreferences?.getInt(Constants.UNIT_OF_TEMP, 0) == 0) {
            mTvTemp.text = informationWeather.data[0].temp.toString() + "°C"

        } else {
            mTvTemp.text = getFahrenheitDegree(informationWeather.data[0].temp).toString() + "°F"
        }

        @SuppressLint("SimpleDateFormat")
        val simpleDateFormat = SimpleDateFormat("EEEE dd-MM-yyyy")
        val day = informationWeather.data[0].timeStamp
        val l = day.toLong()
        val date = Date(l * 1000L)
        val time = simpleDateFormat.format(date)
        mTvCurrentDay.text = time
        mTvSunrise.text = informationWeather.data[0].sunrise
        mTvSunset.text = informationWeather.data[0].sunset
        mTvCountryName.text = informationWeather.data[0].cityName + ", " + informationWeather.data[0].countryCode
        val icon = informationWeather.data[0].weather.icon
        mImgIcon.setImageResource(getIcon(icon))
        mTvStatus.text = informationWeather.data[0].weather.description
        mTvHumidity.text = informationWeather.data[0].humidity.toString() + "%"
        mTvCloud.text = informationWeather.data[0].clouds.toString() + "%"
    }

    private fun getKilometer(speed: Float): Float {
        val convert = 3.6
        val kilometer = speed.times(convert)
        val result = Math.round(kilometer.toFloat().times(10)) / 10.0
        return result.toFloat()
    }

    private fun getFahrenheitDegree(fah: Float): Float {
        val convert = 33.8
        val fahrenheit = fah.times(convert)
        val result = Math.round(fahrenheit.toFloat().times(10)) / 10.0
        return result.toFloat()
    }

    private fun getIcon(icon: String): Int {
        when (icon) {
            Constants.ICON_T01D -> return R.drawable.img_11d
            Constants.ICON_T01N -> return R.drawable.img_11n
            Constants.ICON_T02D -> return R.drawable.img_11d
            Constants.ICON_T02N -> return R.drawable.img_11n
            Constants.ICON_T03D -> return R.drawable.img_11d
            Constants.ICON_T03N -> return R.drawable.img_11n
            Constants.ICON_T04D -> return R.drawable.img_11d
            Constants.ICON_T04N -> return R.drawable.img_11n
            Constants.ICON_T05D -> return R.drawable.img_11d
            Constants.ICON_T05N -> return R.drawable.img_11n
            Constants.ICON_D01D -> return R.drawable.img_09d
            Constants.ICON_D01N -> return R.drawable.img_09n
            Constants.ICON_D02D -> return R.drawable.img_09d
            Constants.ICON_D02N -> return R.drawable.img_09n
            Constants.ICON_D03D -> return R.drawable.img_09d
            Constants.ICON_D03N -> return R.drawable.img_09n
            Constants.ICON_R01D -> return R.drawable.img_10d
            Constants.ICON_R01N -> return R.drawable.img_10n
            Constants.ICON_R02D -> return R.drawable.img_10d
            Constants.ICON_R02N -> return R.drawable.img_10n
            Constants.ICON_R03D -> return R.drawable.img_10d
            Constants.ICON_R03N -> return R.drawable.img_10n
            Constants.ICON_F01D -> return R.drawable.img_13d
            Constants.ICON_F01N -> return R.drawable.img_13n
            Constants.ICON_R04D -> return R.drawable.img_09d
            Constants.ICON_R04N -> return R.drawable.img_09n
            Constants.ICON_R05D -> return R.drawable.img_09d
            Constants.ICON_R05N -> return R.drawable.img_09n
            Constants.ICON_R06D -> return R.drawable.img_09d
            Constants.ICON_R06N -> return R.drawable.img_09n
            Constants.ICON_S01D -> return R.drawable.img_13d
            Constants.ICON_S01N -> return R.drawable.img_13n
            Constants.ICON_S02D -> return R.drawable.img_13d
            Constants.ICON_S02N -> return R.drawable.img_13n
            Constants.ICON_S03D -> return R.drawable.img_13d
            Constants.ICON_S03N -> return R.drawable.img_13n
            Constants.ICON_S04D -> return R.drawable.img_13d
            Constants.ICON_S04N -> return R.drawable.img_13n
            Constants.ICON_S05D -> return R.drawable.img_13d
            Constants.ICON_S05N -> return R.drawable.img_13n
            Constants.ICON_S06D -> return R.drawable.img_13d
            Constants.ICON_S06N -> return R.drawable.img_13n
            Constants.ICON_A01D -> return R.drawable.img_01d
            Constants.ICON_A01N -> return R.drawable.img_01n
            Constants.ICON_A02D -> return R.drawable.img_50d
            Constants.ICON_A02N -> return R.drawable.img_50n
            Constants.ICON_A03D -> return R.drawable.img_50d
            Constants.ICON_A03N -> return R.drawable.img_50n
            Constants.ICON_A04D -> return R.drawable.img_50d
            Constants.ICON_A04N -> return R.drawable.img_50n
            Constants.ICON_A05D -> return R.drawable.img_50d
            Constants.ICON_A05N -> return R.drawable.img_50n
            Constants.ICON_A06D -> return R.drawable.img_50d
            Constants.ICON_A06N -> return R.drawable.img_50n
            Constants.ICON_C01D -> return R.drawable.img_50d
            Constants.ICON_C01N -> return R.drawable.img_50n
            Constants.ICON_C02D -> return R.drawable.img_02d
            Constants.ICON_C02N -> return R.drawable.img_02n
            Constants.ICON_C03D -> return R.drawable.img_03d
            Constants.ICON_C03N -> return R.drawable.img_03n
            Constants.ICON_U00D -> return R.drawable.img_13d
            Constants.ICON_U00N -> return R.drawable.img_13n
            Constants.ICON_C04D -> return R.drawable.img_04d
            Constants.ICON_C04N -> return R.drawable.img_04n
            else -> return R.drawable.img_na
        }
    }
}
