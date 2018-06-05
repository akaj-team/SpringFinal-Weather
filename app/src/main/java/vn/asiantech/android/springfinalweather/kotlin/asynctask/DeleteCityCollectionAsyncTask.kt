package vn.asiantech.android.springfinalweather.kotlin.asynctask

import android.os.AsyncTask
import vn.asiantech.android.springfinalweather.kotlin.dao.CityCollectionDao
import vn.asiantech.android.springfinalweather.kotlin.dao.CityHistoryWeatherDao
import vn.asiantech.android.springfinalweather.kotlin.dao.CityWeatherDao
import vn.asiantech.android.springfinalweather.kotlin.model.CityCollection

class DeleteCityCollectionAsyncTask(
        private var cityCollectionDao: CityCollectionDao,
        private var cityWeatherDao: CityWeatherDao,
        private var cityHistoryWeatherDao: CityHistoryWeatherDao
) : AsyncTask<CityCollection, Unit, Unit>() {
    override fun doInBackground(vararg params: CityCollection?) {
        params[0]?.let {
            cityCollectionDao.delete(it)
            cityWeatherDao.deleteBy(it.cityName)
            cityHistoryWeatherDao.deleteHistoryBy(it.cityName)
        }
    }
}
