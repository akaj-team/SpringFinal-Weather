package vn.asiantech.android.springfinalweather.kotlin.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.NonNull
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants

@Entity(tableName = "CityCollection")
class CityCollection() : Parcelable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "cityname")
    lateinit var cityName: String
    lateinit var countryName: String
    var state = Constants.OTHER_LOCATION
    var temp: Float = 0F
    var appTemp: Float = 0F
    var humidity: Int = 0
    var wind: Float = 0F
    var cloud: Int = 0
    var day: Int = 0
    lateinit var description: String
    var icon: String = "na"
    var date: String = "dd/mm/yy"

    constructor(parcel: Parcel) : this() {
        cityName = parcel.readString()
        countryName = parcel.readString()
        state = parcel.readByte() != 0.toByte()
        temp = parcel.readFloat()
        appTemp = parcel.readFloat()
        humidity = parcel.readInt()
        wind = parcel.readFloat()
        cloud = parcel.readInt()
        day = parcel.readInt()
        description = parcel.readString()
        icon = parcel.readString()
        date = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cityName)
        parcel.writeString(countryName)
        parcel.writeByte(if (state) 1 else 0)
        parcel.writeFloat(temp)
        parcel.writeFloat(appTemp)
        parcel.writeInt(humidity)
        parcel.writeFloat(wind)
        parcel.writeInt(cloud)
        parcel.writeInt(day)
        parcel.writeString(description)
        parcel.writeString(icon)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CityCollection> {
        override fun createFromParcel(parcel: Parcel): CityCollection {
            return CityCollection(parcel)
        }

        override fun newArray(size: Int): Array<CityCollection?> {
            return arrayOfNulls(size)
        }
    }
}
