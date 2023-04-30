package com.example.spotfinder.ui.fragments.login

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import com.example.spotfinder.R
import com.example.spotfinder.databinding.FragmentLoginBinding
import com.example.spotfinder.util.Constants.Companion.ERROR_CASE
import com.example.spotfinder.util.Constants.Companion.LOG_IN
import com.example.spotfinder.util.NetworkResult
import com.example.spotfinder.viewmodels.LoginViewModel
import com.example.spotfinder.viewmodels.MainViewModel


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    // ViewModelL
    private  val mainViewModel: MainViewModel by activityViewModels()
    private val loginViewModel: LoginViewModel by activityViewModels()
    private lateinit var savedStateHandle: SavedStateHandle


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle

        loginViewModel.readUser.observe(viewLifecycleOwner) {user ->
            if (user == LOG_IN) {
                findNavController().navigate(R.id.placesFragment)
            } else if(user == ERROR_CASE) {
                validUser(R.string.error_message)
            }
        }

        binding.btnSendData.setOnClickListener {
            val number = binding.etPhoneNumber.text.toString()
            val password = binding.etPassword.text.toString()

            if (number.isNotEmpty() && password.isNotEmpty()) {
                if (validateData(number, password)) {
                    mainViewModel.login(number.trim(),  password.trim())
                    mainViewModel.loginResponse.observe(viewLifecycleOwner) { response ->
                        when (response) {
                            is NetworkResult.Success -> {
                                loginViewModel.saveUser(LOG_IN)
                                binding.progress.visibility = View.GONE
                            }
                            is NetworkResult.Error -> {
                                binding.progress.visibility = View.GONE
                                loginViewModel.saveUser(ERROR_CASE)
                                //Toast.makeText(requireContext(),
                                  //  response.message.toString(),
                                    //Toast.LENGTH_SHORT).show()
                            }
                            is NetworkResult.Loading -> {
                                binding.progress.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }

    }

    private fun validateData(number: String, password: String): Boolean {

        var flag = true
        if (number.length != 10 && password.length < 6) {
            flag = false
            val builder: AlertDialog.Builder? = activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton(R.string.ok,
                        DialogInterface.OnClickListener { dialog, id ->
                            dialog.dismiss()
                        }
                    )
                }
            }
            builder?.setMessage(R.string.dialog_body)?.setTitle(R.string.dialog_title)
            val dialog: AlertDialog? = builder?.create()
            dialog?.show()
        }

        return flag

    }

    private fun validUser(message: Int) {
        val builder: AlertDialog.Builder? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                    }
                )
            }
        }
        builder?.setMessage(message)?.setTitle(R.string.dialog_title_user)
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }

}