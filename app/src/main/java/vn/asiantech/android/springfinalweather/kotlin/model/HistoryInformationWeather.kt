package vn.asiantech.android.springfinalweather.kotlin.model

import com.google.gson.annotations.SerializedName

class HistoryInformationWeather(
        @SerializedName("forecast") val forecast: HistoryWeatherInformation
)

class HistoryWeatherInformation(
        @SerializedName("forecastday") val forecastDay: List<HistoryInfoWeather>
)

class HistoryInfoWeather(
        @SerializedName("date") val date: String,
        @SerializedName("hour") val hour: List<HistoryInfoWeatherHour>
)

class HistoryInfoWeatherHour(
        @SerializedName("temp_c") val tempC: Float,
        @SerializedName("time") val time: String
)
