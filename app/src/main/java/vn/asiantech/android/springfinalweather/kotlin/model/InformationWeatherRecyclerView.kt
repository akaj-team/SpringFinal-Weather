package vn.asiantech.android.springfinalweather.kotlin.model

import com.google.gson.annotations.SerializedName


data class InformationWeatherRecyclerView(val data: List<InformationDetail>)

data class InformationDetail(val datetime: String,
                             val weather: WeatherDetail,
                             @SerializedName("max_temp") val maxTemp: Double,
                             @SerializedName("min_temp") val minTemp: Double
)

data class WeatherDetail(val icon: String,
                         val description: String)
