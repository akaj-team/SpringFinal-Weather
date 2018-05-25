package vn.asiantech.android.springfinalweather.kotlin.room

import android.content.Context
import vn.asiantech.android.springfinalweather.kotlin.asynctask.*
import vn.asiantech.android.springfinalweather.kotlin.dao.CityCollectionDao
import vn.asiantech.android.springfinalweather.kotlin.dao.CityWeatherDao
import vn.asiantech.android.springfinalweather.kotlin.model.CityCollection
import vn.asiantech.android.springfinalweather.kotlin.model.CityWeather
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnCityCollectionAsyncListener

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

    fun getAllCityCollection(listener: OnCityCollectionAsyncListener) {
        GetAllCityCollectionAsyncTask(mCityCollectionDao, listener).execute()
    }

    fun insert(cityCollection: CityCollection) {
        InsertCityCollectionAsyncTask(mCityCollectionDao).execute(cityCollection)
    }

    fun delete(cityCollection: CityCollection) {
        DeleteCityCollectionAsyncTask(mCityCollectionDao).execute(cityCollection)
    }

    fun getCityWeatherBy(date: String): List<CityWeather> {
        return mCityWeatherDao.getCityWeatherBy(date)
    }

    fun insert(cityWeather: CityWeather) {
        InsertCityWeatherAsyncTask(mCityWeatherDao).execute(cityWeather)
    }

    fun delete(cityWeather: CityWeather) {
        DeleteCityWeatherAsyncTask(mCityWeatherDao).execute(cityWeather)
    }
}
