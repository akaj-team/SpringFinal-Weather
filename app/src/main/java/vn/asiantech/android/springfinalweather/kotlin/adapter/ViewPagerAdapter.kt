package vn.asiantech.android.springfinalweather.kotlin.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.fragment.FragmentShowWeatherForecast
import vn.asiantech.android.springfinalweather.kotlin.model.CityCollection
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnCityCollectionChangeListener

class ViewPagerAdapter(fm: FragmentManager, private val mListCityCollection: List<CityCollection>, private var listener: OnCityCollectionChangeListener)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return newFragment(mListCityCollection[position])
    }

    override fun getCount(): Int {
        return mListCityCollection.size
    }

    private fun newFragment(cityCollection: CityCollection): FragmentShowWeatherForecast {
        val fragmentShowWeatherForecast = FragmentShowWeatherForecast()
        fragmentShowWeatherForecast.setOnAddNewCityCollectionListener(listener)
        val bundle = Bundle()
        bundle.putString(Constants.CITY_NAME, cityCollection.cityName)
        bundle.putString(Constants.DATE, cityCollection.date)
        bundle.putString(Constants.COUNTRY_NAME, cityCollection.countryName)
        bundle.putFloat(Constants.TEMP, cityCollection.temp)
        bundle.putFloat(Constants.APPTEMP, cityCollection.temp)
        bundle.putString(Constants.SUNRISE, cityCollection.sunrise)
        bundle.putString(Constants.SUNSET, cityCollection.sunset)
        bundle.putInt(Constants.HUMIDITY, cityCollection.humidity)
        bundle.putFloat(Constants.WIND, cityCollection.wind)
        bundle.putInt(Constants.CLOUD, cityCollection.cloud)
        bundle.putString(Constants.DESCRIPTION, cityCollection.description)
        bundle.putString(Constants.ICON, cityCollection.icon)
        fragmentShowWeatherForecast.arguments = bundle
        return fragmentShowWeatherForecast
    }
}
