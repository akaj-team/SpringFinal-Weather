package vn.asiantech.android.springfinalweather.kotlin.myinterface

import vn.asiantech.android.springfinalweather.kotlin.model.CityCollection

interface OnCityCollectionAsyncListener {
    fun onLoadListListener(listCityCollection: List<CityCollection>)
}
