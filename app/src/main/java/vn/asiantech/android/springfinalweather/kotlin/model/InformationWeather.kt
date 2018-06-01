package vn.asiantech.android.springfinalweather.kotlin.model

import com.google.gson.annotations.SerializedName
import java.util.concurrent.locks.Condition

class InformationWeather(
        @SerializedName("location") val location: LocationWeather,
        @SerializedName("current") val current: CurrentWeather)
class LocationWeather(
        @SerializedName("name") val name: String,
        @SerializedName("country") val country: String
)

class CurrentWeather(
        @SerializedName("is_day") val isDay: Int,
        @SerializedName("cloud") val cloud: Int,
        @SerializedName("last_updated") val date: String,
        @SerializedName("wind_kph") val windSpeed: Float,
        @SerializedName("humidity") val humidity: Int,
        @SerializedName("condition") val condition: ConditionWeather,
        @SerializedName("temp_c") val temp: Float,
        @SerializedName("feelslike_c") val appTemp: Float
)

class ConditionWeather(
        @SerializedName("text") val description: String,
        @SerializedName("code") val icon: String
)
