package com.example.spotfinder.bindingadapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.spotfinder.R

class PlacesRowBinding {

    companion object {

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }


        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes:Int) {
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberOfComments")
        @JvmStatic
        fun setNumberOfComments(textView: TextView, commets:Int) {
            textView.text = commets.toString()
        }
    }
}