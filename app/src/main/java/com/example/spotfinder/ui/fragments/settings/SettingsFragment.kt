package com.example.spotfinder.ui.fragments.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.spotfinder.R
import com.example.spotfinder.databinding.FragmentSettingsBinding
import com.example.spotfinder.util.Constants.Companion.LOG_OUT
import com.example.spotfinder.viewmodels.LoginViewModel
import com.example.spotfinder.viewmodels.MainViewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnLogin.setOnClickListener {
            mainViewModel.logout()
            loginViewModel.readUser.observe(viewLifecycleOwner) { flag ->
                if (flag == LOG_OUT) {
                    val navController = findNavController()
                    navController.navigate(R.id.action_settingsFragment_to_loginFragment)
                }
            }
        }
    }

}