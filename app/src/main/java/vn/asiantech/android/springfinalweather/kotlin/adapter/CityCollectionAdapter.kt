package vn.asiantech.android.springfinalweather.kotlin.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_city_collection.view.*
import vn.asiantech.android.springfinalweather.R
import vn.asiantech.android.springfinalweather.kotlin.`object`.Constants
import vn.asiantech.android.springfinalweather.kotlin.`object`.Image
import vn.asiantech.android.springfinalweather.kotlin.model.CityCollection
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnCityCollectionChangeListener

class CityCollectionAdapter(
        private var listCity: MutableList<CityCollection>,
        private var listener: OnCityCollectionChangeListener,
        private var unitOfTemp: Int
) : RecyclerView.Adapter<CityCollectionAdapter.CityCollectionHolder>() {
    private var focusItem: String = ""
    fun setFocusItem(focusItem: String) {
        this.focusItem = focusItem
    }

    override fun getItemCount(): Int {
        return listCity.size
    }

    override fun onBindViewHolder(holder: CityCollectionHolder, position: Int) {
        holder.bind(listCity[position], focusItem, unitOfTemp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityCollectionHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city_collection, parent, false)
        return CityCollectionHolder(view, listener)
    }

    class CityCollectionHolder(itemView: View, private var listener: OnCityCollectionChangeListener)
        : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind(cityCollection: CityCollection, focusName: String, unitOfTemp: Int) {
            itemView.tvCityName.text = cityCollection.cityName
            if (unitOfTemp == 0) {
                itemView.tvTemp.text = cityCollection.temp.toInt().toString() + "°C"
            } else {
                itemView.tvTemp.text = getFahrenheitDegree(cityCollection.temp).toString() + "°F"
            }
            itemView.imgWeather.setImageResource(Image.getIcon(cityCollection.icon, cityCollection.day))
            if (cityCollection.state == Constants.USER_LOCATION) {
                itemView.imgBtnStateOrDelete.setImageResource(R.drawable.ic_location_24dp)
            } else {
                itemView.imgBtnStateOrDelete.setImageResource(R.drawable.ic_cancel_24dp)
            }
            itemView.imgBtnStateOrDelete.setOnClickListener {
                if (!cityCollection.state) {
                    listener.onDeleteCityCollection(cityCollection)
                }
            }
            itemView.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    listener.onChangeShowCityCollection(cityCollection)
                }
            }

            if (cityCollection.cityName == focusName) {
                itemView.requestFocus()
            }
        }

        private fun getFahrenheitDegree(fah: Float): Int {
            val fahrenheit = fah.times(9).div(5) + 32
            return fahrenheit.toInt()
        }
    }
}
