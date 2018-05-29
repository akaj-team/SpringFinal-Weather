package vn.asiantech.android.springfinalweather.kotlin.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import vn.asiantech.android.springfinalweather.R
import vn.asiantech.android.springfinalweather.kotlin.model.Detail

class RecyclerViewAdapter(private val weatherDetail: List<Detail>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_weather_forecast, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return weatherDetail.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(weatherDetail[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var mTvDate: TextView = itemView.findViewById(R.id.tvDate)
        private var mTvDateStatus: TextView = itemView.findViewById(R.id.tvDateStatus)
        private var mImgIconDate: ImageView = itemView.findViewById(R.id.imgIconDate)
        private var mTvMaxTempList: TextView = itemView.findViewById(R.id.tvMaxTempList)
        private var mTvMinTempList: TextView = itemView.findViewById(R.id.tvMinTempList)

        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        fun bind(weatherDetail: Detail) {
            mTvDate.text = weatherDetail.datetime
            mTvDateStatus.text = weatherDetail.weather.description
            mTvMaxTempList.text = weatherDetail.maxTemp.toFloat().toString() + "C"
            mTvMinTempList.text = weatherDetail.minTemp.toFloat().toString() + "C"
            val icon = weatherDetail.weather.icon
            Glide.with(itemView.context)
                    .load("https://www.weatherbit.io/static/img/icons/$icon.png")
                    .into(mImgIconDate)
        }
    }
}
