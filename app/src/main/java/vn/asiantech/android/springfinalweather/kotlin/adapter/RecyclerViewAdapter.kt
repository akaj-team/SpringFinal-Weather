package vn.asiantech.android.springfinalweather.kotlin.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import vn.asiantech.android.springfinalweather.R
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.model.InformationDetail

class RecyclerViewAdapter(private val informationDetail: List<InformationDetail>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_weather_forecast, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return informationDetail.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(informationDetail[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var mTvDate: TextView = itemView.findViewById(R.id.tvDate)
        private var mTvDateStatus: TextView = itemView.findViewById(R.id.tvDateStatus)
        private var mImgIconDate: ImageView = itemView.findViewById(R.id.imgIconDate)
        private var mTvMaxTempList: TextView = itemView.findViewById(R.id.tvMaxTempList)
        private var mTvMinTempList: TextView = itemView.findViewById(R.id.tvMinTempList)

        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        fun bind(informationDetail: InformationDetail) {
            mTvDate.text = informationDetail.datetime
            mTvDateStatus.text = informationDetail.weather.description
            mTvMaxTempList.text = informationDetail.maxTemp.toFloat().toString() + "°C"
            mTvMinTempList.text = informationDetail.minTemp.toFloat().toString() + "°C"
            val icon = informationDetail.weather.icon
            mImgIconDate.setImageResource(getIcon(icon))
        }

        private fun getIcon(icon: String): Int {
            when (icon) {
                Constants.ICON_T01D -> return R.drawable.img_11d
                Constants.ICON_T01N -> return R.drawable.img_11n
                Constants.ICON_T02D -> return R.drawable.img_11d
                Constants.ICON_T02N -> return R.drawable.img_11n
                Constants.ICON_T03D -> return R.drawable.img_11d
                Constants.ICON_T03N -> return R.drawable.img_11n
                Constants.ICON_T04D -> return R.drawable.img_11d
                Constants.ICON_T04N -> return R.drawable.img_11n
                Constants.ICON_T05D -> return R.drawable.img_11d
                Constants.ICON_T05N -> return R.drawable.img_11n
                Constants.ICON_D01D -> return R.drawable.img_09d
                Constants.ICON_D01N -> return R.drawable.img_09n
                Constants.ICON_D02D -> return R.drawable.img_09d
                Constants.ICON_D02N -> return R.drawable.img_09n
                Constants.ICON_D03D -> return R.drawable.img_09d
                Constants.ICON_D03N -> return R.drawable.img_09n
                Constants.ICON_R01D -> return R.drawable.img_10d
                Constants.ICON_R01N -> return R.drawable.img_10n
                Constants.ICON_R02D -> return R.drawable.img_10d
                Constants.ICON_R02N -> return R.drawable.img_10n
                Constants.ICON_R03D -> return R.drawable.img_10d
                Constants.ICON_R03N -> return R.drawable.img_10n
                Constants.ICON_F01D -> return R.drawable.img_13d
                Constants.ICON_F01N -> return R.drawable.img_13n
                Constants.ICON_R04D -> return R.drawable.img_09d
                Constants.ICON_R04N -> return R.drawable.img_09n
                Constants.ICON_R05D -> return R.drawable.img_09d
                Constants.ICON_R05N -> return R.drawable.img_09n
                Constants.ICON_R06D -> return R.drawable.img_09d
                Constants.ICON_R06N -> return R.drawable.img_09n
                Constants.ICON_S01D -> return R.drawable.img_13d
                Constants.ICON_S01N -> return R.drawable.img_13n
                Constants.ICON_S02D -> return R.drawable.img_13d
                Constants.ICON_S02N -> return R.drawable.img_13n
                Constants.ICON_S03D -> return R.drawable.img_13d
                Constants.ICON_S03N -> return R.drawable.img_13n
                Constants.ICON_S04D -> return R.drawable.img_13d
                Constants.ICON_S04N -> return R.drawable.img_13n
                Constants.ICON_S05D -> return R.drawable.img_13d
                Constants.ICON_S05N -> return R.drawable.img_13n
                Constants.ICON_S06D -> return R.drawable.img_13d
                Constants.ICON_S06N -> return R.drawable.img_13n
                Constants.ICON_A01D -> return R.drawable.img_01d
                Constants.ICON_A01N -> return R.drawable.img_01n
                Constants.ICON_A02D -> return R.drawable.img_50d
                Constants.ICON_A02N -> return R.drawable.img_50n
                Constants.ICON_A03D -> return R.drawable.img_50d
                Constants.ICON_A03N -> return R.drawable.img_50n
                Constants.ICON_A04D -> return R.drawable.img_50d
                Constants.ICON_A04N -> return R.drawable.img_50n
                Constants.ICON_A05D -> return R.drawable.img_50d
                Constants.ICON_A05N -> return R.drawable.img_50n
                Constants.ICON_A06D -> return R.drawable.img_50d
                Constants.ICON_A06N -> return R.drawable.img_50n
                Constants.ICON_C01D -> return R.drawable.img_50d
                Constants.ICON_C01N -> return R.drawable.img_50n
                Constants.ICON_C02D -> return R.drawable.img_02d
                Constants.ICON_C02N -> return R.drawable.img_02n
                Constants.ICON_C03D -> return R.drawable.img_03d
                Constants.ICON_C03N -> return R.drawable.img_03n
                Constants.ICON_U00D -> return R.drawable.img_13d
                Constants.ICON_U00N -> return R.drawable.img_13n
                Constants.ICON_C04D -> return R.drawable.img_04d
                Constants.ICON_C04N -> return R.drawable.img_04n
                else -> return R.drawable.img_sun
            }
        }
    }
}
