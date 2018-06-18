package vn.asiantech.android.springfinalweather.kotlin.asynctask

import android.os.AsyncTask
import vn.asiantech.android.springfinalweather.kotlin.dao.CityCollectionDao
import vn.asiantech.android.springfinalweather.kotlin.model.CityCollection
import vn.asiantech.android.springfinalweather.kotlin.interfaces.OnInsertDoneListener

class InsertCityCollectionAsyncTask(
        private var cityCollectionDao: CityCollectionDao,
        private var listener: OnInsertDoneListener
) : AsyncTask<CityCollection, Unit, CityCollection>() {
    override fun doInBackground(vararg params: CityCollection): CityCollection {
        cityCollectionDao.insert(params[0])
        return params[0]
    }

    override fun onPostExecute(result: CityCollection) {
        listener.onInsertDone(result)
    }
}
