package vn.asiantech.android.springfinalweather.kotlin.model

import com.google.gson.annotations.SerializedName


class InformationWeatherRecyclerView(
        @SerializedName("forecast") val forecast: ForecastInformation
)

class ForecastInformation(
        @SerializedName("forecastday") val forecastDay: List<ForecastDayInfo>
)

class ForecastDayInfo(
        @SerializedName("date") val date: String,
        @SerializedName("day") val day: DayWeather
)

class DayWeather(
        @SerializedName("maxtemp_c") val maxTemp: Float,
        @SerializedName("mintemp_c") val minTemp: Float,
        @SerializedName("condition") val condition: ConditionDay
)

class ConditionDay(
        @SerializedName("code") val icon: String,
        @SerializedName("text") val description: String
)
