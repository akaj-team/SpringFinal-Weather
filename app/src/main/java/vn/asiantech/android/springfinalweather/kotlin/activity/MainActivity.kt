package vn.asiantech.android.springfinalweather.kotlin.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.asiantech.android.springfinalweather.R
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.`object`.Dimen
import vn.asiantech.android.springfinalweather.kotlin.`object`.Image
import vn.asiantech.android.springfinalweather.kotlin.adapter.CityCollectionAdapter
import vn.asiantech.android.springfinalweather.kotlin.adapter.ViewPagerAdapter
import vn.asiantech.android.springfinalweather.kotlin.apiservice.ApiCityService
import vn.asiantech.android.springfinalweather.kotlin.model.CityCollection
import vn.asiantech.android.springfinalweather.kotlin.model.InformationWeather
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnCityCollectionAsyncListener
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnCityCollectionChangeListener
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnInsertDoneListener
import vn.asiantech.android.springfinalweather.kotlin.room.WeatherRepository


class MainActivity : AppCompatActivity(),
        View.OnClickListener, OnCityCollectionAsyncListener, OnCityCollectionChangeListener,
        DrawerLayout.DrawerListener, Callback<InformationWeather>, OnInsertDoneListener {
    private lateinit var mToolBar: Toolbar
    private lateinit var mRlDrawer: RelativeLayout
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mImgMenuIcon: ImageView
    private lateinit var mTvTitle: TextView
    private lateinit var mTvTitleDrawer: TextView
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
    private var mIsNewData = false
    private var mIsAddNewCity = false
    private var mLocation = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initListener()
        initData()
        when {
            intent.getBooleanExtra(Constants.FINDLOCATION, false) -> checkLocationPermission()
            isHaveDataFromSearch() -> {
                mIsAddNewCity = true
                loadInformationWeather(mCityName)
            }
            else -> initDataFromDatabase()
        }
    }

    @SuppressLint("MissingPermission")
    private fun checkLocationPermission() {
        val granted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val editor = getSharedPreferences(getString(R.string.shared_preference_name), Context.MODE_PRIVATE).edit()
        editor.putBoolean(Constants.LOCATION_PERMISSION, granted)
        editor.apply()
        if (granted) {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val apiServices = ApiCityService()
            apiServices.getCityApi()
                    .getCurrentWeather("${location.latitude},${location.longitude}")
                    .enqueue(object : Callback<InformationWeather> {
                        override fun onResponse(call: Call<InformationWeather>, response: Response<InformationWeather>) {
                            if (response.isSuccessful) {
                                response.body()?.let {
                                    mIsAddNewCity = true
                                    saveNewCityCollection(it, Constants.USER_LOCATION)
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

    }

    private fun initViews() {
        val w = window
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        mToolBar = findViewById(R.id.toolBar)
        mToolBar.setPadding(0, Dimen.getStatusBarHeight(this), 0, Dimen.getStatusBarHeight(this))
        mViewPager = findViewById(R.id.viewPager)
        mDrawerLayout = findViewById(R.id.drawerLayout)
        mImgMenuIcon = findViewById(R.id.imgMenuIcon)
        mTvTitle = findViewById(R.id.tvTitle)
        mTvTitleDrawer = findViewById(R.id.tvTitleDrawer)
        mTvTitleDrawer.setPadding(50, Dimen.getStatusBarHeight(this), 0, Dimen.getStatusBarHeight(this)/2)
        mTvDate = findViewById(R.id.tvDate)
        mTvSetting = findViewById(R.id.tvSetting)
        mRlDrawer = findViewById(R.id.rlDrawer)
        mRlDrawer.setPadding(0, 0, 0, Dimen.getNavigationBarHeight(this))
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
        mCityCollectionAdapter = CityCollectionAdapter(mListCityCollection, this)
        mRecyclerView.adapter = mCityCollectionAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mViewPagerAdapter = ViewPagerAdapter(supportFragmentManager, mListCityCollection)
        mViewPager.adapter = mViewPagerAdapter
    }

    private fun initDataFromDatabase() {
        val sharedPreferences = getSharedPreferences(getString(R.string.shared_preference_name), Context.MODE_PRIVATE)
        mLocation = sharedPreferences.getString(Constants.NAME_LOCATION, "")
        val weatherRepository = WeatherRepository(this)
        weatherRepository.getAllCityCollection(this)
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
                mDrawerLayout.setBackgroundResource(Image.getBackground(
                        mListCityCollection[position].icon,
                        mListCityCollection[position].day
                ))
            }
        })
        reloadListCityCollection()
        reloadViewPager()
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

    private fun goTo(markClass: Class<*>, canBack: Boolean = true) {
        intent = Intent(this, markClass)
        intent.putExtra(Constants.CANBACK, canBack)
        startActivity(intent)
    }

    private fun isHaveDataFromSearch(): Boolean {
        val bundle = intent.extras
        if (bundle != null) {
            mCityName = bundle.getString(Constants.CITY_NAME, "")
            if (mCityName.isNotEmpty()) {
                return true
            }
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
            mDrawerLayout.setBackgroundResource(Image.getBackground(
                    mListCityCollection[index].icon,
                    mListCityCollection[index].day
            ))
        } else {
            goTo(SearchActivity::class.java, false)
        }
        if (isOnline() && !mIsNewData) {
            mListCityCollection.forEach {
                loadInformationWeather(it.cityName)
            }
            mIsNewData = true
            initDataFromDatabase()
        }
        reloadListCityCollection()
    }

    private fun addNewCityCollection(cityCollection: CityCollection) {
        var index: Int? = null
        for (city in mListCityCollection) {
            if (city.cityName == cityCollection.cityName) {
                index = mListCityCollection.indexOf(city)
                mFocusName = cityCollection.cityName
                city.state = cityCollection.state
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
        val weatherRepository = WeatherRepository(this)
        weatherRepository.delete(cityCollection)
        if (mListCityCollection.size > 1) {
            if (cityCollection.cityName == mFocusName) {
                mListCityCollection.remove(cityCollection)
                reloadViewPager()
                mViewPager.currentItem = 0
                mFocusName = mListCityCollection[0].cityName
                mTvTitle.text = "$mFocusName - ${mListCityCollection[0].countryName}"
                mTvDate.text = mListCityCollection[0].date
                mDrawerLayout.setBackgroundResource(Image.getBackground(
                        mListCityCollection[0].icon,
                        mListCityCollection[0].day
                ))
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
            reloadListCityCollection()
            reloadViewPager()
            goTo(SearchActivity::class.java, false)
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
        val apiServices = ApiCityService()
        apiServices.getCityApi().getCurrentWeather(cityName).enqueue(this)
    }

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

    private fun saveNewCityCollection(informationWeather: InformationWeather, state: Boolean = Constants.OTHER_LOCATION) {
        val weatherRepository = WeatherRepository(applicationContext)
        val cityCollection = CityCollection()
        cityCollection.cityName = informationWeather.location.name
        cityCollection.countryName = informationWeather.location.country
        cityCollection.temp = informationWeather.current.temp
        cityCollection.appTemp = informationWeather.current.appTemp
        cityCollection.humidity = informationWeather.current.humidity
        cityCollection.wind = informationWeather.current.windSpeed
        cityCollection.cloud = informationWeather.current.cloud
        cityCollection.description = informationWeather.current.condition.description
        cityCollection.icon = informationWeather.current.condition.icon
        cityCollection.date = informationWeather.current.date
        cityCollection.day = informationWeather.current.isDay
        if (mLocation == cityCollection.cityName) {
            cityCollection.state = true
        }
        if (state) {
            cityCollection.state = true
            val editor = getSharedPreferences(getString(R.string.shared_preference_name), Context.MODE_PRIVATE).edit()
            editor.putString(Constants.NAME_LOCATION, cityCollection.cityName)
            editor.apply()
        }
        weatherRepository.insert(cityCollection, this)
    }

    override fun onInsertDone(cityCollection: CityCollection) {
        if (mIsAddNewCity) {
            mIsAddNewCity = false
            addNewCityCollection(cityCollection)
        }
        initDataFromDatabase()
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
