package vn.asiantech.android.springfinalweather.kotlin.model

import com.google.gson.annotations.SerializedName


data class InformationWeatherRecyclerView(val data: List<Detail>)

data class Detail(val datetime: String,
                  val weather: WeatherBean,
                  @SerializedName("max_temp") val maxTemp: Double,
                  @SerializedName("min_temp") val minTemp: Double
                  )
data class WeatherBean(val icon: String,
                       val description: String)
