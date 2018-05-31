package vn.asiantech.android.springfinalweather.kotlin.`object`

class Constants {
    companion object {
        // const for Location permission
        const val LOCATION_PERMISSION = "location_permission"
        const val UNIT_OF_TEMP = "unit_of_temp"
        const val UNIT_OF_WIND_SPEED = "unit_of_wind_speed"
        const val LOCATION_PERMISSION_REQUEST = 1

        // const for Google find City Api
        const val BASE_URL_API_GOOGLE = "https://maps.googleapis.com"
        const val CITY_TYPES = "(cities)"
        const val GOOGLE_KEY = "AIzaSyAvt30-GCmebRk-RB4QOlljVrCnepnyMHA"

        //const for intent key
        const val CITY_NAME = "city_name"
        const val DATE = "date"
        const val COUNTRY_NAME = "country_name"
        const val TEMP = "temp"
        const val APPTEMP = "app_temp"
        const val HUMIDITY = "humidity"
        const val WIND = "wind"
        const val CLOUD = "cloud"
        const val DESCRIPTION = "description"
        const val ICON = "icon"
        const val IS_DAY = "is_day"
        const val CANBACK = "can_back"
        const val FINDLOCATION = "find_location"

        //const for OpenWeatherApi
        const val KEY = "b314dbc7647740fbbfe45806183105"
        const val BASE_URL = "https://api.weatherbit.io"
        const val DAYS = 5

        //const for Xu api
        const val BASE_URL_WEATHER = "http://api.apixu.com/v1/"

        //const for icon weather

        //const for State of City Collection
        const val USER_LOCATION = true
        const val OTHER_LOCATION = false
        const val FOCUS_POSITION = "focus_position"
        const val NAME_LOCATION = "name_location"
    }
}
