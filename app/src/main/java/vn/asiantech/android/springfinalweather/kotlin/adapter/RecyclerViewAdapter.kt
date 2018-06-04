package vn.asiantech.android.springfinalweather.kotlin.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import vn.asiantech.android.springfinalweather.R
import vn.asiantech.android.springfinalweather.kotlin.`object`.Image
import vn.asiantech.android.springfinalweather.kotlin.model.CityWeather

class RecyclerViewAdapter(private val mListCityWeather: List<CityWeather>, private var unitOfTemp: Int?) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_weather_forecast, parent, false)
        return ViewHolder(view, unitOfTemp)
    }

    override fun getItemCount(): Int {
        return mListCityWeather.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mListCityWeather[position])
    }

    class ViewHolder(itemView: View, private var unitOfTemp: Int?) : RecyclerView.ViewHolder(itemView) {
        private var mTvDate: TextView = itemView.findViewById(R.id.tvDate)
        private var mImgIconDate: ImageView = itemView.findViewById(R.id.imgIconDate)
        private var mTvMaxTempList: TextView = itemView.findViewById(R.id.tvMaxTempList)
        private var mTvMinTempList: TextView = itemView.findViewById(R.id.tvMinTempList)

        @SuppressLint("SetTextI18n")
        fun bind(cityWeather: CityWeather) {
            mTvDate.text = cityWeather.date
            if (unitOfTemp == 0) {
                mTvMaxTempList.text = cityWeather.tempMin.toString() + "°C"
                mTvMinTempList.text = "/ " + cityWeather.tempMax.toString() + "°C"
            } else {
                mTvMaxTempList.text = getFahrenheitDegree(cityWeather.tempMin).toString() + "°F"
                mTvMinTempList.text = "/ " + getFahrenheitDegree(cityWeather.tempMax).toString() + "°F"
            }
            val icon = cityWeather.icon
            mImgIconDate.setImageResource(Image.getIcon(icon, 1))
        }

        private fun getFahrenheitDegree(fah: Float): Float {
            val fahrenheit = fah.times(9).div(5) + 32
            val result = Math.round(fahrenheit.times(10)) / 10.0
            return result.toFloat()
        }
    }
}
