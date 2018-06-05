package vn.asiantech.android.springfinalweather.kotlin.asynctask

import android.os.AsyncTask
import vn.asiantech.android.springfinalweather.kotlin.dao.CityWeatherDao
import vn.asiantech.android.springfinalweather.kotlin.model.CityWeather
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnCityWeatherAsyncListener

class GetAllCityWeatherAsyncTask(
        private var cityWeatherDao: CityWeatherDao,
        private var listener: OnCityWeatherAsyncListener,
        private var cityName: String
) : AsyncTask<Unit, Unit, List<CityWeather>>() {
    private var mCheck = false

    override fun doInBackground(vararg params: Unit?): List<CityWeather> {
        mCheck = true
        return cityWeatherDao.getCityWeatherBy(cityName)
    }

    override fun onPostExecute(result: List<CityWeather>) {
        if (mCheck) {
            listener.onLoadCityWeatherList(result)
        }
    }
}
