package vn.asiantech.android.springfinalweather.kotlin.dao

import android.arch.persistence.room.*
import vn.asiantech.android.springfinalweather.kotlin.model.CityCollection

@Dao
interface CityCollectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cityCollection: CityCollection)

    @Update
    fun update(cityCollection: CityCollection)

    @Delete
    fun delete(cityCollection: CityCollection)

    @Query("SELECT * FROM CityCollection order by state DESC")
    fun getAllCityCollection(): List<CityCollection>
}
