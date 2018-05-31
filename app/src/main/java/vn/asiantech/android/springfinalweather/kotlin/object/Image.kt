package vn.asiantech.android.springfinalweather.kotlin.`object`

import vn.asiantech.android.springfinalweather.R

class Image {
    companion object {
        fun getIcon(icon: String, isDay: Int): Int {
            return when (isDay) {
                0 -> {
                    when (icon) {
                        else -> return R.drawable.img_na
                    }
                }
                1 -> {
                    when (icon) {
                        else -> return R.drawable.img_na
                    }
                }
                else -> return R.drawable.img_na
            }
        }
    }
}
