package com.example.spotfinder.util

import androidx.recyclerview.widget.DiffUtil
import com.example.spotfinder.models.Place

class PlacesDiffUtil (
    private val oldList: List<Place>,
    private val newList: List<Place> ): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
       return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }


}