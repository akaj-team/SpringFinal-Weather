package vn.asiantech.android.springfinalweather.kotlin.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants

@Entity(tableName = "CityCollection")
class CityCollection {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "cityname")
    lateinit var cityName: String

    lateinit var countryName: String
    var state = Constants.OTHER_LOCATION
    var temp: Float = 0F
    var appTemp: Float = 0F
    lateinit var sunrise: String
    lateinit var sunset: String
    var humidity: Int = 0
    var wind: Float = 0F
    var cloud: Int = 0
    lateinit var description: String
    var icon: String = "na"
    var date: String = "dd/mm/yy"
}
