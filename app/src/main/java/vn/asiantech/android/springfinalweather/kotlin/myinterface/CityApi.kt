package vn.asiantech.android.springfinalweather.kotlin.myinterface

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.model.CityPrediction

interface CityApi {
    @GET("/maps/api/place/autocomplete/json")
    fun getCityPrediction(
            @Query("input") input: String,
            @Query("types") types: String = Constants.CITY_TYPES,
            @Query("key") key: String = Constants.GOOGLE_KEY
    ): Call<CityPrediction>
}
