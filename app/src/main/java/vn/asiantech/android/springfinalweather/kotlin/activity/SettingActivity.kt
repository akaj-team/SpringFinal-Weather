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
import android.view.WindowManager
import android.widget.CompoundButton
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_setting.*
import vn.asiantech.android.springfinalweather.R
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.`object`.Dimen
import vn.asiantech.android.springfinalweather.kotlin.room.WeatherRepository

class SettingActivity : AppCompatActivity(),
        View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private lateinit var mSharedPreferences: SharedPreferences
    private lateinit var mEditor: SharedPreferences.Editor
    private lateinit var mUnitDialog: AlertDialog
    private var mCheckedItemTemp: Int = 0
    private var mCheckedItemWindSpeed: Int = 0
    private val mItemsUnitOfTemp = arrayOf("C degree", "F degree")
    private val mItemsUnitOfWindSpeed = arrayOf("km/h", "m/s")
    private var mGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initViews()
        initListener()
        initData()
    }

    private fun initViews() {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        toolBar.setPadding(0, Dimen.getStatusBarHeight(this) * 3 / 2, 0, Dimen.getStatusBarHeight(this) / 2)
    }

    private fun initListener() {
        imgIconBack.setOnClickListener(this)
        switchAllowCurrentLocation.setOnCheckedChangeListener(this)
        llUnitOfTemp.setOnClickListener(this)
        llUnitOfWindSpeed.setOnClickListener(this)
    }

    private fun initData() {
        mSharedPreferences = getSharedPreferences(
                getString(R.string.shared_preference_name),
                Context.MODE_PRIVATE
        )
        mEditor = mSharedPreferences.edit()
        mGranted = mSharedPreferences.getBoolean(Constants.LOCATION_PERMISSION, false)
        switchAllowCurrentLocation.isChecked = mGranted
        mCheckedItemTemp = mSharedPreferences.getInt(Constants.UNIT_OF_TEMP, 0)
        mCheckedItemWindSpeed = mSharedPreferences.getInt(Constants.UNIT_OF_WIND_SPEED, 0)
        tvUnitOfTemp.text = mItemsUnitOfTemp[mCheckedItemTemp]
        tvUnitOfWindSpeed.text = mItemsUnitOfWindSpeed[mCheckedItemWindSpeed]
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imgIconBack -> {
                val intent = Intent(this, MainActivity::class.java)
                mGranted = mSharedPreferences.getBoolean(Constants.LOCATION_PERMISSION, false)
                intent.putExtra(Constants.FINDLOCATION, mGranted)
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
                    tvUnitOfTemp
            )
            R.id.llUnitOfWindSpeed -> showDialog(
                    mItemsUnitOfWindSpeed,
                    Constants.UNIT_OF_WIND_SPEED,
                    R.string.wind_speed,
                    tvUnitOfWindSpeed
            )
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        mGranted = mSharedPreferences.getBoolean(Constants.LOCATION_PERMISSION, false)
        intent.putExtra(Constants.FINDLOCATION, mGranted)
        if (!mGranted) {
            val weatherRepository = WeatherRepository(this)
            weatherRepository.deleteLocation()
        }
        startActivity(intent)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
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
            switchAllowCurrentLocation.isChecked = false
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
