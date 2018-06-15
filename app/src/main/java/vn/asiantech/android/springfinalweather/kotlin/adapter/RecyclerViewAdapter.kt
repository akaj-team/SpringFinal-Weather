package vn.asiantech.android.springfinalweather.kotlin.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import vn.asiantech.android.springfinalweather.R
import vn.asiantech.android.springfinalweather.kotlin.`object`.Dimen
import vn.asiantech.android.springfinalweather.kotlin.`object`.Image
import vn.asiantech.android.springfinalweather.kotlin.model.CityWeather

class RecyclerViewAdapter(private val listCityWeather: List<CityWeather>, private var unitOfTemp: Int?) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_weather_forecast, parent, false)
        return ViewHolder(view, unitOfTemp)
    }

    override fun getItemCount(): Int {
        return listCityWeather.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listCityWeather[position])
    }

    class ViewHolder(itemView: View, private var unitOfTemp: Int?) : RecyclerView.ViewHolder(itemView) {
        private var tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private var imgIconDate: ImageView = itemView.findViewById(R.id.imgIconDate)
        private var tvMaxTempList: TextView = itemView.findViewById(R.id.tvMaxTempList)
        private var tvMinTempList: TextView = itemView.findViewById(R.id.tvMinTempList)

        @SuppressLint("SetTextI18n")
        fun bind(cityWeather: CityWeather) {
            tvDate.text = Dimen.getDateFormat(cityWeather.date)
            if (unitOfTemp == 0) {
                tvMaxTempList.text = cityWeather.tempMin.toInt().toString() + "°C"
                tvMinTempList.text = "/  " + cityWeather.tempMax.toInt().toString() + "°C"
            } else {
                tvMaxTempList.text = getFahrenheitDegree(cityWeather.tempMin).toInt().toString() + "°F"
                tvMinTempList.text = "/  " + getFahrenheitDegree(cityWeather.tempMax).toInt().toString() + "°F"
            }
            val icon = cityWeather.icon
            imgIconDate.setImageResource(Image.getIcon(icon, 1))
        }

        private fun getFahrenheitDegree(fah: Float): Float {
            val fahrenheit = fah.times(9).div(5) + 32
            val result = Math.round(fahrenheit.times(10)) / 10.0
            return result.toFloat()
        }
    }
}
