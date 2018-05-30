package vn.asiantech.android.springfinalweather.kotlin.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index

@Entity(tableName = "CityWeather",
        foreignKeys = [(ForeignKey(
                entity = CityCollection::class,
                parentColumns = arrayOf("cityname"),
                childColumns = arrayOf("cityname"),
                onDelete = ForeignKey.CASCADE))],
        indices = [(Index(value = arrayOf("cityname", "date"), unique = true))],
        primaryKeys = ["date", "cityname"])
class CityWeather {
    @ColumnInfo(name = "cityname")
    lateinit var cityName: String
    lateinit var date: String
    var tempMin: Float = 0F
    @ColumnInfo(name = "tempmax")
    var tempMax: Float = 0F
    lateinit var description: String
    var icon: String = "na"
}
