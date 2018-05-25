package vn.asiantech.android.springfinalweather.kotlin.asynctask

import android.database.sqlite.SQLiteConstraintException
import android.os.AsyncTask
import vn.asiantech.android.springfinalweather.kotlin.dao.CityWeatherDao
import vn.asiantech.android.springfinalweather.kotlin.model.CityWeather

class InsertCityWeatherAsyncTask(private var cityWeatherDao: CityWeatherDao) : AsyncTask<CityWeather, Unit, Unit>() {
    override fun doInBackground(vararg params: CityWeather?) {
        try {
            params[0]?.let { cityWeatherDao.insert(it) }
        } catch (e: SQLiteConstraintException) {
            params[0]?.let { cityWeatherDao.update(it) }
        }
    }
}
