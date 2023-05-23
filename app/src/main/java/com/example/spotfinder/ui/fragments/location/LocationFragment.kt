package com.example.spotfinder.ui.fragments.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.spotfinder.R
import com.example.spotfinder.databinding.FragmentLocationBinding
import com.example.spotfinder.models.Place
import com.example.spotfinder.util.Constants
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!

    private lateinit var googleMap: GoogleMap
    private lateinit var coordinates: LatLng
    private lateinit var title: String
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.onResume()

        val args = arguments
        val myBundle: Place? = args?.getParcelable(Constants.BUNDLE_LKEY)
        val latitude = myBundle?.address?.latitude
        val longitude = myBundle?.address?.longitude
        title = myBundle?.name!!
        coordinates = LatLng(latitude!!, longitude!!)
        binding.mapView.getMapAsync(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLocationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onMapReady(map: GoogleMap) {
        map.addMarker(
            MarkerOptions()
                .position(coordinates)
                    .title(title)
        )

        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 18f),
            4000,
            null
        )




    /*        map?.let {
            googleMap = it
        }
  */
    }

}