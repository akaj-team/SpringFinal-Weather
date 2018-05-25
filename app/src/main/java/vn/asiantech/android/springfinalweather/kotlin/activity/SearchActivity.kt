package vn.asiantech.android.springfinalweather.kotlin.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.asiantech.android.springfinalweather.R
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.adapter.CityPredictionAdapter
import vn.asiantech.android.springfinalweather.kotlin.apiservice.ApiCityService
import vn.asiantech.android.springfinalweather.kotlin.model.City
import vn.asiantech.android.springfinalweather.kotlin.model.CityPrediction
import vn.asiantech.android.springfinalweather.kotlin.myinterface.CityApi
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnSelectCityListener

class SearchActivity : AppCompatActivity(),
        View.OnClickListener, TextWatcher, Callback<CityPrediction>, OnSelectCityListener {
    private lateinit var mImgBack: ImageView
    private lateinit var mEdtCityName: EditText
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mCityPredictionAdapter: CityPredictionAdapter
    private lateinit var mCityApi: CityApi
    private var mListCity: MutableList<City>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initViews()
        initListener()
        initData()
    }

    private fun initViews() {
        mImgBack = findViewById(R.id.imgIconBack)
        mEdtCityName = findViewById(R.id.edtCityName)
        mRecyclerView = findViewById(R.id.recyclerViewResultLocation)
    }

    private fun initListener() {
        mImgBack.setOnClickListener(this)
        mEdtCityName.addTextChangedListener(this)
    }

    private fun initData() {
        mCityApi = ApiCityService().getCityApi()
    }

    override fun onClick(v: View?) {
        onBackPressed()
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val callCityPrediction: Call<CityPrediction> = mCityApi.getCityPrediction(s.toString())
        callCityPrediction.enqueue(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onResponse(call: Call<CityPrediction>?, response: Response<CityPrediction>?) {
        if (response?.isSuccessful == true) {
            if (mListCity == null) {
                mListCity = response.body()?.listCity?.toMutableList()
                mCityPredictionAdapter = CityPredictionAdapter(mListCity, this)
                mRecyclerView.adapter = mCityPredictionAdapter
                mRecyclerView.layoutManager = LinearLayoutManager(this)
            } else {
                mListCity?.clear()
                response.body()?.listCity?.forEach { mListCity?.add(it) }
                mCityPredictionAdapter.notifyDataSetChanged()
            }
        } else {
            Toast.makeText(this, getString(R.string.response_fail), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onFailure(call: Call<CityPrediction>?, t: Throwable?) {
        Toast.makeText(this, getString(R.string.connect_fail), Toast.LENGTH_SHORT).show()
    }

    override fun onCitySelected(cityName: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Constants.CITY_NAME, cityName)
        startActivity(intent)
    }
}
