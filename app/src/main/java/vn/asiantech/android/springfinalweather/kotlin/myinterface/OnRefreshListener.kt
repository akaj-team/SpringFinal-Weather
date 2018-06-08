package vn.asiantech.android.springfinalweather.kotlin.myinterface

import android.support.v4.widget.SwipeRefreshLayout

interface OnRefreshListener {
    fun onRefresh(cityName: String, swipeRefreshLayout: SwipeRefreshLayout)
}
