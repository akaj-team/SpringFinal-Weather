package vn.asiantech.android.springfinalweather.kotlin.model

import com.google.gson.annotations.SerializedName

class InformationWeather {


    /**
     * coord : {"lon":105.85,"lat":21.03}
     * weather : [{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03d"}]
     * base : stations
     * main : {"temp":305.15,"pressure":1005,"humidity":66,"temp_min":305.15,"temp_max":305.15}
     * visibility : 10000
     * wind : {"speed":5.7,"deg":130}
     * clouds : {"all":40}
     * dt : 1526893200
     * sys : {"type":1,"id":7980,"message":0.0049,"country":"VN","sunrise":1526854608,"sunset":1526902188}
     * id : 1581130
     * name : Hanoi
     * cod : 200
     */

    var base: String? = null
    var main: MainBean? = null
    var visibility: Int = 0
    var wind: WindBean? = null
    var clouds: CloudsBean? = null
    var dt: Int = 0
    var sys: SysBean? = null
    var id: Int = 0
    var name: String? = null
    var weather: List<WeatherBean>? = null

    class MainBean {
        /**
         * temp : 305.15
         * pressure : 1005
         * humidity : 66
         * temp_min : 305.15
         * temp_max : 305.15
         */

        var temp: Double = 0.toDouble()
        var humidity: Int = 0
        @SerializedName("temp_min")
        var tempMin: Double = 0.toDouble()
        @SerializedName("temp_max")
        var tempMax: Double = 0.toDouble()
    }

    class WindBean {
        /**
         * speed : 5.7
         * deg : 130
         */

        var speed: Double = 0.toDouble()
    }

    class CloudsBean {
        /**
         * all : 40
         */

        var all: Int = 0
    }

    class SysBean {
        /**
         * type : 1
         * id : 7980
         * message : 0.0049
         * country : VN
         * sunrise : 1526854608
         * sunset : 1526902188
         */

        var country: String? = null
    }

    class WeatherBean {
        /**
         * id : 802
         * main : Clouds
         * description : scattered clouds
         * icon : 03d
         */

        var description: String? = null
        var icon: String? = null
    }
}
