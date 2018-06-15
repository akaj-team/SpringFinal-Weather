package vn.asiantech.android.springfinalweather.kotlin.apiservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.myinterface.CityApi

class ApiCityService {
    private var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_WEATHER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getCityApi(): CityApi {
        return retrofit.create(CityApi::class.java)
    }
}
