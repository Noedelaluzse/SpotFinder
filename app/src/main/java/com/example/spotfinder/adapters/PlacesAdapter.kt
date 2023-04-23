package com.example.spotfinder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.spotfinder.databinding.PlacesRowLayoutBinding
import com.example.spotfinder.models.Place
import com.example.spotfinder.models.ResponsePlaces
import com.example.spotfinder.util.PlacesDiffUtil

class PlacesAdapter: RecyclerView.Adapter<PlacesAdapter.MyViewHolder>() {

    private var places = emptyList<Place>()

    class MyViewHolder(private val binding: PlacesRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Place) {
            binding.result = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PlacesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val currentPlace = places[position]
        holder.bind(currentPlace)
    }

    override fun getItemCount(): Int {
        return places.size
    }

    fun setData(newData: ResponsePlaces) {

        val placesDiffUtil = PlacesDiffUtil(places, newData.arrLugares)
        val diffUtilResult = DiffUtil.calculateDiff(placesDiffUtil)
        places = newData.arrLugares
        diffUtilResult.dispatchUpdatesTo(this)
    //notifyDataSetChanged()
    }
}