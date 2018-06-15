package vn.asiantech.android.springfinalweather.kotlin.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
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
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var cityName: String
    private lateinit var cityCollectionAdapter: CityCollectionAdapter
    private var listCityCollection: MutableList<CityCollection> = mutableListOf()
    private var focusName: String = ""
    private var isNewData = false
    private var isAddNewCity = false
    private var location = ""
    private lateinit var dialogLoading: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initListener()
        initData()
        when {
            intent.getBooleanExtra(Constants.FIND_LOCATION, false) -> {
                if (isOnline()) {
                    checkLocationPermission()
                } else {
                    Toast.makeText(this, R.string.connect_fail, Toast.LENGTH_SHORT).show()
                    initDataFromDatabase()
                }
            }
            isHaveDataFromSearch() -> {
                if (isOnline()) {
                    isAddNewCity = true
                    loadInformationWeather(cityName)
                } else {
                    Toast.makeText(this, R.string.connect_fail, Toast.LENGTH_SHORT).show()
                    initDataFromDatabase()
                }
            }
            else -> initDataFromDatabase()
        }
    }

    @SuppressLint("MissingPermission")
    private fun checkLocationPermission() {
        dialogLoading.show()
        val granted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        setGranted(granted)
        if (granted) {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val providers = locationManager.getProviders(true)
            var location: Location? = null
            for (provider in providers) {
                location = locationManager.getLastKnownLocation(provider)
                if (location == null) {
                    continue
                }
                break
            }
            if (location != null) {
                val apiServices = ApiCityService()
                apiServices.getCityApi()
                        .getCurrentWeather("${location.latitude},${location.longitude}")
                        .enqueue(object : Callback<InformationWeather> {
                            override fun onResponse(call: Call<InformationWeather>, response: Response<InformationWeather>) {
                                if (response.isSuccessful) {
                                    response.body()?.let {
                                        isAddNewCity = true
                                        saveNewCityCollection(it, Constants.USER_LOCATION)
                                    }
                                } else {
                                    Toast.makeText(this@MainActivity, R.string.city_not_found, Toast.LENGTH_SHORT).show()
                                    dialogLoading.dismiss()
                                }
                            }

                            override fun onFailure(call: Call<InformationWeather>, t: Throwable) {
                                Toast.makeText(this@MainActivity, R.string.notification, Toast.LENGTH_SHORT).show()
                                setGranted(false)
                                dialogLoading.dismiss()
                            }
                        })
            } else {
                Toast.makeText(this, R.string.city_not_found, Toast.LENGTH_SHORT).show()
                setGranted(false)
                initDataFromDatabase()
            }
        }
    }

    private fun setGranted(granted: Boolean) {
        val editor = getSharedPreferences(getString(R.string.shared_preference_name), Context.MODE_PRIVATE).edit()
        editor.putBoolean(Constants.LOCATION_PERMISSION, granted)
        editor.apply()
    }

    private fun initViews() {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        toolBar.setPadding(0, Dimen.getStatusBarHeight(this), 0, 0)
        tvTitleDrawer.setPadding(50, Dimen.getStatusBarHeight(this), 0, Dimen.getStatusBarHeight(this) / 2)
        rlDrawer.setPadding(0, 0, 0, Dimen.getNavigationBarHeight(this))
        dialogLoading = Dialog(this, R.style.Dialog)
        dialogLoading.setContentView(R.layout.dialog_waiting)
        dialogLoading.setCanceledOnTouchOutside(false)
        dialogLoading.setCancelable(false)
    }

    private fun reloadViewPager() {
        viewPagerAdapter.notifyDataSetChanged()
        viewPager.offscreenPageLimit = listCityCollection.size
    }

    private fun reloadListCityCollection() {
        cityCollectionAdapter.setFocusItem(focusName)
        cityCollectionAdapter.notifyDataSetChanged()
    }

    private fun initData() {
        val sharedPreferences = getSharedPreferences(getString(R.string.shared_preference_name), Context.MODE_PRIVATE)
        cityCollectionAdapter = CityCollectionAdapter(
                listCityCollection,
                this,
                sharedPreferences.getInt(Constants.UNIT_OF_TEMP, 0)
        )
        recyclerViewLocation.adapter = cityCollectionAdapter
        recyclerViewLocation.layoutManager = LinearLayoutManager(this)
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, listCityCollection)
        viewPager.adapter = viewPagerAdapter
    }

    private fun initDataFromDatabase() {
        val sharedPreferences = getSharedPreferences(getString(R.string.shared_preference_name), Context.MODE_PRIVATE)
        location = sharedPreferences.getString(Constants.NAME_LOCATION, "")
        val weatherRepository = WeatherRepository(this)
        weatherRepository.getAllCityCollection(this)
    }

    private fun initListener() {
        drawerLayout.addDrawerListener(this)
        imgMenuIcon.setOnClickListener(this)
        tvSetting.setOnClickListener(this)
        tvAddLocation.setOnClickListener(this)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                focusName = listCityCollection[position].cityName
                tvTitle.text = "$focusName, ${listCityCollection[position].countryName}"
                tvDate.text = Dimen.getDate(listCityCollection[position].date)
                drawerLayout.setBackgroundResource(Image.getBackground(
                        listCityCollection[position].icon,
                        listCityCollection[position].day
                ))
            }
        })
        imgRefresh.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imgMenuIcon -> drawerLayout.openDrawer(Gravity.LEFT)
            R.id.tvSetting -> goTo(SettingActivity::class.java)
            R.id.tvAddLocation -> goTo(SearchActivity::class.java)
            R.id.imgRefresh -> {
                if (isOnline()) {
                    focusName = listCityCollection[viewPager.currentItem].cityName
                    val editor = getSharedPreferences(getString(R.string.shared_preference_name), Context.MODE_PRIVATE).edit()
                    editor.putString(Constants.FOCUS_POSITION, focusName)
                    editor.apply()
                    loadInformationWeather(focusName)
                } else {
                    Toast.makeText(this, R.string.connect_fail, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goTo(markClass: Class<*>, canBack: Boolean = true) {
        intent = Intent(this, markClass)
        intent.putExtra(Constants.CAN_BACK, canBack)
        startActivity(intent)
    }

    private fun isHaveDataFromSearch(): Boolean {
        val bundle = intent.extras
        if (bundle != null) {
            cityName = bundle.getString(Constants.CITY_NAME, "")
            if (cityName.isNotEmpty()) {
                return true
            }
        }
        return false
    }

    @SuppressLint("SetTextI18n")
    override fun onLoadListListener(listCityCollection: List<CityCollection>) {
        val sharedPreferences = getSharedPreferences(getString(R.string.shared_preference_name), Context.MODE_PRIVATE)
        this.listCityCollection.clear()
        var hasFocusName = false
        var index = 0
        if (listCityCollection.isNotEmpty()) {
            focusName = sharedPreferences.getString(Constants.FOCUS_POSITION, listCityCollection[0].cityName)
            listCityCollection.forEach {
                this.listCityCollection.add(it)
                if (focusName == it.cityName) {
                    hasFocusName = true
                    index = listCityCollection.indexOf(it)
                }
            }
            if (!hasFocusName) {
                focusName = listCityCollection[0].cityName
            }
            reloadViewPager()
            viewPager.currentItem = index
            tvTitle.text = "$focusName, ${this.listCityCollection[index].countryName}"
            tvDate.text = Dimen.getDate(this.listCityCollection[index].date)
            drawerLayout.setBackgroundResource(Image.getBackground(
                    this.listCityCollection[index].icon,
                    this.listCityCollection[index].day
            ))
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    Constants.LOCATION_PERMISSION_REQUEST
            )
        }
        if (isOnline() && !isNewData && this.listCityCollection.isNotEmpty()) {
            isNewData = true
            this.listCityCollection.forEach {
                loadInformationWeather(it.cityName)
            }
        }
        reloadListCityCollection()
        dialogLoading.dismiss()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            val sharedPreferences = getSharedPreferences(
                    getString(R.string.shared_preference_name),
                    Context.MODE_PRIVATE
            )
            val granted = sharedPreferences.getBoolean(Constants.LOCATION_PERMISSION, true)
            if (isOnline()) {
                if (granted) {
                    checkLocationPermission()
                } else {
                    goTo(SearchActivity::class.java, false)
                }
            } else {
                Toast.makeText(this, R.string.connect_fail, Toast.LENGTH_SHORT).show()
                goTo(SearchActivity::class.java, false)
            }
        } else {
            goTo(SearchActivity::class.java, false)
        }
    }

    private fun addNewCityCollection(cityCollection: CityCollection) {
        var index: Int? = null
        for (city in listCityCollection) {
            if (city.cityName == cityCollection.cityName) {
                index = listCityCollection.indexOf(city)
                focusName = cityCollection.cityName
                break
            }
        }
        if (index == null) {
            listCityCollection.add(cityCollection)
            focusName = cityCollection.cityName
        }
        val editor = getSharedPreferences(getString(R.string.shared_preference_name), Context.MODE_PRIVATE).edit()
        editor.putString(Constants.FOCUS_POSITION, focusName)
        editor.apply()
    }

    @SuppressLint("SetTextI18n")
    override fun onDeleteCityCollection(cityCollection: CityCollection) {
        val weatherRepository = WeatherRepository(this)
        weatherRepository.delete(cityCollection)
        if (listCityCollection.size > 1) {
            if (cityCollection.cityName == focusName) {
                listCityCollection.remove(cityCollection)
                reloadViewPager()
                viewPager.currentItem = 0
                focusName = listCityCollection[0].cityName
                tvTitle.text = "$focusName, ${listCityCollection[0].countryName}"
                tvDate.text = Dimen.getDate(listCityCollection[0].date)
                drawerLayout.setBackgroundResource(Image.getBackground(
                        listCityCollection[0].icon,
                        listCityCollection[0].day
                ))
                reloadListCityCollection()
            } else {
                if (viewPager.currentItem > listCityCollection.indexOf(cityCollection)) {
                    viewPager.currentItem = listCityCollection.indexOf(cityCollection) + 1
                }
                listCityCollection.remove(cityCollection)
                reloadListCityCollection()
                reloadViewPager()
            }
        } else {
            listCityCollection.clear()
            reloadListCityCollection()
            reloadViewPager()
            goTo(SearchActivity::class.java, false)
        }
    }

    override fun onChangeShowCityCollection(cityCollection: CityCollection) {
        focusName = cityCollection.cityName
        viewPager.currentItem = listCityCollection.indexOf(cityCollection)
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
        dialogLoading.show()
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
            dialogLoading.dismiss()
        }
    }

    override fun onFailure(call: Call<InformationWeather>, t: Throwable) {
        Toast.makeText(baseContext, R.string.notification, Toast.LENGTH_SHORT).show()
        initDataFromDatabase()
        dialogLoading.dismiss()
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
        if (location == cityCollection.cityName) {
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
        if (isAddNewCity) {
            isAddNewCity = false
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
        editor.putString(Constants.FOCUS_POSITION, focusName)
        editor.apply()
    }
}
