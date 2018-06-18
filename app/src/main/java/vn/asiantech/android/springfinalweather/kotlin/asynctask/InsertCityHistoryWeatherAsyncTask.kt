package vn.asiantech.android.springfinalweather.kotlin.asynctask

import android.os.AsyncTask
import vn.asiantech.android.springfinalweather.kotlin.dao.CityHistoryWeatherDao
import vn.asiantech.android.springfinalweather.kotlin.model.CityHistoryWeather
import vn.asiantech.android.springfinalweather.kotlin.interfaces.OnLoadListHistoryWeather

class InsertCityHistoryWeatherAsyncTask(private var cityHistoryWeatherDao: CityHistoryWeatherDao,
                                        private var listener: OnLoadListHistoryWeather) : AsyncTask<List<CityHistoryWeather>, Unit, Unit>() {
    private var check = false

    override fun doInBackground(vararg params: List<CityHistoryWeather>?) {
        params[0]?.let { cityHistoryWeatherDao.insertHistory(it) }
        check = true
    }

    override fun onPostExecute(result: Unit?) {
        if (check) {
            listener.onLoadListHistoryWeather()
        }
    }
}
