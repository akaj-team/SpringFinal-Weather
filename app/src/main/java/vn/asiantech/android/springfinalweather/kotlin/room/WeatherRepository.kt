package vn.asiantech.android.springfinalweather.kotlin.room

import android.content.Context
import vn.asiantech.android.springfinalweather.kotlin.asynctask.*
import vn.asiantech.android.springfinalweather.kotlin.dao.CityCollectionDao
import vn.asiantech.android.springfinalweather.kotlin.dao.CityHistoryWeatherDao
import vn.asiantech.android.springfinalweather.kotlin.dao.CityWeatherDao
import vn.asiantech.android.springfinalweather.kotlin.model.CityCollection
import vn.asiantech.android.springfinalweather.kotlin.model.CityHistoryWeather
import vn.asiantech.android.springfinalweather.kotlin.model.CityWeather
import vn.asiantech.android.springfinalweather.kotlin.myinterface.*

class WeatherRepository(context: Context) {
    private lateinit var mCityWeatherDao: CityWeatherDao
    private lateinit var mCityCollectionDao: CityCollectionDao
    private lateinit var mCityHistoryWeatherDao: CityHistoryWeatherDao

    init {
        val weatherDatabase = WeatherDatabaseRoom.getWeatherDatabase(context)
        if (weatherDatabase != null) {
            mCityWeatherDao = weatherDatabase.cityWeatherDao()
            mCityHistoryWeatherDao = weatherDatabase.cityHistoryWeatherDao()
            mCityCollectionDao = weatherDatabase.cityCollectionDao()
        }
    }

    fun getAllCityCollection(listener: OnCityCollectionAsyncListener) {
        GetAllCityCollectionAsyncTask(mCityCollectionDao, listener).execute()
    }

    fun insert(cityCollection: CityCollection, listener: OnInsertDoneListener) {
        InsertCityCollectionAsyncTask(mCityCollectionDao, listener).execute(cityCollection)
    }

    fun delete(cityCollection: CityCollection) {
        DeleteCityCollectionAsyncTask(mCityCollectionDao, mCityWeatherDao, mCityHistoryWeatherDao).execute(cityCollection)
    }

    fun deleteLocation() {
        DeleteUserLocationAsyncTask(mCityCollectionDao).execute()
    }

    fun getCityWeatherBy(cityName: String, listener: OnCityWeatherAsyncListener) {
        GetAllCityWeatherAsyncTask(mCityWeatherDao, listener, cityName).execute()
    }

    fun insert(cityWeather: CityWeather) {
        InsertCityWeatherAsyncTask(mCityWeatherDao).execute(cityWeather)
    }

    fun deleteBy(cityName: String) {
        DeleteCityWeatherAsyncTask(mCityWeatherDao, cityName).execute()
    }

    fun getCityHistoryWeatherBy(cityName: String, listen: OnCityHistoryWeatherAsyncListener) {
        GetAllCityHistoryWeatherAsyncTask(mCityHistoryWeatherDao, listen, cityName).execute()
    }

    fun insertHistory(cityHistoryWeather: List<CityHistoryWeather>, listen: OnLoadListHistoryWeather) {
        InsertCityHistoryWeatherAsyncTask(mCityHistoryWeatherDao, listen).execute(cityHistoryWeather)
    }

    fun deleteHistoryBy(cityName: String) {
        DeleteCityHistoryWeatherAsyncTask(mCityHistoryWeatherDao, cityName).execute()
    }
}
