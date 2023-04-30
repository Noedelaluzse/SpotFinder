package com.example.spotfinder.ui.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.example.spotfinder.R
import com.example.spotfinder.databinding.FragmentOverviewBinding
import com.example.spotfinder.models.Place
import com.example.spotfinder.util.Constants.Companion.BUNDLE_LKEY


class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Place? = args?.getParcelable(BUNDLE_LKEY)

        // TODO settear la imagen de mi lugar
        //binding.mainImageView.load(myBundle.image)
        binding.tvTitle.text = myBundle?.name
        binding.tvLikes.text = myBundle?.likes.toString()
        binding.tvComments.text = myBundle?.comments.toString()
        binding.tvSummary.text = myBundle?.description

        if (myBundle?.sportBar == true) {
            binding.imvSportBar.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.restaurant == true) {
            binding.imvRestaurante.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.bar == true) {
            binding.imvBar.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.cafe == true) {
            binding.imvCafe.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.nightclub == true) {
            binding.imvNightClub.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.liveMusic == true) {
            binding.imvLiveMusic.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.park == true) {
            binding.imvPark.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
        }

       /*  TODO agragar la propiedad others a la clase de Places
        if (myBundle?.others == true) {
            binding.imvOthers.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green)
        } */






        return binding.root
    }

}