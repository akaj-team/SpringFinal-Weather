package vn.asiantech.android.springfinalweather.kotlin.asynctask

import android.os.AsyncTask
import vn.asiantech.android.springfinalweather.kotlin.dao.CityWeatherDao

class DeleteCityWeatherAsyncTask(
        private var cityWeatherDao: CityWeatherDao,
        private var cityName: String
) : AsyncTask<Unit, Unit, Unit>() {
    override fun doInBackground(vararg params: Unit?) {
        cityWeatherDao.deleteBy(cityName)
    }
}
