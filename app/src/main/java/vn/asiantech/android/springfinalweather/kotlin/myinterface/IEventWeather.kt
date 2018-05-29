package vn.asiantech.android.springfinalweather.kotlin.myinterface

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import vn.asiantech.android.springfinalweather.kotlin.model.InformationWeather

interface IEventWeather {
    @GET("/v2.0/current")
    fun getInformationWeather(@Query("city") cityName: String, @Query("key") key: String): Call<InformationWeather>
}
