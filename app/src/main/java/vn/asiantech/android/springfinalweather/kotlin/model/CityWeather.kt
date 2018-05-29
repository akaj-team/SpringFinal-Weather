package vn.asiantech.android.springfinalweather.kotlin.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import vn.asiantech.android.springfinalweather.R

@Entity(tableName = "CityWeather",
        foreignKeys = [(ForeignKey(
                entity = CityCollection::class,
                parentColumns = arrayOf("cityname"),
                childColumns = arrayOf("cityname"),
                onDelete = ForeignKey.CASCADE))],
        primaryKeys = ["cityname", "date", "time"])
class CityWeather {
    @ColumnInfo(name = "cityname")
    lateinit var cityName: String
    lateinit var date: String
    lateinit var time: String
    var temp: Float = 0F
    @ColumnInfo(name = "tempmin")
    var tempMin: Float = 0F
    @ColumnInfo(name = "tempmax")
    var tempMax: Float = 0F
    var humidity: Int = 0
    var wind: Float = 0F
    var cloud = 0
    lateinit var description: String
    var icon: Int = R.drawable.img_na
}
