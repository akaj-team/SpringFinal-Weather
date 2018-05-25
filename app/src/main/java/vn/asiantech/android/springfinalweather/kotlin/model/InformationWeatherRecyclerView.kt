package vn.asiantech.android.springfinalweather.kotlin.model

import com.google.gson.annotations.SerializedName

class InformationWeatherRecyclerView {

    /**
     * cod : 200
     * message : 0.0113
     * cnt : 38
     * list : [{"dt":1527228000,"main":{"temp":291.86,"temp_min":291.86,"temp_max":294.046,"pressure":990.17,"sea_level":1031.14,"grnd_level":990.17,"humidity":89,"temp_kf":-2.19},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10n"}],"clouds":{"all":0},"wind":{"speed":2.21,"deg":162.504},"rain":{"3h":1.45},"sys":{"pod":"n"},"dt_txt":"2018-05-25 06:00:00"},{"dt":1527238800,"main":{"temp":291.26,"temp_min":291.26,"temp_max":292.716,"pressure":989.36,"sea_level":1030.55,"grnd_level":989.36,"humidity":90,"temp_kf":-1.46},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10n"}],"clouds":{"all":0},"wind":{"speed":1.78,"deg":163.508},"rain":{"3h":1.09},"sys":{"pod":"n"},"dt_txt":"2018-05-25 09:00:00"},{"dt":1527249600,"main":{"temp":293.5,"temp_min":293.5,"temp_max":294.229,"pressure":989.5,"sea_level":1030.51,"grnd_level":989.5,"humidity":92,"temp_kf":-0.73},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"clouds":{"all":0},"wind":{"speed":1.53,"deg":163.505},"rain":{},"sys":{"pod":"d"},"dt_txt":"2018-05-25 12:00:00"},{"dt":1527260400,"main":{"temp":299.106,"temp_min":299.106,"temp_max":299.106,"pressure":989.15,"sea_level":1029.87,"grnd_level":989.15,"humidity":74,"temp_kf":0},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"02d"}],"clouds":{"all":8},"wind":{"speed":1.51,"deg":183.002},"rain":{},"sys":{"pod":"d"},"dt_txt":"2018-05-25 15:00:00"},{"dt":1527271200,"main":{"temp":301.226,"temp_min":301.226,"temp_max":301.226,"pressure":987.31,"sea_level":1027.71,"grnd_level":987.31,"humidity":64,"temp_kf":0},"weather":[{"id":801,"main":"Clouds","description":"few clouds","icon":"02d"}],"clouds":{"all":20},"wind":{"speed":1.51,"deg":215.508},"rain":{},"sys":{"pod":"d"},"dt_txt":"2018-05-25 18:00:00"},{"dt":1527282000,"main":{"temp":300.755,"temp_min":300.755,"temp_max":300.755,"pressure":985.12,"sea_level":1025.69,"grnd_level":985.12,"humidity":62,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10d"}],"clouds":{"all":76},"wind":{"speed":1.43,"deg":231.002},"rain":{"3h":0.07},"sys":{"pod":"d"},"dt_txt":"2018-05-25 21:00:00"},{"dt":1527292800,"main":{"temp":297.51,"temp_min":297.51,"temp_max":297.51,"pressure":984.84,"sea_level":1025.38,"grnd_level":984.84,"humidity":77,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10n"}],"clouds":{"all":56},"wind":{"speed":1.36,"deg":190.004},"rain":{"3h":1.08},"sys":{"pod":"n"},"dt_txt":"2018-05-26 00:00:00"},{"dt":1527303600,"main":{"temp":294.962,"temp_min":294.962,"temp_max":294.962,"pressure":985.04,"sea_level":1025.83,"grnd_level":985.04,"humidity":83,"temp_kf":0},"weather":[{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03n"}],"clouds":{"all":36},"wind":{"speed":1.5,"deg":169.503},"rain":{},"sys":{"pod":"n"},"dt_txt":"2018-05-26 03:00:00"},{"dt":1527314400,"main":{"temp":294.065,"temp_min":294.065,"temp_max":294.065,"pressure":984.57,"sea_level":1025.3,"grnd_level":984.57,"humidity":86,"temp_kf":0},"weather":[{"id":801,"main":"Clouds","description":"few clouds","icon":"02n"}],"clouds":{"all":24},"wind":{"speed":1.87,"deg":213.5},"rain":{},"sys":{"pod":"n"},"dt_txt":"2018-05-26 06:00:00"},{"dt":1527325200,"main":{"temp":293.686,"temp_min":293.686,"temp_max":293.686,"pressure":984.49,"sea_level":1025.3,"grnd_level":984.49,"humidity":86,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04n"}],"clouds":{"all":68},"wind":{"speed":1.81,"deg":216.504},"rain":{},"sys":{"pod":"n"},"dt_txt":"2018-05-26 09:00:00"},{"dt":1527336000,"main":{"temp":294.628,"temp_min":294.628,"temp_max":294.628,"pressure":984.7,"sea_level":1025.4,"grnd_level":984.7,"humidity":86,"temp_kf":0},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"clouds":{"all":0},"wind":{"speed":1.71,"deg":216.005},"rain":{},"sys":{"pod":"d"},"dt_txt":"2018-05-26 12:00:00"},{"dt":1527346800,"main":{"temp":299.407,"temp_min":299.407,"temp_max":299.407,"pressure":984.19,"sea_level":1024.65,"grnd_level":984.19,"humidity":74,"temp_kf":0},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"clouds":{"all":0},"wind":{"speed":1.52,"deg":226.503},"rain":{},"sys":{"pod":"d"},"dt_txt":"2018-05-26 15:00:00"},{"dt":1527357600,"main":{"temp":302.199,"temp_min":302.199,"temp_max":302.199,"pressure":983.24,"sea_level":1023.5,"grnd_level":983.24,"humidity":59,"temp_kf":0},"weather":[{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03d"}],"clouds":{"all":32},"wind":{"speed":1.51,"deg":238.001},"rain":{},"sys":{"pod":"d"},"dt_txt":"2018-05-26 18:00:00"},{"dt":1527368400,"main":{"temp":301.841,"temp_min":301.841,"temp_max":301.841,"pressure":981.65,"sea_level":1021.94,"grnd_level":981.65,"humidity":57,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}],"clouds":{"all":68},"wind":{"speed":1.46,"deg":236.51},"rain":{},"sys":{"pod":"d"},"dt_txt":"2018-05-26 21:00:00"},{"dt":1527379200,"main":{"temp":299.585,"temp_min":299.585,"temp_max":299.585,"pressure":982.03,"sea_level":1022.49,"grnd_level":982.03,"humidity":69,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04n"}],"clouds":{"all":64},"wind":{"speed":1.47,"deg":188.504},"rain":{},"sys":{"pod":"n"},"dt_txt":"2018-05-27 00:00:00"},{"dt":1527390000,"main":{"temp":296.557,"temp_min":296.557,"temp_max":296.557,"pressure":983.06,"sea_level":1023.66,"grnd_level":983.06,"humidity":76,"temp_kf":0},"weather":[{"id":801,"main":"Clouds","description":"few clouds","icon":"02n"}],"clouds":{"all":20},"wind":{"speed":2.12,"deg":200.508},"rain":{},"sys":{"pod":"n"},"dt_txt":"2018-05-27 03:00:00"},{"dt":1527400800,"main":{"temp":294.754,"temp_min":294.754,"temp_max":294.754,"pressure":983.44,"sea_level":1024.21,"grnd_level":983.44,"humidity":87,"temp_kf":0},"weather":[{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03n"}],"clouds":{"all":44},"wind":{"speed":1.51,"deg":199.01},"rain":{},"sys":{"pod":"n"},"dt_txt":"2018-05-27 06:00:00"},{"dt":1527411600,"main":{"temp":294.259,"temp_min":294.259,"temp_max":294.259,"pressure":983.63,"sea_level":1024.41,"grnd_level":983.63,"humidity":89,"temp_kf":0},"weather":[{"id":801,"main":"Clouds","description":"few clouds","icon":"02n"}],"clouds":{"all":24},"wind":{"speed":2.01,"deg":196.503},"rain":{},"sys":{"pod":"n"},"dt_txt":"2018-05-27 09:00:00"},{"dt":1527422400,"main":{"temp":295.305,"temp_min":295.305,"temp_max":295.305,"pressure":985,"sea_level":1025.69,"grnd_level":985,"humidity":86,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}],"clouds":{"all":56},"wind":{"speed":2.05,"deg":201.001},"rain":{},"sys":{"pod":"d"},"dt_txt":"2018-05-27 12:00:00"},{"dt":1527433200,"main":{"temp":299.27,"temp_min":299.27,"temp_max":299.27,"pressure":985.3,"sea_level":1025.78,"grnd_level":985.3,"humidity":77,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}],"clouds":{"all":56},"wind":{"speed":1.71,"deg":205.003},"rain":{},"sys":{"pod":"d"},"dt_txt":"2018-05-27 15:00:00"},{"dt":1527444000,"main":{"temp":301.592,"temp_min":301.592,"temp_max":301.592,"pressure":984.71,"sea_level":1025.18,"grnd_level":984.71,"humidity":68,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],"clouds":{"all":88},"wind":{"speed":1.51,"deg":178.5},"rain":{},"sys":{"pod":"d"},"dt_txt":"2018-05-27 18:00:00"},{"dt":1527454800,"main":{"temp":301.277,"temp_min":301.277,"temp_max":301.277,"pressure":983.61,"sea_level":1024.06,"grnd_level":983.61,"humidity":64,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],"clouds":{"all":88},"wind":{"speed":1.95,"deg":169.001},"rain":{},"sys":{"pod":"d"},"dt_txt":"2018-05-27 21:00:00"},{"dt":1527465600,"main":{"temp":299.038,"temp_min":299.038,"temp_max":299.038,"pressure":984.35,"sea_level":1024.91,"grnd_level":984.35,"humidity":70,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04n"}],"clouds":{"all":92},"wind":{"speed":1.91,"deg":159.504},"rain":{},"sys":{"pod":"n"},"dt_txt":"2018-05-28 00:00:00"},{"dt":1527476400,"main":{"temp":296.96,"temp_min":296.96,"temp_max":296.96,"pressure":985.28,"sea_level":1025.95,"grnd_level":985.28,"humidity":73,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04n"}],"clouds":{"all":92},"wind":{"speed":1.62,"deg":162},"rain":{},"sys":{"pod":"n"},"dt_txt":"2018-05-28 03:00:00"},{"dt":1527487200,"main":{"temp":295.103,"temp_min":295.103,"temp_max":295.103,"pressure":985.02,"sea_level":1025.88,"grnd_level":985.02,"humidity":82,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04n"}],"clouds":{"all":92},"wind":{"speed":1.01,"deg":132.501},"rain":{},"sys":{"pod":"n"},"dt_txt":"2018-05-28 06:00:00"},{"dt":1527498000,"main":{"temp":294.313,"temp_min":294.313,"temp_max":294.313,"pressure":985.41,"sea_level":1026.17,"grnd_level":985.41,"humidity":79,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10n"}],"clouds":{"all":92},"wind":{"speed":1.86,"deg":145.007},"rain":{"3h":0.03},"sys":{"pod":"n"},"dt_txt":"2018-05-28 09:00:00"},{"dt":1527508800,"main":{"temp":294.087,"temp_min":294.087,"temp_max":294.087,"pressure":985.63,"sea_level":1026.55,"grnd_level":985.63,"humidity":86,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10d"}],"clouds":{"all":92},"wind":{"speed":2.37,"deg":127.501},"rain":{"3h":0.72},"sys":{"pod":"d"},"dt_txt":"2018-05-28 12:00:00"},{"dt":1527519600,"main":{"temp":295.375,"temp_min":295.375,"temp_max":295.375,"pressure":986.25,"sea_level":1026.9,"grnd_level":986.25,"humidity":83,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10d"}],"clouds":{"all":92},"wind":{"speed":2.95,"deg":109.001},"rain":{"3h":1.43},"sys":{"pod":"d"},"dt_txt":"2018-05-28 15:00:00"},{"dt":1527530400,"main":{"temp":298.031,"temp_min":298.031,"temp_max":298.031,"pressure":985.27,"sea_level":1025.83,"grnd_level":985.27,"humidity":76,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10d"}],"clouds":{"all":20},"wind":{"speed":2.36,"deg":78.5058},"rain":{"3h":0.26},"sys":{"pod":"d"},"dt_txt":"2018-05-28 18:00:00"},{"dt":1527541200,"main":{"temp":301.629,"temp_min":301.629,"temp_max":301.629,"pressure":983.44,"sea_level":1023.95,"grnd_level":983.44,"humidity":65,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10d"}],"clouds":{"all":32},"wind":{"speed":2.22,"deg":81.0038},"rain":{"3h":0.03},"sys":{"pod":"d"},"dt_txt":"2018-05-28 21:00:00"},{"dt":1527552000,"main":{"temp":295.225,"temp_min":295.225,"temp_max":295.225,"pressure":983.77,"sea_level":1024.13,"grnd_level":983.77,"humidity":93,"temp_kf":0},"weather":[{"id":501,"main":"Rain","description":"moderate rain","icon":"10n"}],"clouds":{"all":76},"wind":{"speed":1.16,"deg":77.5075},"rain":{"3h":4.22},"sys":{"pod":"n"},"dt_txt":"2018-05-29 00:00:00"},{"dt":1527562800,"main":{"temp":294.342,"temp_min":294.342,"temp_max":294.342,"pressure":984,"sea_level":1024.64,"grnd_level":984,"humidity":91,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10n"}],"clouds":{"all":68},"wind":{"speed":1.62,"deg":57.0002},"rain":{"3h":0.97},"sys":{"pod":"n"},"dt_txt":"2018-05-29 03:00:00"},{"dt":1527573600,"main":{"temp":294.427,"temp_min":294.427,"temp_max":294.427,"pressure":983.51,"sea_level":1024.17,"grnd_level":983.51,"humidity":92,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04n"}],"clouds":{"all":92},"wind":{"speed":1.95,"deg":98.5004},"rain":{},"sys":{"pod":"n"},"dt_txt":"2018-05-29 06:00:00"},{"dt":1527584400,"main":{"temp":294.187,"temp_min":294.187,"temp_max":294.187,"pressure":982.75,"sea_level":1023.45,"grnd_level":982.75,"humidity":90,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04n"}],"clouds":{"all":92},"wind":{"speed":1.92,"deg":99.5003},"rain":{},"sys":{"pod":"n"},"dt_txt":"2018-05-29 09:00:00"},{"dt":1527595200,"main":{"temp":295.007,"temp_min":295.007,"temp_max":295.007,"pressure":983.28,"sea_level":1024.06,"grnd_level":983.28,"humidity":89,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],"clouds":{"all":92},"wind":{"speed":2.11,"deg":155.506},"rain":{},"sys":{"pod":"d"},"dt_txt":"2018-05-29 12:00:00"},{"dt":1527606000,"main":{"temp":295.03,"temp_min":295.03,"temp_max":295.03,"pressure":984.69,"sea_level":1025.24,"grnd_level":984.69,"humidity":88,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10d"}],"clouds":{"all":100},"wind":{"speed":2.18,"deg":135.502},"rain":{"3h":0.48},"sys":{"pod":"d"},"dt_txt":"2018-05-29 15:00:00"},{"dt":1527616800,"main":{"temp":294.722,"temp_min":294.722,"temp_max":294.722,"pressure":983.8,"sea_level":1024.39,"grnd_level":983.8,"humidity":95,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10d"}],"clouds":{"all":92},"wind":{"speed":2.14,"deg":70.5013},"rain":{"3h":1.69},"sys":{"pod":"d"},"dt_txt":"2018-05-29 18:00:00"},{"dt":1527627600,"main":{"temp":297.378,"temp_min":297.378,"temp_max":297.378,"pressure":981.96,"sea_level":1022.38,"grnd_level":981.96,"humidity":80,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10d"}],"clouds":{"all":88},"wind":{"speed":2.36,"deg":72.0023},"rain":{"3h":0.0099999999999998},"sys":{"pod":"d"},"dt_txt":"2018-05-29 21:00:00"}]
     * city : {"id":4298960,"name":"London","coord":{"lat":37.129,"lon":-84.0833},"country":"US","population":7993}
     */

    var cod: String? = null
    var message: Double = 0.toDouble()
    var cnt: Int = 0
    var city: CityBean? = null
    var list: List<ListBean>? = null

    class CityBean {
        /**
         * id : 4298960
         * name : London
         * coord : {"lat":37.129,"lon":-84.0833}
         * country : US
         * population : 7993
         */

        var id: Int = 0
        var name: String? = null
        var coord: CoordBean? = null
        var country: String? = null
        var population: Int = 0

        class CoordBean {
            /**
             * lat : 37.129
             * lon : -84.0833
             */

            var lat: Double = 0.toDouble()
            var lon: Double = 0.toDouble()
        }
    }

    class ListBean {
        /**
         * dt : 1527228000
         * main : {"temp":291.86,"temp_min":291.86,"temp_max":294.046,"pressure":990.17,"sea_level":1031.14,"grnd_level":990.17,"humidity":89,"temp_kf":-2.19}
         * weather : [{"id":500,"main":"Rain","description":"light rain","icon":"10n"}]
         * clouds : {"all":0}
         * wind : {"speed":2.21,"deg":162.504}
         * rain : {"3h":1.45}
         * sys : {"pod":"n"}
         * dt_txt : 2018-05-25 06:00:00
         */

        var dt: Int = 0
        var main: MainBean? = null
        var clouds: CloudsBean? = null
        var wind: WindBean? = null
        var rain: RainBean? = null
        var sys: SysBean? = null
        var dt_txt: String? = null
        var weather: List<WeatherBean>? = null

        class MainBean {
            /**
             * temp : 291.86
             * temp_min : 291.86
             * temp_max : 294.046
             * pressure : 990.17
             * sea_level : 1031.14
             * grnd_level : 990.17
             * humidity : 89
             * temp_kf : -2.19
             */

            var temp: Double = 0.toDouble()
            var temp_min: Double = 0.toDouble()
            var temp_max: Double = 0.toDouble()
            var pressure: Double = 0.toDouble()
            var sea_level: Double = 0.toDouble()
            var grnd_level: Double = 0.toDouble()
            var humidity: Int = 0
            var temp_kf: Double = 0.toDouble()
        }

        class CloudsBean {
            /**
             * all : 0
             */

            var all: Int = 0
        }

        class WindBean {
            /**
             * speed : 2.21
             * deg : 162.504
             */

            var speed: Double = 0.toDouble()
            var deg: Double = 0.toDouble()
        }

        class RainBean {
            /**
             * 3h : 1.45
             */

            @SerializedName("3h")
            var `_$3h`: Double = 0.toDouble()
        }

        class SysBean {
            /**
             * pod : n
             */

            var pod: String? = null
        }

        class WeatherBean {
            /**
             * id : 500
             * main : Rain
             * description : light rain
             * icon : 10n
             */

            var id: Int = 0
            var main: String? = null
            var description: String? = null
            var icon: String? = null
        }
    }
}
