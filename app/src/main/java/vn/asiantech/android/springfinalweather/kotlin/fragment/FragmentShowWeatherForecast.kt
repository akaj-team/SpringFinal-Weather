package vn.asiantech.android.springfinalweather.kotlin.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
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
import vn.asiantech.android.springfinalweather.kotlin.apiservice.ApiServices
import vn.asiantech.android.springfinalweather.kotlin.model.InformationWeather
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
    }

    private fun initData() {
        val bundle = arguments
        if (bundle != null) {
            loadInformationWeather(bundle.getString(Constants.CITY_NAME))
        }
    }

    private fun loadInformationWeather(cityName: String) {
        val apiServices = ApiServices()
        apiServices.getIEventWeather().getInformationWeather(cityName, Constants.APP_ID).enqueue(object : Callback<InformationWeather> {
            override fun onResponse(call: Call<InformationWeather>, response: Response<InformationWeather>) {
                if (response.body() != null) {
                    showInformationWeather(Objects.requireNonNull<InformationWeather>(response.body()))
                }
            }

            override fun onFailure(call: Call<InformationWeather>, t: Throwable) {
                Toast.makeText(context, R.string.notification, Toast.LENGTH_SHORT).show()
            }
        })
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
            Constants.ICON_01D -> return R.drawable.img_01d
            Constants.ICON_01N -> return R.drawable.img_01n
            Constants.ICON_02D -> return R.drawable.img_02d
            Constants.ICON_02N -> return R.drawable.img_02n
            Constants.ICON_03D -> return R.drawable.img_03d
            Constants.ICON_03N -> return R.drawable.img_03n
            Constants.ICON_04D -> return R.drawable.img_04d
            Constants.ICON_04N -> return R.drawable.img_04n
            Constants.ICON_09D -> return R.drawable.img_09d
            Constants.ICON_09N -> return R.drawable.img_09n
            Constants.ICON_10D -> return R.drawable.img_10d
            Constants.ICON_10N -> return R.drawable.img_10n
            Constants.ICON_11D -> return R.drawable.img_11d
            Constants.ICON_11N -> return R.drawable.img_11n
            Constants.ICON_13D -> return R.drawable.img_13d
            Constants.ICON_13N -> return R.drawable.img_13n
            Constants.ICON_50D -> return R.drawable.img_50d
            Constants.ICON_50N -> return R.drawable.img_50n
            else -> return R.drawable.img_sun
        }
    }
}
