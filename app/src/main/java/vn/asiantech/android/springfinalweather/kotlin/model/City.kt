package vn.asiantech.android.springfinalweather.kotlin.model

import com.google.gson.annotations.SerializedName

class City {
    lateinit var description: String

    @SerializedName("structured_formatting")
    lateinit var structure: Structure

    class Structure {
        @SerializedName("main_text")
        lateinit var cityName: String
    }
}
