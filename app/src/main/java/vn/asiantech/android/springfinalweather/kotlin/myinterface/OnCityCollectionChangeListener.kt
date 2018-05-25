package vn.asiantech.android.springfinalweather.kotlin.myinterface

import vn.asiantech.android.springfinalweather.kotlin.model.CityCollection

interface OnCityCollectionChangeListener {
    fun onChangeShowCityCollection(cityCollection: CityCollection)
    fun onDeleteCityCollection(cityCollection: CityCollection)
}