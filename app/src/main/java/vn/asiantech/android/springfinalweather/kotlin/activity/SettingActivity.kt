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
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var unitDialog: AlertDialog
    private var checkedItemTemp: Int = 0
    private var checkedItemWindSpeed: Int = 0
    private val itemsUnitOfTemp = arrayOf("C degree", "F degree")
    private val itemsUnitOfWindSpeed = arrayOf("km/h", "m/s")
    private var granted = false

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
        sharedPreferences = getSharedPreferences(
                getString(R.string.shared_preference_name),
                Context.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()
        granted = sharedPreferences.getBoolean(Constants.LOCATION_PERMISSION, false)
        switchAllowCurrentLocation.isChecked = granted
        checkedItemTemp = sharedPreferences.getInt(Constants.UNIT_OF_TEMP, 0)
        checkedItemWindSpeed = sharedPreferences.getInt(Constants.UNIT_OF_WIND_SPEED, 0)
        tvUnitOfTemp.text = itemsUnitOfTemp[checkedItemTemp]
        tvUnitOfWindSpeed.text = itemsUnitOfWindSpeed[checkedItemWindSpeed]
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imgIconBack -> {
                val intent = Intent(this, MainActivity::class.java)
                granted = sharedPreferences.getBoolean(Constants.LOCATION_PERMISSION, false)
                intent.putExtra(Constants.FIND_LOCATION, granted)
                if (!granted) {
                    val weatherRepository = WeatherRepository(this)
                    weatherRepository.deleteLocation()
                }
                startActivity(intent)
            }
            R.id.llUnitOfTemp -> showDialog(
                    itemsUnitOfTemp,
                    Constants.UNIT_OF_TEMP,
                    R.string.temperature,
                    tvUnitOfTemp
            )
            R.id.llUnitOfWindSpeed -> showDialog(
                    itemsUnitOfWindSpeed,
                    Constants.UNIT_OF_WIND_SPEED,
                    R.string.wind_speed,
                    tvUnitOfWindSpeed
            )
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        granted = sharedPreferences.getBoolean(Constants.LOCATION_PERMISSION, false)
        intent.putExtra(Constants.FIND_LOCATION, granted)
        if (!granted) {
            val weatherRepository = WeatherRepository(this)
            weatherRepository.deleteLocation()
        }
        startActivity(intent)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked && !granted) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    Constants.LOCATION_PERMISSION_REQUEST
            )
        } else {
            editor.putBoolean(Constants.LOCATION_PERMISSION, isChecked)
            editor.apply()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            granted = true
            editor.putBoolean(Constants.LOCATION_PERMISSION, granted)
            editor.apply()
        } else {
            granted = false
            switchAllowCurrentLocation.isChecked = false
        }
    }

    private fun showDialog(items: Array<String>, key: String, titleId: Int, textView: TextView) {
        val builder = AlertDialog.Builder(this)
        val checkedItem = sharedPreferences.getInt(key, 0)
        builder.setTitle(titleId)
        builder.setSingleChoiceItems(items, checkedItem) { _, which ->
            editor.putInt(key, which)
            editor.apply()
            textView.text = items[which]
            unitDialog.dismiss()
        }
        unitDialog = builder.create()
        unitDialog.show()
    }
}
