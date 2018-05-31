package vn.asiantech.android.springfinalweather.kotlin.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import vn.asiantech.android.springfinalweather.R
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.room.WeatherRepository

class SettingActivity : AppCompatActivity(),
        View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private lateinit var mImgBack: ImageView
    private lateinit var mSwitchAllowCurrentLocation: Switch
    private lateinit var mSharedPreferences: SharedPreferences
    private lateinit var mEditor: SharedPreferences.Editor
    private lateinit var mUnitDialog: AlertDialog
    private lateinit var mLlUnitOfTemp: LinearLayout
    private lateinit var mLlUnitOfWindSpeed: LinearLayout
    private lateinit var mTvUnitOfTemp: TextView
    private lateinit var mTvUnitOfWindSpeed: TextView
    private var mCheckedItemTemp: Int = 0
    private var mCheckedItemWindSpeed: Int = 0
    private val mItemsUnitOfTemp = arrayOf("C degree", "F degree")
    private val mItemsUnitOfWindSpeed = arrayOf("km/h", "m/s")
    private var mGranted = false
    private var mInteractive = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initViews()
        initListener()
        initData()
    }

    private fun initViews() {
        mImgBack = findViewById(R.id.imgIconBack)
        mSwitchAllowCurrentLocation = findViewById(R.id.switchAllowCurrentLocation)
        mLlUnitOfTemp = findViewById(R.id.llUnitOfTemp)
        mLlUnitOfWindSpeed = findViewById(R.id.llUnitOfWindSpeed)
        mTvUnitOfTemp = findViewById(R.id.tvUnitOfTemp)
        mTvUnitOfWindSpeed = findViewById(R.id.tvUnitOfWindSpeed)
    }

    private fun initListener() {
        mImgBack.setOnClickListener(this)
        mSwitchAllowCurrentLocation.setOnCheckedChangeListener(this)
        mLlUnitOfTemp.setOnClickListener(this)
        mLlUnitOfWindSpeed.setOnClickListener(this)
    }

    private fun initData() {
        mSharedPreferences = getSharedPreferences(
                getString(R.string.shared_preference_name),
                Context.MODE_PRIVATE
        )
        mEditor = mSharedPreferences.edit()
        mGranted = mSharedPreferences.getBoolean(Constants.LOCATION_PERMISSION, false)
        mSwitchAllowCurrentLocation.isChecked = mGranted
        mCheckedItemTemp = mSharedPreferences.getInt(Constants.UNIT_OF_TEMP, 0)
        mCheckedItemWindSpeed = mSharedPreferences.getInt(Constants.UNIT_OF_WIND_SPEED, 0)
        mTvUnitOfTemp.text = mItemsUnitOfTemp[mCheckedItemTemp]
        mTvUnitOfWindSpeed.text = mItemsUnitOfWindSpeed[mCheckedItemWindSpeed]
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imgIconBack -> {
                val intent = Intent(this, MainActivity::class.java)
                mGranted = mSharedPreferences.getBoolean(Constants.LOCATION_PERMISSION, false)
                if (mInteractive > 1) {
                    intent.putExtra(Constants.FINDLOCATION, mGranted)
                }
                if (!mGranted) {
                    val weatherRepository = WeatherRepository(this)
                    weatherRepository.deleteLocation()
                }
                startActivity(intent)
            }
            R.id.llUnitOfTemp -> showDialog(
                    mItemsUnitOfTemp,
                    Constants.UNIT_OF_TEMP,
                    R.string.temperature,
                    mTvUnitOfTemp
            )
            R.id.llUnitOfWindSpeed -> showDialog(
                    mItemsUnitOfWindSpeed,
                    Constants.UNIT_OF_WIND_SPEED,
                    R.string.wind_speed,
                    mTvUnitOfWindSpeed
            )
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        mInteractive++
        if (isChecked && !mGranted) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    Constants.LOCATION_PERMISSION_REQUEST
            )
        } else {
            mEditor.putBoolean(Constants.LOCATION_PERMISSION, isChecked)
            mEditor.apply()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mGranted = true
            mEditor.putBoolean(Constants.LOCATION_PERMISSION, mGranted)
            mEditor.apply()
        } else {
            mGranted = false
            mSwitchAllowCurrentLocation.isChecked = false
        }
    }

    private fun showDialog(items: Array<String>, key: String, titleId: Int, textView: TextView) {
        val builder = AlertDialog.Builder(this)
        val checkedItem = mSharedPreferences.getInt(key, 0)
        builder.setTitle(titleId)
        builder.setSingleChoiceItems(items, checkedItem) { _, which ->
            mEditor.putInt(key, which)
            mEditor.apply()
            textView.text = items[which]
            mUnitDialog.dismiss()
        }
        mUnitDialog = builder.create()
        mUnitDialog.show()
    }
}
