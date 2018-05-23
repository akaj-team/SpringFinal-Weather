package vn.asiantech.android.springfinalweather.kotlin.activity

import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import vn.asiantech.android.springfinalweather.R

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private lateinit var mImgMenuIcon: ImageView
    private lateinit var mTvTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        initViews()
        initListener()
    }

    private fun initViews() {
        mDrawerLayout = findViewById(R.id.drawerLayout)
        mDrawerToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)
        mImgMenuIcon = findViewById(R.id.imgMenuIcon)
        mTvTitle = findViewById(R.id.tvTitle)
    }

    private fun initListener() {
        mDrawerLayout.addDrawerListener(mDrawerToggle)
        mImgMenuIcon.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        mDrawerLayout.openDrawer(Gravity.LEFT)
    }
}
