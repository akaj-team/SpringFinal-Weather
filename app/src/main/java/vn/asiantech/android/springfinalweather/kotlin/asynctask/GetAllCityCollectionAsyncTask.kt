package vn.asiantech.android.springfinalweather.kotlin.asynctask

import android.os.AsyncTask
import vn.asiantech.android.springfinalweather.kotlin.dao.CityCollectionDao
import vn.asiantech.android.springfinalweather.kotlin.model.CityCollection
import vn.asiantech.android.springfinalweather.kotlin.interfaces.OnCityCollectionAsyncListener

class GetAllCityCollectionAsyncTask(
        private var cityCollectionDao: CityCollectionDao,
        private var listener: OnCityCollectionAsyncListener
) : AsyncTask<Unit, Unit, List<CityCollection>>() {
    override fun doInBackground(vararg params: Unit?): List<CityCollection>? {
        return cityCollectionDao.getAllCityCollection()
    }

    override fun onPostExecute(result: List<CityCollection>) {
        listener.onLoadListListener(result)
    }
}
