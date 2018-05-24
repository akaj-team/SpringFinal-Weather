package vn.asiantech.android.springfinalweather.kotlin.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import vn.asiantech.android.springfinalweather.kotlin.fragment.FragmentShowWeatherForecast

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> FragmentShowWeatherForecast()
            else -> null
        }
    }

    override fun getCount(): Int {
        return 1
    }
}
