package vn.asiantech.android.springfinalweather.kotlin.asynctask

import android.os.AsyncTask
import vn.asiantech.android.springfinalweather.kotlin.dao.CityHistoryWeatherDao
import vn.asiantech.android.springfinalweather.kotlin.model.CityHistoryWeather
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnCityHistoryWeatherAsyncListener

class GetAllCityHistoryWeatherAsyncTask(
        private var cityHistoryWeatherDao: CityHistoryWeatherDao,
        private var listener: OnCityHistoryWeatherAsyncListener,
        private var cityName: String
) : AsyncTask<Unit, Unit, List<CityHistoryWeather>>() {
    private var mCheck = false

    override fun doInBackground(vararg params: Unit?): List<CityHistoryWeather> {
        mCheck = true
        return cityHistoryWeatherDao.getCityHistoryWeatherBy(cityName)
    }

    override fun onPostExecute(result: List<CityHistoryWeather>) {
        if (mCheck) {
            listener.onLoadCityHistoryWeatherList(result)
        }
    }
}
