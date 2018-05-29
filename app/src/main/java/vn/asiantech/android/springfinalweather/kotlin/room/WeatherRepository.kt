package vn.asiantech.android.springfinalweather.kotlin.room

import android.content.Context
import vn.asiantech.android.springfinalweather.kotlin.asynctask.DeleteCityCollectionAsyncTask
import vn.asiantech.android.springfinalweather.kotlin.asynctask.DeleteCityWeatherAsyncTask
import vn.asiantech.android.springfinalweather.kotlin.asynctask.InsertCityCollectionAsyncTask
import vn.asiantech.android.springfinalweather.kotlin.asynctask.InsertCityWeatherAsyncTask
import vn.asiantech.android.springfinalweather.kotlin.dao.CityCollectionDao
import vn.asiantech.android.springfinalweather.kotlin.dao.CityWeatherDao
import vn.asiantech.android.springfinalweather.kotlin.model.CityCollection
import vn.asiantech.android.springfinalweather.kotlin.model.CityWeather

class WeatherRepository(context: Context) {
    private lateinit var mCityWeatherDao: CityWeatherDao
    private lateinit var mCityCollectionDao: CityCollectionDao

    init {
        val weatherDatabase = WeatherDatabaseRoom.getWeatherDatabase(context)
        if (weatherDatabase != null) {
            mCityWeatherDao = weatherDatabase.cityWeatherDao()
            mCityCollectionDao = weatherDatabase.cityCollectionDao()
        }
    }

    fun getAllCityCollection(): List<CityCollection> {
        return mCityCollectionDao.getAllCityCollection()
    }

    fun getCityWeatherBy(date: String): List<CityWeather> {
        return mCityWeatherDao.getCityWeatherBy(date)
    }

    fun insert(cityCollection: CityCollection) {
        InsertCityCollectionAsyncTask(mCityCollectionDao).execute(cityCollection)
    }

    fun insert(cityWeather: CityWeather) {
        InsertCityWeatherAsyncTask(mCityWeatherDao).execute(cityWeather)
    }

    fun delete(cityCollection: CityCollection) {
        DeleteCityCollectionAsyncTask(mCityCollectionDao).execute(cityCollection)
    }

    fun delete(cityWeather: CityWeather) {
        DeleteCityWeatherAsyncTask(mCityWeatherDao).execute(cityWeather)
    }
}
