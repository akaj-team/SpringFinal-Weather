package vn.asiantech.android.springfinalweather.kotlin.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import vn.asiantech.android.springfinalweather.kotlin.dao.CityCollectionDao
import vn.asiantech.android.springfinalweather.kotlin.dao.CityHistoryWeatherDao
import vn.asiantech.android.springfinalweather.kotlin.dao.CityWeatherDao
import vn.asiantech.android.springfinalweather.kotlin.model.CityCollection
import vn.asiantech.android.springfinalweather.kotlin.model.CityHistoryWeather
import vn.asiantech.android.springfinalweather.kotlin.model.CityWeather


@Database(entities = [(CityCollection::class), (CityWeather::class), (CityHistoryWeather::class)], version = 3, exportSchema = false)
abstract class WeatherDatabaseRoom : RoomDatabase() {
    abstract fun cityCollectionDao(): CityCollectionDao
    abstract fun cityWeatherDao(): CityWeatherDao
    abstract fun cityHistoryWeatherDao(): CityHistoryWeatherDao

    companion object {
        private var INSTANCE: WeatherDatabaseRoom? = null

        fun getWeatherDatabase(context: Context): WeatherDatabaseRoom? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        WeatherDatabaseRoom::class.java,
                        "weather.db"
                ).build()
            }
            return INSTANCE
        }
    }
}
