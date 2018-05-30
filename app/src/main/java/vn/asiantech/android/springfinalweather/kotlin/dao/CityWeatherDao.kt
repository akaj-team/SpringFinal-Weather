package vn.asiantech.android.springfinalweather.kotlin.dao

import android.arch.persistence.room.*
import vn.asiantech.android.springfinalweather.kotlin.model.CityWeather

@Dao
interface CityWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cityWeather: CityWeather)

    @Update
    fun update(cityWeather: CityWeather)

    @Query("DELETE FROM CityWeather WHERE cityname = :cityName")
    fun deleteBy(cityName: String)

    @Query("SELECT * FROM CityWeather WHERE cityname = :cityName")
    fun getCityWeatherBy(cityName: String): List<CityWeather>
}
