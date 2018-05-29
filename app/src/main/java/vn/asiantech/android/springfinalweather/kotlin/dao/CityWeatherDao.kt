package vn.asiantech.android.springfinalweather.kotlin.dao

import android.arch.persistence.room.*
import vn.asiantech.android.springfinalweather.kotlin.model.CityWeather

@Dao
interface CityWeatherDao {
    @Insert
    fun insert(cityWeather: CityWeather)

    @Update
    fun update(cityWeather: CityWeather)

    @Delete
    fun delete(cityWeather: CityWeather)

    @Query("SELECT * FROM CityWeather WHERE date = :date")
    fun getCityWeatherBy(date: String): List<CityWeather>
}
