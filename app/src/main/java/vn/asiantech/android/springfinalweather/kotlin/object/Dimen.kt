package vn.asiantech.android.springfinalweather.kotlin.`object`

import android.annotation.SuppressLint
import android.app.Activity
import java.text.SimpleDateFormat

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

        @SuppressLint("SimpleDateFormat")
        fun getDate(day: String): String {
            val dateParse = SimpleDateFormat("yyyy-MM-dd HH:mm")
            val dateFormat = SimpleDateFormat("MM/dd/yyyy")
            return dateFormat.format(dateParse.parse(day))
        }

        @SuppressLint("SimpleDateFormat")
        fun getDateFormat(day: String): String {
            val dateParse = SimpleDateFormat("yyyy-MM-dd")
            val dateFormat = SimpleDateFormat("MM/dd/yyyy")
            return dateFormat.format(dateParse.parse(day))
        }
    }
}
