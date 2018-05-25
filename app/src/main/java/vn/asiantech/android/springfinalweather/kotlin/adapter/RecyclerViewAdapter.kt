package vn.asiantech.android.springfinalweather.kotlin.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.asiantech.android.springfinalweather.R
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.apiservice.ApiServicesRecyclerView
import vn.asiantech.android.springfinalweather.kotlin.model.InformationWeatherRecyclerView
import java.text.SimpleDateFormat
import java.util.*

class RecyclerViewAdapter(private val listInformationWeatherRecyclerView: List<InformationWeatherRecyclerView>, private val cityName: String) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_weather_forecast, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listInformationWeatherRecyclerView.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listInformationWeatherRecyclerView[position])
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private lateinit var mContext: Context


        private var mTvDate: TextView = itemView.findViewById(R.id.tvDate)
        private var mTvDateStatus: TextView = itemView.findViewById(R.id.tvDateStatus)
        private var mImgIconDate: ImageView = itemView.findViewById(R.id.imgIconDate)
        private var mTvMaxTempList: TextView = itemView.findViewById(R.id.tvMaxTempList)
        private var mTvMinTempList: TextView = itemView.findViewById(R.id.tvMinTempList)

        @SuppressLint("SimpleDateFormat")
        private fun showInformationWeatherRecyclerView(informationWeatherRecyclerView: InformationWeatherRecyclerView) {
            val listBean = informationWeatherRecyclerView.list?.get(0)

            val simpleDateFormat = SimpleDateFormat("EEEE dd-MM-yyyy")
            val day = listBean?.dt
            val l = day?.toLong()
            val date = l?.times(1000L)?.let { Date(it) }
            val time = simpleDateFormat.format(date)
            mTvDate.text = time
            mTvDateStatus.text = listBean?.weather?.get(0)?.description
            mTvMaxTempList.text = listBean?.main?.temp_max.toString()
            mTvMinTempList.text = listBean?.main?.temp_min.toString()

            val icon = listBean?.weather
            mImgIconDate.setImageResource(getIcon(icon.toString()))
        }

        private fun loadInformationWeatherRecyclerView(cityName: String) {
            val apiServicesRecyclerView = ApiServicesRecyclerView()
            apiServicesRecyclerView.getIEventWeatherRecyclerView().getInformationWeatherRecyclerView(cityName, Constants.APP_ID).enqueue(object : Callback<InformationWeatherRecyclerView> {
                override fun onResponse(call: Call<InformationWeatherRecyclerView>?, response: Response<InformationWeatherRecyclerView>?) {
                    if (response?.body() != null) {
                        showInformationWeatherRecyclerView(Objects.requireNonNull<InformationWeatherRecyclerView>(response.body()))
                    }
                }

                override fun onFailure(call: Call<InformationWeatherRecyclerView>?, t: Throwable?) {
                    Toast.makeText(mContext, R.string.notification, Toast.LENGTH_SHORT).show()
                }

            })
        }

        private fun getIcon(icon: String): Int {
            when (icon) {
                Constants.ICON_01D -> return R.drawable.img_01d
                Constants.ICON_01N -> return R.drawable.img_01n
                Constants.ICON_02D -> return R.drawable.img_02d
                Constants.ICON_02N -> return R.drawable.img_02n
                Constants.ICON_03D -> return R.drawable.img_03d
                Constants.ICON_03N -> return R.drawable.img_03n
                Constants.ICON_04D -> return R.drawable.img_04d
                Constants.ICON_04N -> return R.drawable.img_04n
                Constants.ICON_09D -> return R.drawable.img_09d
                Constants.ICON_09N -> return R.drawable.img_09n
                Constants.ICON_10D -> return R.drawable.img_10d
                Constants.ICON_10N -> return R.drawable.img_10n
                Constants.ICON_11D -> return R.drawable.img_11d
                Constants.ICON_11N -> return R.drawable.img_11n
                Constants.ICON_13D -> return R.drawable.img_13d
                Constants.ICON_13N -> return R.drawable.img_13n
                Constants.ICON_50D -> return R.drawable.img_50d
                Constants.ICON_50N -> return R.drawable.img_50n
                else -> return R.drawable.img_sun
            }
        }


        override fun onClick(v: View?) {

        }



        fun bind(informationWeatherRecyclerView: InformationWeatherRecyclerView) {
            mTvDate.text = informationWeatherRecyclerView.list?.get(0)?.dt.toString()
            mTvDateStatus.text = informationWeatherRecyclerView.list?.get(0)?.weather?.get(0)?.description
            informationWeatherRecyclerView.list?.get(0)?.weather?.get(0)?.icon?.toInt()?.let { mImgIconDate.setImageResource(it) }
            mTvMaxTempList.text = informationWeatherRecyclerView.list?.get(0)?.main?.temp_max?.toString()
            mTvMinTempList.text = informationWeatherRecyclerView.list?.get(0)?.main?.temp_min?.toString()
        }
    }
}
