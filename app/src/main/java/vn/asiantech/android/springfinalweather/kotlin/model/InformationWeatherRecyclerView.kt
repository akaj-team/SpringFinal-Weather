package vn.asiantech.android.springfinalweather.kotlin.model

import com.google.gson.annotations.SerializedName


class InformationWeatherRecyclerView(val data: List<InformationDetail>)

class InformationDetail(val datetime: String,
                        val weather: WeatherDetail,
                        @SerializedName("max_temp") val maxTemp: Float,
                        @SerializedName("min_temp") val minTemp: Float
)

class WeatherDetail(val icon: String,
                    val description: String
)
