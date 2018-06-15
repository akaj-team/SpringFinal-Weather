package vn.asiantech.android.springfinalweather.kotlin.layoutmanager

import android.content.Context
import android.support.v7.widget.LinearLayoutManager

class LinearLayoutDisableScroll(context: Context?) : LinearLayoutManager(context) {
    override fun canScrollVertically(): Boolean {
        return false
    }
}
