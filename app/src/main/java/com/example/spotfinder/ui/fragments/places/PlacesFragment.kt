package com.example.spotfinder.ui.fragments.places

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spotfinder.R
import com.example.spotfinder.viewmodels.MainViewModel
import com.example.spotfinder.adapters.PlacesAdapter
import com.example.spotfinder.databinding.FragmentPlacesBinding
import com.example.spotfinder.util.Constants.Companion.LOG_OUT
import com.example.spotfinder.util.NetworkListener
import com.example.spotfinder.util.NetworkResult
import com.example.spotfinder.util.observeOnce
import com.example.spotfinder.viewmodels.LoginViewModel
import com.example.spotfinder.viewmodels.PlacesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlacesFragment : Fragment() {

    // ARGUMENTS FROM BOTTOMSHEETDIALOG
    private val args by navArgs<PlacesFragmentArgs>()

    private var _binding: FragmentPlacesBinding? = null
    private val binding get() = _binding!!

    private  val mainViewModel: MainViewModel by activityViewModels()
    private  val placesViewModel: PlacesViewModel by activityViewModels()
    private val loginViewModel: LoginViewModel by activityViewModels()
    private val mAdapter by lazy { PlacesAdapter() }

    private lateinit var networkListener: NetworkListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPlacesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        loginViewModel.readUser.observeOnce(viewLifecycleOwner) {flag ->
            if(flag == LOG_OUT) {
                val navController = findNavController()
                navController.navigate(R.id.action_placesFragment_to_loginFragment)
            } else {
                binding.placesFab.visibility = View.VISIBLE
                setupRecyclerView()
            }
        }
        setupRecyclerView()
        placesViewModel.readBackOnline.observe(viewLifecycleOwner) {
            placesViewModel.backOnline = it
        }


        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect{status ->
                    Log.d("NetworkListener", status.toString())
                    placesViewModel.networkStatus = status
                    placesViewModel.showNetworkStatus()
                    readDatabase()
                }
        }

        binding.placesFab.setOnClickListener {
            if (placesViewModel.networkStatus) {
                findNavController().navigate(R.id.action_placesFragment_to_placesBottomSheet)
            } else {
                placesViewModel.showNetworkStatus()
            }
        }

    }

    private fun setupRecyclerView() {
        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readPlaces.observeOnce(viewLifecycleOwner) { databse ->
                if (databse.isNotEmpty() && !args.backFromBottomSheet) {
                    mAdapter.setData(databse[0].responsePlaces)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            }
        }
    }



    private fun requestApiData() {
        Log.d("PlacesFragment", "requestApiData called!" )
        mainViewModel.getPlaces(placesViewModel.applyQueries())
        mainViewModel.placesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readPlaces.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].responsePlaces)
                }
            }
        }
    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.shimmerFrameLayout.startShimmer()
        binding.recyclerview.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.recyclerview.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}