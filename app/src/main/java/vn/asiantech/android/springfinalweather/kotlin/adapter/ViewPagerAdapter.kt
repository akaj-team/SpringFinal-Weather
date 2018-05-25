package vn.asiantech.android.springfinalweather.kotlin.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.fragment.FragmentShowWeatherForecast

class ViewPagerAdapter(fm: FragmentManager, private val cityName: String) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> newFragment(cityName)
            else -> null
        }
    }

    override fun getCount(): Int {
        return 1
    }

    private fun newFragment(cityName: String) :FragmentShowWeatherForecast {
        val fragmentShowWeatherForecast = FragmentShowWeatherForecast()
        val bundle = Bundle()
        bundle.putString(Constants.CITY_NAME, cityName)
        fragmentShowWeatherForecast.arguments = bundle
        return fragmentShowWeatherForecast
    }
}
