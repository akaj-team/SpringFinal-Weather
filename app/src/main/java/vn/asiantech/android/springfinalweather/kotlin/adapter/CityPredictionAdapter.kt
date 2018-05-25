package vn.asiantech.android.springfinalweather.kotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import vn.asiantech.android.springfinalweather.R
import vn.asiantech.android.springfinalweather.kotlin.model.City
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnSelectCityListener

class CityPredictionAdapter(private val listCity: List<City>?, private val listener: OnSelectCityListener) :
        RecyclerView.Adapter<CityPredictionAdapter.CityHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city_prediction, parent, false)
        return CityHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return listCity?.size ?: 0
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(listCity?.get(position))
    }


    class CityHolder(itemView: View, listener: OnSelectCityListener) : RecyclerView.ViewHolder(itemView) {
        private var mTvCityName: TextView = itemView.findViewById(R.id.tvCityName)
        private var mTvDescription: TextView = itemView.findViewById(R.id.tvDescription)

        init {
            itemView.setOnClickListener {
                listener.onCitySelected(mTvCityName.text.toString().trim().toLowerCase())
            }
        }

        fun bind(city: City?) {
            mTvCityName.text = city?.structure?.cityName
            mTvDescription.text = city?.description
        }
    }
}
