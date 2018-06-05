package vn.asiantech.android.springfinalweather.kotlin.myinterface

import vn.asiantech.android.springfinalweather.kotlin.model.CityHistoryWeather

interface OnCityHistoryWeatherAsyncListener {
    fun onLoadCityHistoryWeatherList(listCityHistoryWeather: List<CityHistoryWeather>)
}
