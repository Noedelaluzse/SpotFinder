package com.example.spotfinder.ui.fragments.commets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spotfinder.adapters.CommentsAdapter
import com.example.spotfinder.databinding.FragmentCommentsBinding
import com.example.spotfinder.models.Place
import com.example.spotfinder.util.Constants
import com.example.spotfinder.util.NetworkListener
import com.example.spotfinder.util.NetworkResult
import com.example.spotfinder.util.observeOnce
import com.example.spotfinder.viewmodels.CommentViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class CommentsFragment : Fragment() {

    private var _binding: FragmentCommentsBinding? = null
    private val binding get() = _binding!!

    // VIEWMODELS
    private  val mainViewModel: CommentViewModel by activityViewModels()

    // ADAPTER
    private val mAdapter: CommentsAdapter by lazy {  CommentsAdapter() }

    private lateinit var networkListener: NetworkListener
    private lateinit var idPlace: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCommentsBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Place? = args?.getParcelable(Constants.BUNDLE_LKEY)
        idPlace = myBundle?.id.toString()
        mainViewModel.getComments(idPlace)
        setupRecyclerView()

        binding.progress.visibility = View.VISIBLE

/*        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect{ status ->
                    Log.d("NetworkListener", status.toString())
                    mainViewModel.networkStatus = status
                    readData()

                }
        }*/

        mainViewModel.commentsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    //hideShimmerEffect()
                    response.data?.comment?.let { mAdapter.setData(it) }
                    binding.progress.visibility = View.GONE
                }
                is NetworkResult.Error -> {
                    //hideShimmerEffect()
                    //loadDataFromCache()
                    binding.noComments.text = "Sin comentarios"
                    binding.noComments.visibility = View.VISIBLE
                    binding.progress.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    //showShimmerEffect()
                }
            }
        }

       /* mainViewModel.commentsResponse.observe(viewLifecycleOwner) {

            val data = mainViewModel.commentsResponse.value!!.data

            if (data != null) {
                binding.noComments.visibility = View.GONE
                Log.d("commentsResponse", data.toString())
                mAdapter.setData(data.comment)

                //binding.progress.visibility = View.GONE
            } else {
                binding.noComments.visibility = View.VISIBLE
                binding.noComments.text = "SIN COMENTARIOS"
                binding.progress.visibility = View.GONE
            }
        }*/
        //

        return binding.root

    }

    private fun readData() {
        lifecycleScope.launch {
            mainViewModel.commentsResponse.observeOnce(viewLifecycleOwner) { response ->
                if (!response.data?.comment.isNullOrEmpty()) {
                    mAdapter.setData(response.data!!.comment)
                } else {
                    requestData()

                }
            }
         }
    }

    private fun requestData() {
        mainViewModel.getComments(idPlace)
    }

    private fun setupRecyclerView() {
        binding.rvCommets.adapter = mAdapter
        binding.rvCommets.layoutManager = LinearLayoutManager(requireContext())

    }

}