package vn.asiantech.android.springfinalweather.kotlin.`object`

import vn.asiantech.android.springfinalweather.R

class Image {
    companion object {
        fun getIcon(icon: String, isDay: Int): Int {
            return when (isDay) {
                1 -> {
                    when (icon) {
                        Constants.CODE_1000 -> R.drawable.ic_113d
                        Constants.CODE_1003 -> R.drawable.ic_116d
                        Constants.CODE_1006 -> R.drawable.ic_116d
                        Constants.CODE_1009 -> R.drawable.ic_122d
                        Constants.CODE_1030 -> R.drawable.ic_143d
                        Constants.CODE_1063 -> R.drawable.ic_176d
                        Constants.CODE_1066 -> R.drawable.ic_179d
                        Constants.CODE_1069 -> R.drawable.ic_182d
                        Constants.CODE_1072 -> R.drawable.ic_185d
                        Constants.CODE_1087 -> R.drawable.ic_200d
                        Constants.CODE_1114 -> R.drawable.ic_227d
                        Constants.CODE_1117 -> R.drawable.ic_230d
                        Constants.CODE_1135 -> R.drawable.ic_248d
                        Constants.CODE_1147 -> R.drawable.ic_260d
                        Constants.CODE_1150 -> R.drawable.ic_263d
                        Constants.CODE_1153 -> R.drawable.ic_266d
                        Constants.CODE_1168 -> R.drawable.ic_281d
                        Constants.CODE_1171 -> R.drawable.ic_284d
                        Constants.CODE_1180 -> R.drawable.ic_293d
                        Constants.CODE_1183 -> R.drawable.ic_296d
                        Constants.CODE_1186 -> R.drawable.ic_299d
                        Constants.CODE_1189 -> R.drawable.ic_302d
                        Constants.CODE_1192 -> R.drawable.ic_305d
                        Constants.CODE_1195 -> R.drawable.ic_308d
                        Constants.CODE_1198 -> R.drawable.ic_311d
                        Constants.CODE_1201 -> R.drawable.ic_314d
                        Constants.CODE_1204 -> R.drawable.ic_317d
                        Constants.CODE_1207 -> R.drawable.ic_320d
                        Constants.CODE_1210 -> R.drawable.ic_323d
                        Constants.CODE_1213 -> R.drawable.ic_326d
                        Constants.CODE_1216 -> R.drawable.ic_329d
                        Constants.CODE_1219 -> R.drawable.ic_332d
                        Constants.CODE_1222 -> R.drawable.ic_335d
                        Constants.CODE_1225 -> R.drawable.ic_338d
                        Constants.CODE_1237 -> R.drawable.ic_350d
                        Constants.CODE_1240 -> R.drawable.ic_353d
                        Constants.CODE_1243 -> R.drawable.ic_356d
                        Constants.CODE_1246 -> R.drawable.ic_359d
                        Constants.CODE_1249 -> R.drawable.ic_362d
                        Constants.CODE_1252 -> R.drawable.ic_365d
                        Constants.CODE_1255 -> R.drawable.ic_368d
                        Constants.CODE_1258 -> R.drawable.ic_371d
                        Constants.CODE_1261 -> R.drawable.ic_374d
                        Constants.CODE_1264 -> R.drawable.ic_377d
                        Constants.CODE_1273 -> R.drawable.ic_386d
                        Constants.CODE_1276 -> R.drawable.ic_389d
                        Constants.CODE_1279 -> R.drawable.ic_392d
                        Constants.CODE_1282 -> R.drawable.ic_395d
                        else -> R.drawable.img_na
                    }
                }
                0 -> {
                    when (icon) {
                        Constants.CODE_1000 -> R.drawable.ic_113
                        Constants.CODE_1003 -> R.drawable.ic_116
                        Constants.CODE_1006 -> R.drawable.ic_116
                        Constants.CODE_1009 -> R.drawable.ic_122
                        Constants.CODE_1030 -> R.drawable.ic_143
                        Constants.CODE_1063 -> R.drawable.ic_176
                        Constants.CODE_1066 -> R.drawable.ic_179
                        Constants.CODE_1069 -> R.drawable.ic_182
                        Constants.CODE_1072 -> R.drawable.ic_185
                        Constants.CODE_1087 -> R.drawable.ic_200
                        Constants.CODE_1114 -> R.drawable.ic_227
                        Constants.CODE_1117 -> R.drawable.ic_230
                        Constants.CODE_1135 -> R.drawable.ic_248
                        Constants.CODE_1147 -> R.drawable.ic_260
                        Constants.CODE_1150 -> R.drawable.ic_263
                        Constants.CODE_1153 -> R.drawable.ic_266
                        Constants.CODE_1168 -> R.drawable.ic_281
                        Constants.CODE_1171 -> R.drawable.ic_284
                        Constants.CODE_1180 -> R.drawable.ic_293
                        Constants.CODE_1183 -> R.drawable.ic_296
                        Constants.CODE_1186 -> R.drawable.ic_299
                        Constants.CODE_1189 -> R.drawable.ic_302
                        Constants.CODE_1192 -> R.drawable.ic_305
                        Constants.CODE_1195 -> R.drawable.ic_308
                        Constants.CODE_1198 -> R.drawable.ic_311
                        Constants.CODE_1201 -> R.drawable.ic_314
                        Constants.CODE_1204 -> R.drawable.ic_317
                        Constants.CODE_1207 -> R.drawable.ic_320
                        Constants.CODE_1210 -> R.drawable.ic_323
                        Constants.CODE_1213 -> R.drawable.ic_326
                        Constants.CODE_1216 -> R.drawable.ic_329
                        Constants.CODE_1219 -> R.drawable.ic_332
                        Constants.CODE_1222 -> R.drawable.ic_335
                        Constants.CODE_1225 -> R.drawable.ic_338
                        Constants.CODE_1237 -> R.drawable.ic_350
                        Constants.CODE_1240 -> R.drawable.ic_353
                        Constants.CODE_1243 -> R.drawable.ic_356
                        Constants.CODE_1246 -> R.drawable.ic_359
                        Constants.CODE_1249 -> R.drawable.ic_362
                        Constants.CODE_1252 -> R.drawable.ic_365
                        Constants.CODE_1255 -> R.drawable.ic_368
                        Constants.CODE_1258 -> R.drawable.ic_371
                        Constants.CODE_1261 -> R.drawable.ic_374
                        Constants.CODE_1264 -> R.drawable.ic_377
                        Constants.CODE_1273 -> R.drawable.ic_386
                        Constants.CODE_1276 -> R.drawable.ic_389
                        Constants.CODE_1279 -> R.drawable.ic_392
                        Constants.CODE_1282 -> R.drawable.ic_395
                        else -> R.drawable.img_na
                    }
                }
                else -> return R.drawable.img_na
            }
        }
    }
}
