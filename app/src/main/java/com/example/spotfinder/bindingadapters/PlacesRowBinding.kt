package com.example.spotfinder.bindingadapters

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.spotfinder.R
import com.example.spotfinder.models.Place
import com.example.spotfinder.ui.fragments.places.PlacesFragmentArgs
import com.example.spotfinder.ui.fragments.places.PlacesFragmentDirections

class PlacesRowBinding {

    companion object {

        @BindingAdapter("onPlaceClickLister")
        @JvmStatic
        fun onPlaceClickLister(placeRowLayout: ConstraintLayout, result: Place) {

            placeRowLayout.setOnClickListener {
                try {
                    Log.i("onPlacesClickLister", result.toString())
                    val action =
                        PlacesFragmentDirections.actionPlacesFragmentToDetailsActivity(result)
                    placeRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onPlacesClickLister", e.toString())
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load("https://img.freepik.com/vector-gratis/apoye-concepto-negocio-local_23-2148592675.jpg") {
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