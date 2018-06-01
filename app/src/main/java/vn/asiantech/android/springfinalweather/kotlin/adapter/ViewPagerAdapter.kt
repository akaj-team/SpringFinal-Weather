package vn.asiantech.android.springfinalweather.kotlin.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.fragment.FragmentShowWeatherForecast
import vn.asiantech.android.springfinalweather.kotlin.model.CityCollection

class ViewPagerAdapter(fm: FragmentManager, private var mListCityCollection: MutableList<CityCollection>)
    : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return newFragment(mListCityCollection[position])
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getCount(): Int {
        return mListCityCollection.size
    }

    private fun newFragment(cityCollection: CityCollection): FragmentShowWeatherForecast {
        val fragmentShowWeatherForecast = FragmentShowWeatherForecast()
        val bundle = Bundle()
        bundle.putString(Constants.CITY_NAME, cityCollection.cityName)
        bundle.putString(Constants.DATE, cityCollection.date)
        bundle.putString(Constants.COUNTRY_NAME, cityCollection.countryName)
        bundle.putFloat(Constants.TEMP, cityCollection.temp)
        bundle.putFloat(Constants.APP_TEMP, cityCollection.appTemp)
        bundle.putInt(Constants.HUMIDITY, cityCollection.humidity)
        bundle.putFloat(Constants.WIND, cityCollection.wind)
        bundle.putInt(Constants.CLOUD, cityCollection.cloud)
        bundle.putString(Constants.DESCRIPTION, cityCollection.description)
        bundle.putString(Constants.ICON, cityCollection.icon)
        bundle.putInt(Constants.IS_DAY, cityCollection.day)
        fragmentShowWeatherForecast.arguments = bundle
        return fragmentShowWeatherForecast
    }
}
