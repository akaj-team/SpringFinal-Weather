package vn.asiantech.android.springfinalweather.kotlin.adapter

import android.Manifest
import android.app.Activity
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import vn.asiantech.android.springfinalweather.R
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.model.City
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnSelectCityListener

class CityPredictionAdapter(
        private val listCity: List<City>?,
        private val listener: OnSelectCityListener,
        private val activity: Activity
) : RecyclerView.Adapter<CityPredictionAdapter.CityHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city_prediction, parent, false)
        return CityHolder(view, listener, activity)
    }

    override fun getItemCount(): Int {
        return listCity?.size ?: 0
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(listCity?.get(position))
    }


    class CityHolder(itemView: View, listener: OnSelectCityListener, activity: Activity)
        : RecyclerView.ViewHolder(itemView) {
        private var tvCityName: TextView = itemView.findViewById(R.id.tvCityName)

        init {
            itemView.setOnClickListener {
                if (tvCityName.text.trim() == activity.getString(R.string.get_my_location)) {
                    ActivityCompat.requestPermissions(
                            activity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            Constants.LOCATION_PERMISSION_REQUEST
                    )
                } else {
                    listener.onCitySelected(tvCityName.text.toString().trim())
                }
            }
        }

        fun bind(city: City?) {
            tvCityName.text = city?.name
        }
    }
}
