package vn.asiantech.android.springfinalweather.kotlin.myinterface

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import vn.asiantech.android.springfinalweather.kotlin.model.InformationWeatherRecyclerView

interface IEventWeatherRecyclerView {
    @GET("/v2.0/forecast/daily")
    fun getInformationWeatherRecyclerView(@Query("city") cityName: String, @Query("days") days: String, @Query("key") key: String): Call<InformationWeatherRecyclerView>
}
