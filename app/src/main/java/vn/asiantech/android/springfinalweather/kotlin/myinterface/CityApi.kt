package vn.asiantech.android.springfinalweather.kotlin.myinterface

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.model.City
import vn.asiantech.android.springfinalweather.kotlin.model.HistoryInformationWeather
import vn.asiantech.android.springfinalweather.kotlin.model.InformationWeather
import vn.asiantech.android.springfinalweather.kotlin.model.InformationWeatherRecyclerView

interface CityApi {
    @GET("search.json")
    fun getListCity(
            @Query("q") name: String,
            @Query("key") key: String = Constants.KEY
    ): Call<List<City>>

    @GET("current.json")
    fun getCurrentWeather(
            @Query("q") name: String,
            @Query("key") key: String = Constants.KEY
    ): Call<InformationWeather>

    @GET("forecast.json")
    fun getFiveDayWeather(
            @Query("q") name: String,
            @Query("days") day: Int = Constants.DAYS,
            @Query("key") key: String = Constants.KEY
    ): Call<InformationWeatherRecyclerView>

    @GET("history.json")
    fun getHistoryWeather(
            @Query("q") name: String,
            @Query("dt") dateTime: String,
            @Query("key") key: String = Constants.KEY
    ): Call<HistoryInformationWeather>
}
