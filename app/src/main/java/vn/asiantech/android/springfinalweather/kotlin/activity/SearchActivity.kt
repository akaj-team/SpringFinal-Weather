package vn.asiantech.android.springfinalweather.kotlin.activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.asiantech.android.springfinalweather.R
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.`object`.Dimen
import vn.asiantech.android.springfinalweather.kotlin.adapter.CityPredictionAdapter
import vn.asiantech.android.springfinalweather.kotlin.apiservice.ApiCityService
import vn.asiantech.android.springfinalweather.kotlin.model.City
import vn.asiantech.android.springfinalweather.kotlin.myinterface.CityApi
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnSelectCityListener

class SearchActivity : AppCompatActivity(),
        View.OnClickListener, TextWatcher, Callback<List<City>>, OnSelectCityListener {
    private lateinit var cityPredictionAdapter: CityPredictionAdapter
    private lateinit var cityApi: CityApi
    private var listCity: MutableList<City> = mutableListOf()
    private var granted = false
    private var canBack = true
    private var cityMyLocation = City()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initViews()
        initListener()
        initData()
    }

    private fun initViews() {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        toolBarSearch.setPadding(0, Dimen.getStatusBarHeight(this) * 3 / 2, 0, Dimen.getStatusBarHeight(this) / 2)
    }

    private fun initListener() {
        imgIconBack.setOnClickListener(this)
        edtCityName.addTextChangedListener(this)
    }

    private fun initData() {
        canBack = intent.getBooleanExtra(Constants.CAN_BACK, true)
        if (!canBack) {
            imgIconBack.visibility = View.INVISIBLE
        }
        val sharedPreferences = getSharedPreferences(getString(R.string.shared_preference_name), Context.MODE_PRIVATE)
        granted = sharedPreferences.getBoolean(Constants.LOCATION_PERMISSION, false)
        cityMyLocation.name = getString(R.string.get_my_location)
        if (!granted) {
            listCity.add(cityMyLocation)
        }
        cityPredictionAdapter = CityPredictionAdapter(listCity, this, this)
        recyclerViewResultLocation.adapter = cityPredictionAdapter
        recyclerViewResultLocation.layoutManager = LinearLayoutManager(this)
        cityApi = ApiCityService().getCityApi()
    }

    override fun onClick(v: View?) {
        if (canBack) {
            onBackPressed()
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val callCity: Call<List<City>> = cityApi.getListCity(s.toString())
        callCity.enqueue(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onResponse(call: Call<List<City>>?, response: Response<List<City>>) {
        if (response.isSuccessful) {
            listCity.clear()
            if (!granted) {
                listCity.add(cityMyLocation)
            }
            response.body()?.forEach { listCity.add(it) }
            cityPredictionAdapter.notifyDataSetChanged()
        } else {
            Toast.makeText(this, getString(R.string.response_fail), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onFailure(call: Call<List<City>>?, t: Throwable?) {
        Toast.makeText(this, getString(R.string.connect_fail), Toast.LENGTH_SHORT).show()
    }

    override fun onCitySelected(cityName: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Constants.CITY_NAME, cityName)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (isOnline()) {
                val editor = getSharedPreferences(getString(R.string.shared_preference_name), Context.MODE_PRIVATE).edit()
                editor.putBoolean(Constants.LOCATION_PERMISSION, true)
                editor.apply()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(Constants.FIND_LOCATION, true)
                startActivity(intent)
            } else {
                Toast.makeText(this, R.string.connect_fail, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}
