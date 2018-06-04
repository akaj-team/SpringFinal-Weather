package vn.asiantech.android.springfinalweather.kotlin.`object`

import android.app.Activity

class Dimen {
    companion object {
        fun getStatusBarHeight(activity: Activity): Int {
            var result = 0
            val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = activity.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }

        fun getNavigationBarHeight(activity: Activity): Int {
            var result = 0
            val resourceId = activity.resources.getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = activity.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }
    }
}
