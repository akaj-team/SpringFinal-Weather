package vn.asiantech.android.springfinalweather.kotlin.asynctask

import android.os.AsyncTask
import vn.asiantech.android.springfinalweather.kotlin.dao.CityHistoryWeatherDao

class DeleteCityHistoryWeatherAsyncTask(
        private var cityHistoryWeatherDao: CityHistoryWeatherDao,
        private var cityName: String
) : AsyncTask<Unit, Unit, Unit>() {
    override fun doInBackground(vararg params: Unit?) {
        cityHistoryWeatherDao.deleteHistoryBy(cityName)
    }
}
