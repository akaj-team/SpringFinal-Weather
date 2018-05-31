package vn.asiantech.android.springfinalweather.kotlin.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.asiantech.android.springfinalweather.R
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.adapter.CityCollectionAdapter
import vn.asiantech.android.springfinalweather.kotlin.adapter.ViewPagerAdapter
import vn.asiantech.android.springfinalweather.kotlin.apiservice.ApiServices
import vn.asiantech.android.springfinalweather.kotlin.model.CityCollection
import vn.asiantech.android.springfinalweather.kotlin.model.InformationWeather
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnCityCollectionAsyncListener
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnCityCollectionChangeListener
import vn.asiantech.android.springfinalweather.kotlin.room.WeatherRepository
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(),
        View.OnClickListener, OnCityCollectionAsyncListener, OnCityCollectionChangeListener,
        DrawerLayout.DrawerListener {
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mImgMenuIcon: ImageView
    private lateinit var mTvTitle: TextView
    private lateinit var mTvDate: TextView
    private lateinit var mViewPager: ViewPager
    private lateinit var mViewPagerAdapter: ViewPagerAdapter
    private lateinit var mTvSetting: TextView
    private lateinit var mTvAddLocation: TextView
    private lateinit var mCityName: String
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mCityCollectionAdapter: CityCollectionAdapter
    private var mListCityCollection: MutableList<CityCollection> = mutableListOf()
    private var mFocusName: String = ""
    private var isNewData = false
    private var isAddLocation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initListener()
    }

    override fun onResume() {
        super.onResume()
        if (isHaveDataFromSearch()) {
            isAddLocation = true
            loadInformationWeather(mCityName)
        }
        initData()
    }

    private fun initViews() {
        mViewPager = findViewById(R.id.viewPager)
        mDrawerLayout = findViewById(R.id.drawerLayout)
        mImgMenuIcon = findViewById(R.id.imgMenuIcon)
        mTvTitle = findViewById(R.id.tvTitle)
        mTvDate = findViewById(R.id.tvDate)
        mTvSetting = findViewById(R.id.tvSetting)
        mTvAddLocation = findViewById(R.id.tvAddLocation)
        mRecyclerView = findViewById(R.id.recyclerViewLocation)
    }

    private fun reloadViewPager() {
        mViewPagerAdapter.notifyDataSetChanged()
    }

    private fun reloadListCityCollection() {
        mCityCollectionAdapter.setFocusItem(mFocusName)
        mCityCollectionAdapter.notifyDataSetChanged()
    }

    private fun initData() {
        val weatherRepository = WeatherRepository(this)
        weatherRepository.getAllCityCollection(this)
        mViewPagerAdapter = ViewPagerAdapter(supportFragmentManager, mListCityCollection)
        mViewPager.adapter = mViewPagerAdapter
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                mFocusName = mListCityCollection[position].cityName
                mTvTitle.text = "$mFocusName - ${mListCityCollection[position].countryName}"
                mTvDate.text = mListCityCollection[position].date
            }
        })
        mCityCollectionAdapter = CityCollectionAdapter(mListCityCollection, this)
        mRecyclerView.adapter = mCityCollectionAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initListener() {
        mDrawerLayout.addDrawerListener(this)
        mImgMenuIcon.setOnClickListener(this)
        mTvSetting.setOnClickListener(this)
        mTvAddLocation.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imgMenuIcon -> mDrawerLayout.openDrawer(Gravity.LEFT)
            R.id.tvSetting -> goTo(SettingActivity::class.java)
            R.id.tvAddLocation -> goTo(SearchActivity::class.java)
        }
    }

    private fun goTo(markClass: Class<*>) {
        intent = Intent(this, markClass)
        startActivity(intent)
    }

    private fun isHaveDataFromSearch(): Boolean {
        val bundle = intent.extras
        if (bundle != null) {
            mCityName = bundle.getString(Constants.CITY_NAME)
            return true
        }
        return false
    }

    @SuppressLint("SetTextI18n")
    override fun onLoadListListener(listCityCollection: List<CityCollection>) {
        val sharedPreferences = getSharedPreferences(getString(R.string.shared_preference_name), Context.MODE_PRIVATE)
        mListCityCollection.clear()
        var hasFocusName = false
        var index = 0
        if (listCityCollection.isNotEmpty()) {
            mFocusName = sharedPreferences.getString(Constants.FOCUS_POSITION, listCityCollection[0].cityName)
            listCityCollection.forEach {
                mListCityCollection.add(it)
                if (mFocusName == it.cityName) {
                    hasFocusName = true
                    index = listCityCollection.indexOf(it)
                }
            }
            if (!hasFocusName) {
                mFocusName = listCityCollection[0].cityName
            }
            reloadViewPager()
            mViewPager.currentItem = index
            mTvTitle.text = "$mFocusName - ${mListCityCollection[index].countryName}"
            mTvDate.text = mListCityCollection[index].date
        }
        if (isOnline() && !isNewData) {
            mListCityCollection.forEach { loadInformationWeather(it.cityName) }
            isNewData = true
            initData()
        }
        reloadListCityCollection()
    }

    private fun addNewCityCollection(cityCollection: CityCollection) {
        var index: Int? = null
        for (city in mListCityCollection) {
            if (city.cityName == cityCollection.cityName) {
                index = mListCityCollection.indexOf(city)
                mFocusName = cityCollection.cityName
                mViewPager.currentItem = index
                break
            }
        }
        if (index == null) {
            mListCityCollection.add(cityCollection)
            mFocusName = cityCollection.cityName
            reloadListCityCollection()
            reloadViewPager()
            mViewPager.currentItem = mListCityCollection.size - 1
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onDeleteCityCollection(cityCollection: CityCollection) {
//        val weatherRepository = WeatherRepository(this)
//        weatherRepository.delete(cityCollection)
        if (mListCityCollection.size > 1) {
            if (cityCollection.cityName == mFocusName) {
                mListCityCollection.remove(cityCollection)
                reloadViewPager()
                mViewPager.currentItem = 0
                mFocusName = mListCityCollection[0].cityName
                mTvTitle.text = "$mFocusName - ${mListCityCollection[0].countryName}"
                mTvDate.text = mListCityCollection[0].date
                reloadListCityCollection()
            } else {
                if (mViewPager.currentItem > mListCityCollection.indexOf(cityCollection)) {
                    mViewPager.currentItem = mListCityCollection.indexOf(cityCollection) + 1
                }
                mListCityCollection.remove(cityCollection)
                reloadListCityCollection()
                reloadViewPager()
            }
        } else {
            mListCityCollection.clear()
            goTo(SearchActivity::class.java)
        }
    }

    override fun onChangeShowCityCollection(cityCollection: CityCollection) {
        mFocusName = cityCollection.cityName
        mViewPager.currentItem = mListCityCollection.indexOf(cityCollection)
    }

    override fun onDrawerStateChanged(newState: Int) {
        reloadListCityCollection()
    }

    override fun onDrawerOpened(drawerView: View) {
    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
    }

    override fun onDrawerClosed(drawerView: View) {
    }

    private fun loadInformationWeather(cityName: String) {
        val apiServices = ApiServices()
        apiServices.getIEventWeather().getInformationWeather(cityName, Constants.KEY).enqueue(object : Callback<InformationWeather> {
            override fun onResponse(call: Call<InformationWeather>, response: Response<InformationWeather>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        saveNewCityCollection(it)
                    }
                } else {
                    Toast.makeText(baseContext, R.string.city_not_found, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<InformationWeather>, t: Throwable) {
                Toast.makeText(baseContext, R.string.notification, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveNewCityCollection(informationWeather: InformationWeather) {
        val weatherRepository = WeatherRepository(applicationContext)
        val cityCollection = CityCollection()
        cityCollection.cityName = informationWeather.data[0].cityName
        cityCollection.countryName = informationWeather.data[0].countryCode
        cityCollection.temp = informationWeather.data[0].temp
        cityCollection.appTemp = informationWeather.data[0].appTemp
        cityCollection.sunrise = informationWeather.data[0].sunrise
        cityCollection.sunset = informationWeather.data[0].sunset
        cityCollection.humidity = informationWeather.data[0].humidity
        cityCollection.wind = informationWeather.data[0].windSpeed
        cityCollection.cloud = informationWeather.data[0].clouds
        cityCollection.description = informationWeather.data[0].weather.description
        cityCollection.icon = informationWeather.data[0].weather.icon
        cityCollection.date = formatDate(informationWeather.data[0].timeStamp)
        weatherRepository.insert(cityCollection)
        if (isAddLocation) {
            isAddLocation = false
            addNewCityCollection(cityCollection)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatDate(timeStamp: Int): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = Date(timeStamp * 1000L)
        return simpleDateFormat.format(date)
    }

    private fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    override fun onStop() {
        super.onStop()
        val editor = getSharedPreferences(getString(R.string.shared_preference_name), Context.MODE_PRIVATE).edit()
        editor.putString(Constants.FOCUS_POSITION, mFocusName)
        editor.apply()
    }
}
