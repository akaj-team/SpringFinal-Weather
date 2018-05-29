package vn.asiantech.android.springfinalweather.kotlin.model

import com.google.gson.annotations.SerializedName

class InformationWeather(val data: List<Detail>)

data class Detail(val pod: String,
                  @SerializedName("country_code") val countryCode: String,
                  val clouds: Int,
                  @SerializedName("ts") val timeStamp: Int,
                  @SerializedName("wind_spd") val windSpeed: Float,
                  val datetime: String,
                  @SerializedName("city_name") val cityName: String,
                  @SerializedName("rh") val humidity: Int,
                  val weather: WeatherBean,
                  val temp: Float,
                  val sunset: String,
                  val sunrise: String,
                  @SerializedName("app_temp") val appTemp: Float)

data class WeatherBean(val icon: String,
                       val description: String)
