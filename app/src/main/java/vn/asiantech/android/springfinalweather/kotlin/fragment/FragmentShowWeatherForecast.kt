package vn.asiantech.android.springfinalweather.kotlin.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.support.v4.app.Fragment
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.asiantech.android.springfinalweather.R
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.apiservice.ApiServices
import vn.asiantech.android.springfinalweather.kotlin.model.InformationtWeather
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class FragmentShowWeatherForecast : Fragment() {
    private var mSharedPreferences: SharedPreferences? = null
    private lateinit var mTvCurrentDay: TextView
    private lateinit var mTvCountryName: TextView
    private lateinit var mEdtSearch: EditText
    private lateinit var mBtnSearch: Button
    private lateinit var mTvTemp: TextView
    private lateinit var mImgIcon: ImageView
    private lateinit var mTvStatus: TextView
    private lateinit var mTvHumidity: TextView
    private lateinit var mTvCloud: TextView
    private lateinit var mTvWind: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_show_weather_forecast, container, false)
        initViews(view)
        Log.d("zxc", "onCreat: ")
        setListener()
        return view
    }

    private fun initViews(view: View) {
        mTvCurrentDay = view.findViewById(R.id.tvCurrentDay)
        mTvCountryName = view.findViewById(R.id.tvCountryName)
        mEdtSearch = view.findViewById(R.id.edtSearch)
        mBtnSearch = view.findViewById(R.id.btnSearch)
        mTvTemp = view.findViewById(R.id.tvTemp)
        mImgIcon = view.findViewById(R.id.imgIcon)
        mTvStatus = view.findViewById(R.id.tvStatus)
        mTvHumidity = view.findViewById(R.id.tvHumidity)
        mTvCloud = view.findViewById(R.id.tvCloud)
        mTvWind = view.findViewById(R.id.tvWind)
    }

    private fun loadInformationWeather(cityName: String) {
        val apiServices = ApiServices()
        apiServices.getIEventWeather().getInformationWeather(cityName, Constants.APP_ID).enqueue(object : Callback<InformationtWeather> {
            override fun onResponse(call: Call<InformationtWeather>, response: Response<InformationtWeather>) {
                if (response.body() != null) {
                    showInformationWeather(Objects.requireNonNull<InformationtWeather>(response.body()))
                }
            }

            override fun onFailure(call: Call<InformationtWeather>, t: Throwable) {
                Toast.makeText(context, R.string.notification, Toast.LENGTH_SHORT).show()
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun showInformationWeather(informationtWeather: InformationtWeather) {
        val mainBean = informationtWeather.main
        val sysBean = informationtWeather.sys
        val weatherBean = informationtWeather.weather
        val cloudsBean = informationtWeather.clouds
        val windBean = informationtWeather.wind

        mSharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
        Log.d("zxv", mSharedPreferences?.getInt(Constants.UNIT_OF_TEMP, 0).toString())
        if (mSharedPreferences?.getInt(Constants.UNIT_OF_TEMP, 0) == 0) {
            mTvWind.text = getKilomet(windBean?.speed?.toFloat()).toString() + " km/h"
        } else {
            mTvWind.text = windBean?.speed.toString() + " m/s"
        }

        if (mSharedPreferences?.getInt(Constants.UNIT_OF_TEMP, 0) == 0) {
            mTvTemp.text = getCelsiusDegree(mainBean?.temp?.toFloat()).toString() + "°C"
        } else {
            mTvTemp.text = getFahrenheit(mainBean?.temp?.toFloat()).toString() + "°F"
        }

        @SuppressLint("SimpleDateFormat")
        val simpleDateFormat = SimpleDateFormat("EEEE dd-MM-yyyy")
        val day = informationtWeather.dt
        val l = day.toLong()
        val date = Date(l * 1000L)
        val time = simpleDateFormat.format(date)
        mTvCurrentDay.text = time
        mTvCountryName.text = informationtWeather.name + ", " + sysBean?.country
        val icon = weatherBean?.get(0)?.icon
        mImgIcon.setImageResource(getIcon(icon.toString()))
        mTvStatus.text = weatherBean?.get(0)?.main
        mTvHumidity.text = mainBean?.humidity.toString() + "%"
        mTvCloud.text = cloudsBean?.all.toString() + "%"
    }

    private fun getKilomet(speed: Float?): Float? {
        val convert = BigDecimal("3.6")
        val kilometer = convert.setScale(2, BigDecimal.ROUND_HALF_EVEN).toFloat()
        return (speed?.times(kilometer))
    }

    private fun getCelsiusDegree(cel: Float?): Float? {
        val convert = BigDecimal("273.15")
        val celsius = convert.setScale(2, BigDecimal.ROUND_HALF_EVEN).toFloat()
        return (cel?.minus(celsius))
    }

    private fun getFahrenheit(fah: Float?): Float? {
        val convert = BigDecimal("2.05")
        val fahrenheit = convert.setScale(2, BigDecimal.ROUND_HALF_EVEN).toFloat()
        return (fah?.div(fahrenheit))
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

    private fun setListener() {
        mBtnSearch.setOnClickListener { view ->
            if (view.id == R.id.btnSearch) {
                val searchCity = mEdtSearch.text.toString().trim()
                if (!TextUtils.isEmpty(searchCity)) {
                    loadInformationWeather(searchCity)
                }
            }
        }
    }
}
