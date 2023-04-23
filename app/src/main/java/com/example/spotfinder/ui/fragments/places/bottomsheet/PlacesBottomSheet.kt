package com.example.spotfinder.ui.fragments.places.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.spotfinder.R
import com.example.spotfinder.databinding.PlacesBottomSheetBinding
import com.example.spotfinder.util.Constants.Companion.DEFAULT_CATEGORY
import com.example.spotfinder.viewmodels.PlacesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.*


class PlacesBottomSheet : BottomSheetDialogFragment() {

    // BINDING VARIABLES
    private var _binding: PlacesBottomSheetBinding? = null
    private val binding get() = _binding!!

    // VIEWMODEL
    private val placesViewModel: PlacesViewModel by activityViewModels()

    // VARIABLES FOR BOTTOMSHEET
    private var categoryType = DEFAULT_CATEGORY
    private var categoryTypeId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlacesBottomSheetBinding.inflate(inflater, container, false)

        placesViewModel.readPlaceType

        placesViewModel.readPlaceType.asLiveData().observe(viewLifecycleOwner) { value ->
            categoryType = value.selectedCategoryName
            updateChip(value.selectedCategoryNameId, binding.chipGroupCategory)
        }


        binding.chipGroupCategory.setOnCheckedStateChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId.first())
            val selectedCategoryType = chip.text.toString().lowercase(Locale.ROOT)
            categoryType = selectedCategoryType
            categoryTypeId = selectedChipId.first()
        }

        binding.btnApply.setOnClickListener {
            placesViewModel.saveCategoryType(
                categoryType,
                categoryTypeId
            )
            val action =
                PlacesBottomSheetDirections.actionPlacesBottomSheetToPlacesFragment(true)
            findNavController().navigate(action)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception) {
                Log.d("PlacesBottomSheet", e.message.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}