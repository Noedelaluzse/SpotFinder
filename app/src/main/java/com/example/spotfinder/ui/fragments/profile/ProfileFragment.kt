package com.example.spotfinder.ui.fragments.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.spotfinder.R
import com.example.spotfinder.databinding.FragmentProfileBinding
import com.example.spotfinder.models.ResponsePlaces
import com.example.spotfinder.util.NetworkResult
import com.example.spotfinder.viewmodels.MainViewModel


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    private  val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var myUser: ResponsePlaces
    private var uidUser = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.getUserInfomationForfragment()
        mainViewModel.temp.observe(viewLifecycleOwner) {
            if (it != "") {
                mainViewModel.getUserInfo(it)
            }
        }
        mainViewModel.userInfo.observe(viewLifecycleOwner) { response ->
            binding.progress.visibility = View.VISIBLE
            when (response) {
                is NetworkResult.Success -> {
                    binding.tvUserName.text = response.data?.user?.name
                    binding.tvUserNumber.text = response.data?.user?.phone
                    if (response.data?.user?.img!!.isNotEmpty()) {
                        val urlImage = ContextCompat.getDrawable(requireContext(), response.data.user.img.toInt())
                        binding.imgProfileUser.background = urlImage
                    }
                    binding.progress.visibility = View.GONE
                }
                is NetworkResult.Error -> {
                    binding.tvUserName.text = " "
                    binding.tvUserNumber.text = " "
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progress.visibility = View.GONE
                }
                is NetworkResult.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }
            }
        }
    }

}