package vn.asiantech.android.springfinalweather.kotlin.asynctask

import android.os.AsyncTask
import vn.asiantech.android.springfinalweather.kotlin.dao.CityCollectionDao

class DeleteUserLocationAsyncTask(
        private var cityCollectionDao: CityCollectionDao
) : AsyncTask<Unit, Unit, Unit>() {
    override fun doInBackground(vararg params: Unit?) {
        cityCollectionDao.deleteLocation()
    }
}
