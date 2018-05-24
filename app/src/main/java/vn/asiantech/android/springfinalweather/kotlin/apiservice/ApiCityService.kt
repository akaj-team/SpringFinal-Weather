package vn.asiantech.android.springfinalweather.kotlin.apiservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.myinterface.CityApi

class ApiCityService {
    private var mRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_API_GOOGLE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getCityApi(): CityApi {
        return mRetrofit.create(CityApi::class.java)
    }
}
