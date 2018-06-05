package vn.asiantech.android.springfinalweather.kotlin.dao

import android.arch.persistence.room.*
import vn.asiantech.android.springfinalweather.kotlin.model.CityHistoryWeather

@Dao
interface CityHistoryWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(cityHistoryWeather: List<CityHistoryWeather>)

    @Update
    fun update(cityHistoryWeather: CityHistoryWeather)

    @Query("DELETE FROM CityHistoryWeather where cityname = :cityName")
    fun deleteHistoryBy(cityName: String)

    @Query("SELECT * FROM CityHistoryWeather where cityname = :cityName")
    fun getCityHistoryWeatherBy(cityName: String): List<CityHistoryWeather>
}
