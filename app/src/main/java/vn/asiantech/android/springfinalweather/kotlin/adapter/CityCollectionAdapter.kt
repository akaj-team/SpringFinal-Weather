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
import vn.asiantech.android.springfinalweather.kotlin.model.CityCollection
import vn.asiantech.android.springfinalweather.kotlin.myinterface.OnCityCollectionChangeListener

class CityCollectionAdapter(
        private var mListCity: MutableList<CityCollection>,
        private var mListener: OnCityCollectionChangeListener
) : RecyclerView.Adapter<CityCollectionAdapter.CityCollectionHolder>() {
    private var mFocusItem: String = ""
    fun setFocusItem(focusItem: String) {
        mFocusItem = focusItem
    }

    override fun getItemCount(): Int {
        return mListCity.size
    }

    override fun onBindViewHolder(holder: CityCollectionHolder, position: Int) {
        holder.bind(mListCity[position], mFocusItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityCollectionHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city_collection, parent, false)
        return CityCollectionHolder(view, mListener)
    }

    class CityCollectionHolder(itemView: View, private var listener: OnCityCollectionChangeListener)
        : RecyclerView.ViewHolder(itemView) {
        private var mTvCityName: TextView = itemView.findViewById(R.id.tvCityName)
        private var mImgIcon: ImageView = itemView.findViewById(R.id.imgBtnStateOrDelete)

        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind(cityCollection: CityCollection, focusName: String) {
            mTvCityName.text = cityCollection.cityName
            if (cityCollection.state == Constants.USER_LOCATION) {
                mImgIcon.setImageResource(R.drawable.ic_location_purple_24dp)
            } else {
                mImgIcon.setImageResource(R.drawable.ic_cancel_black_24dp)
            }
            mImgIcon.setOnClickListener {
                if (!cityCollection.state) {
                    listener.onDeleteCityCollection(cityCollection)
                }
            }
            mTvCityName.setOnClickListener {
                listener.onChangeShowCityCollection(cityCollection)
                itemView.requestFocus()
            }

            if (cityCollection.cityName == focusName) {
                itemView.requestFocus()
            }
        }
    }
}
