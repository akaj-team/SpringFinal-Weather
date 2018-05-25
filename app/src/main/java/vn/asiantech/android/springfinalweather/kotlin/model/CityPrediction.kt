package vn.asiantech.android.springfinalweather.kotlin.model

import com.google.gson.annotations.SerializedName

class CityPrediction {
    @SerializedName("predictions")
    lateinit var listCity: List<City>
}
