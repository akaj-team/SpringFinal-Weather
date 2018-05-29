package vn.asiantech.android.springfinalweather.kotlin.asynctask

import android.database.sqlite.SQLiteConstraintException
import android.os.AsyncTask
import vn.asiantech.android.springfinalweather.kotlin.dao.CityCollectionDao
import vn.asiantech.android.springfinalweather.kotlin.model.CityCollection

class InsertCityCollectionAsyncTask(private var cityCollectionDao: CityCollectionDao) : AsyncTask<CityCollection, Unit, Unit>() {
    override fun doInBackground(vararg params: CityCollection?) {
        try {
            params[0]?.let { cityCollectionDao.insert(it) }
        } catch (e: SQLiteConstraintException) {
            params[0]?.let { cityCollectionDao.update(it) }
        }
    }
}
