package vn.asiantech.android.springfinalweather.kotlin.myinterface

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import vn.asiantech.android.springfinalweather.kotlin.model.InformationtWeather

interface IEventWeather {
    @GET("/data/2.5/weather")
    fun getInformationWeather(@Query("q") cityName: String, @Query("appid") app_id: String): Call<InformationtWeather>
}
