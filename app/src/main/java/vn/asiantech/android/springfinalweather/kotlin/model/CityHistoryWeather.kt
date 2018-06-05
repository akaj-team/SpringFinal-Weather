package vn.asiantech.android.springfinalweather.kotlin.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index

@Entity(tableName = "CityHistoryWeather",
        foreignKeys = [(ForeignKey(
                entity = CityCollection::class,
                parentColumns = arrayOf("cityname"),
                childColumns = arrayOf("cityname"),
                onDelete = ForeignKey.CASCADE))],
        indices = [(Index(value = arrayOf("cityname", "date", "time"), unique = true))],
        primaryKeys = ["date", "cityname", "time"])
class CityHistoryWeather {
    @ColumnInfo(name = "cityname")
    lateinit var cityName: String
    lateinit var date: String
    @ColumnInfo(name = "tempc")
    var tempC: Float = 0F
    lateinit var time: String
}
