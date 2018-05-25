package vn.asiantech.android.springfinalweather.kotlin.asynctask

import android.os.AsyncTask
import vn.asiantech.android.springfinalweather.kotlin.dao.CityCollectionDao
import vn.asiantech.android.springfinalweather.kotlin.model.CityCollection

class DeleteCityCollectionAsyncTask(private var cityCollectionDao: CityCollectionDao) : AsyncTask<CityCollection, Unit, Unit>() {
    override fun doInBackground(vararg params: CityCollection?) {
        params[0]?.let { cityCollectionDao.delete(it) }
    }
}
