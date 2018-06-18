package vn.asiantech.android.springfinalweather.kotlin.interfaces

import vn.asiantech.android.springfinalweather.kotlin.model.CityHistoryWeather

interface OnCityHistoryWeatherAsyncListener {
    fun onLoadCityHistoryWeatherList(listCityHistoryWeather: List<CityHistoryWeather>)
}
