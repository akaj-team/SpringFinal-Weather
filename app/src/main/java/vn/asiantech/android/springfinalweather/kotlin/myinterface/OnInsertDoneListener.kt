package vn.asiantech.android.springfinalweather.kotlin.myinterface

import vn.asiantech.android.springfinalweather.kotlin.model.CityCollection

interface OnInsertDoneListener {
    fun onInsertDone(cityCollection: CityCollection)
}