package vn.asiantech.android.springfinalweather.kotlin.apiservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.myinterface.IEventWeather

class ApiServices {
    private var mIEventWeather: IEventWeather

    fun getIEventWeather(): IEventWeather {
        return mIEventWeather
    }

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        mIEventWeather = retrofit.create(IEventWeather::class.java)
    }
}
