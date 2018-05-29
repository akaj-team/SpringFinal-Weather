package vn.asiantech.android.springfinalweather.kotlin.apiservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.myinterface.IEventWeatherRecyclerView

class ApiServicesRecyclerView {
    private var mIEventWeatherRecyclerView: IEventWeatherRecyclerView

    fun getIEventWeatherRecyclerView(): IEventWeatherRecyclerView {
        return mIEventWeatherRecyclerView
    }

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL1)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        mIEventWeatherRecyclerView = retrofit.create(IEventWeatherRecyclerView::class.java)
    }
}
