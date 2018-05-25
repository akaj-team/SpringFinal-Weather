package vn.asiantech.android.springfinalweather.kotlin.asynctask

import android.os.AsyncTask
import vn.asiantech.android.springfinalweather.kotlin.dao.CityWeatherDao
import vn.asiantech.android.springfinalweather.kotlin.model.CityWeather

class DeleteCityWeatherAsyncTask(private var cityWeatherDao: CityWeatherDao) : AsyncTask<CityWeather, Unit, Unit>() {
    override fun doInBackground(vararg params: CityWeather?) {
        params[0]?.let { cityWeatherDao.delete(it) }
    }
}
