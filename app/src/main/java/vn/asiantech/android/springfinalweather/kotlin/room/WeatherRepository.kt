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
    private lateinit var cityWeatherDao: CityWeatherDao
    private lateinit var cityCollectionDao: CityCollectionDao
    private lateinit var cityHistoryWeatherDao: CityHistoryWeatherDao

    init {
        val weatherDatabase = WeatherDatabaseRoom.getWeatherDatabase(context)
        if (weatherDatabase != null) {
            cityWeatherDao = weatherDatabase.cityWeatherDao()
            cityHistoryWeatherDao = weatherDatabase.cityHistoryWeatherDao()
            cityCollectionDao = weatherDatabase.cityCollectionDao()
        }
    }

    fun getAllCityCollection(listener: OnCityCollectionAsyncListener) {
        GetAllCityCollectionAsyncTask(cityCollectionDao, listener).execute()
    }

    fun insert(cityCollection: CityCollection, listener: OnInsertDoneListener) {
        InsertCityCollectionAsyncTask(cityCollectionDao, listener).execute(cityCollection)
    }

    fun delete(cityCollection: CityCollection) {
        DeleteCityCollectionAsyncTask(cityCollectionDao, cityWeatherDao, cityHistoryWeatherDao).execute(cityCollection)
    }

    fun deleteLocation() {
        DeleteUserLocationAsyncTask(cityCollectionDao).execute()
    }

    fun getCityWeatherBy(cityName: String, listener: OnCityWeatherAsyncListener) {
        GetAllCityWeatherAsyncTask(cityWeatherDao, listener, cityName).execute()
    }

    fun insert(cityWeather: CityWeather) {
        InsertCityWeatherAsyncTask(cityWeatherDao).execute(cityWeather)
    }

    fun deleteBy(cityName: String) {
        DeleteCityWeatherAsyncTask(cityWeatherDao, cityName).execute()
    }

    fun getCityHistoryWeatherBy(cityName: String, listen: OnCityHistoryWeatherAsyncListener) {
        GetAllCityHistoryWeatherAsyncTask(cityHistoryWeatherDao, listen, cityName).execute()
    }

    fun insertHistory(cityHistoryWeather: List<CityHistoryWeather>, listen: OnLoadListHistoryWeather) {
        InsertCityHistoryWeatherAsyncTask(cityHistoryWeatherDao, listen).execute(cityHistoryWeather)
    }

    fun deleteHistoryBy(cityName: String) {
        DeleteCityHistoryWeatherAsyncTask(cityHistoryWeatherDao, cityName).execute()
    }
}
