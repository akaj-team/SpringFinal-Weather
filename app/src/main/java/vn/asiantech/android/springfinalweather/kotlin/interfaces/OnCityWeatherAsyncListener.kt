package vn.asiantech.android.springfinalweather.kotlin.interfaces

import vn.asiantech.android.springfinalweather.kotlin.model.CityWeather

interface OnCityWeatherAsyncListener {
    fun onLoadCityWeatherList(listCityWeather: List<CityWeather>)
}
