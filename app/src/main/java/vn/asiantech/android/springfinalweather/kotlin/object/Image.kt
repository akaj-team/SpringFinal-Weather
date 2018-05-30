package vn.asiantech.android.springfinalweather.kotlin.`object`

import vn.asiantech.android.springfinalweather.R

class Image {
    companion object {
        fun getIcon(icon: String): Int {
            when (icon) {
                Constants.ICON_T01D -> return R.drawable.ic_t01d
                Constants.ICON_T01N -> return R.drawable.ic_t01n
                Constants.ICON_T02D -> return R.drawable.ic_t01d
                Constants.ICON_T02N -> return R.drawable.ic_t01n
                Constants.ICON_T03D -> return R.drawable.ic_t01d
                Constants.ICON_T03N -> return R.drawable.ic_t01n
                Constants.ICON_T04D -> return R.drawable.ic_t04d
                Constants.ICON_T04N -> return R.drawable.ic_t04n
                Constants.ICON_T05D -> return R.drawable.ic_t04d
                Constants.ICON_T05N -> return R.drawable.ic_t04n
                Constants.ICON_D01D -> return R.drawable.ic_d01d
                Constants.ICON_D01N -> return R.drawable.ic_d01n
                Constants.ICON_D02D -> return R.drawable.ic_d01d
                Constants.ICON_D02N -> return R.drawable.ic_d01n
                Constants.ICON_D03D -> return R.drawable.ic_d01d
                Constants.ICON_D03N -> return R.drawable.ic_d01n
                Constants.ICON_R01D -> return R.drawable.ic_r01
                Constants.ICON_R01N -> return R.drawable.ic_r01
                Constants.ICON_R02D -> return R.drawable.ic_r01
                Constants.ICON_R02N -> return R.drawable.ic_r01
                Constants.ICON_R03D -> return R.drawable.ic_r01
                Constants.ICON_R03N -> return R.drawable.ic_r01
                Constants.ICON_F01D -> return R.drawable.ic_r01
                Constants.ICON_F01N -> return R.drawable.ic_r01
                Constants.ICON_R04D -> return R.drawable.ic_r01
                Constants.ICON_R04N -> return R.drawable.ic_r01
                Constants.ICON_R05D -> return R.drawable.ic_d01d
                Constants.ICON_R05N -> return R.drawable.ic_d01n
                Constants.ICON_R06D -> return R.drawable.ic_r01
                Constants.ICON_R06N -> return R.drawable.ic_r01
                Constants.ICON_S01D -> return R.drawable.ic_s01d
                Constants.ICON_S01N -> return R.drawable.ic_s01n
                Constants.ICON_S02D -> return R.drawable.ic_s02
                Constants.ICON_S02N -> return R.drawable.ic_s02
                Constants.ICON_S03D -> return R.drawable.ic_s02
                Constants.ICON_S03N -> return R.drawable.ic_s02
                Constants.ICON_S04D -> return R.drawable.ic_s05
                Constants.ICON_S04N -> return R.drawable.ic_s05
                Constants.ICON_S05D -> return R.drawable.ic_s05
                Constants.ICON_S05N -> return R.drawable.ic_s05
                Constants.ICON_S06D -> return R.drawable.ic_r01
                Constants.ICON_S06N -> return R.drawable.ic_r01
                Constants.ICON_A01D -> return R.drawable.ic_a01d
                Constants.ICON_A01N -> return R.drawable.ic_a01n
                Constants.ICON_A02D -> return R.drawable.ic_a01d
                Constants.ICON_A02N -> return R.drawable.ic_a01n
                Constants.ICON_A03D -> return R.drawable.ic_a01d
                Constants.ICON_A03N -> return R.drawable.ic_a01n
                Constants.ICON_A04D -> return R.drawable.ic_a01d
                Constants.ICON_A04N -> return R.drawable.ic_a01n
                Constants.ICON_A05D -> return R.drawable.ic_a01d
                Constants.ICON_A05N -> return R.drawable.ic_a01n
                Constants.ICON_A06D -> return R.drawable.ic_a01d
                Constants.ICON_A06N -> return R.drawable.ic_a01n
                Constants.ICON_C01D -> return R.drawable.ic_c01d
                Constants.ICON_C01N -> return R.drawable.ic_c01n
                Constants.ICON_C02D -> return R.drawable.ic_c02d
                Constants.ICON_C02N -> return R.drawable.ic_c02n
                Constants.ICON_C03D -> return R.drawable.ic_c04
                Constants.ICON_C03N -> return R.drawable.ic_c04
                Constants.ICON_U00D -> return R.drawable.img_na
                Constants.ICON_U00N -> return R.drawable.img_na
                Constants.ICON_C04D -> return R.drawable.ic_c04
                Constants.ICON_C04N -> return R.drawable.ic_c04
                else -> return R.drawable.img_na
            }
        }
    }
}
