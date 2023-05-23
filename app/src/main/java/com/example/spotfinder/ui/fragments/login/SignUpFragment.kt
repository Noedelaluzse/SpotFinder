package com.example.spotfinder.ui.fragments.login

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.spotfinder.R
import com.example.spotfinder.databinding.FragmentSignUpBinding
import com.example.spotfinder.models.login.NewUser
import com.example.spotfinder.util.Constants.Companion.ERROR_CASE
import com.example.spotfinder.util.Constants.Companion.LOG_IN
import com.example.spotfinder.util.Constants.Companion.NONE_CASE
import com.example.spotfinder.util.NetworkResult
import com.example.spotfinder.util.Options
import com.example.spotfinder.viewmodels.LoginViewModel
import com.example.spotfinder.viewmodels.MainViewModel

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    var urlImg = ""
    var optionSelected = 0



    // viewmodel
    private  val mainViewModel: MainViewModel by activityViewModels()
    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        loginViewModel.readUser.observe(viewLifecycleOwner) {user ->
            if (user == LOG_IN) {
                findNavController().navigate(R.id.placesFragment)
            } else if (user == ERROR_CASE) {
                showMessage("Error", "Ocurrio un problema al intentar realizar el registro, intenta mas tarde")
                loginViewModel.saveUser(NONE_CASE)
            }
        }
        initProfileImage()
        binding.btnRegistrar.setOnClickListener {
            signIn()
        }
        return binding.root
    }

    private fun initProfileImage() {
        binding.imgOption1.setOnClickListener { val option: Int = 1; setImageProfile(option) }
        binding.imgOption2.setOnClickListener { val option: Int = 2; setImageProfile(option) }
        binding.imgOption3.setOnClickListener { val option: Int = 3;setImageProfile(option) }
        binding.imgOption4.setOnClickListener { val option: Int = 4; setImageProfile(option) }
        binding.imgOption5.setOnClickListener { val option: Int = 5; setImageProfile(option) }
        binding.imgOption6.setOnClickListener { val option: Int = 6; setImageProfile(option) }
    }

    private fun setImageProfile(option: Int) {

        when (option) {
            1 -> {
                urlImg = R.drawable.avatar_1_raster.toString()

                if (optionSelected != 0) {
                    restoreImage(optionSelected)
                }
                optionSelected = 1;

                binding.imgOption1.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.colorPrimary)
                )

            }
            2 -> {
                urlImg = R.drawable.avatar_2_raster.toString()

                if (optionSelected != 0) {
                    restoreImage(optionSelected)
                }
                optionSelected = 2;

                binding.imgOption2.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.colorPrimary)
                )

            }
            3 -> {
                urlImg = R.drawable.avatar_3_raster.toString()

                if (optionSelected != 0) {
                    restoreImage(optionSelected)
                }
                optionSelected = 3;

                binding.imgOption3.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.colorPrimary)
                )

            }
            4 -> {
                urlImg = R.drawable.avatar_4_raster.toString()

                if (optionSelected != 0) {
                    restoreImage(optionSelected)
                }
                optionSelected = 4;

                binding.imgOption4.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.colorPrimary)
                )

            }
            5 -> {
                urlImg = R.drawable.avatar_5_raster.toString()

                if (optionSelected != 0) {
                    restoreImage(optionSelected)
                }
                optionSelected = 5;

                binding.imgOption5.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.colorPrimary)
                )

            }
            6 -> {
                urlImg = R.drawable.avatar_6_raster.toString()

                if (optionSelected != 0) {
                    restoreImage(optionSelected)
                }
                optionSelected = 6;

                binding.imgOption6.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.colorPrimary)
                )
            }
        }

    }

    private fun restoreImage(optionSelected: Int) {
        when (optionSelected) {
            1 -> binding.imgOption1.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.avatar_1_raster)
            2 -> binding.imgOption2.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.avatar_2_raster)
            3 -> binding.imgOption3.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.avatar_3_raster)
            4 -> binding.imgOption4.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.avatar_4_raster)
            5 -> binding.imgOption5.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.avatar_5_raster)
            6 -> binding.imgOption6.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.avatar_6_raster)
        }
    }

    fun signIn() {
        val phone: String = binding.etPhoneRegister.text.toString()
        val password: String = binding.etPasswordRegister.text.toString()
        val name: String = binding.etUserRegister.text.toString()
        val owner: Boolean = false
        val status: Boolean = true
        val google: Boolean = false

        if (validateData(phone, password, name)) {
            val newUser = NewUser(name, phone, password, urlImg, owner, status, google )
            Log.i("NewUser", newUser.toString())
            mainViewModel.createUser(newUser)
            mainViewModel.loginResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        loginViewModel.saveUser(LOG_IN)
                        binding.progress.visibility = View.GONE
                    }
                    is NetworkResult.Error -> {
                        loginViewModel.saveUser(ERROR_CASE)
                        binding.progress.visibility = View.GONE
                    }
                    is NetworkResult.Loading -> {
                        binding.progress.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun validateData(phone: String, password: String, name: String): Boolean {
        if (name != "" && phone.length >  9 && password.length > 6 && urlImg != "") {
            return true
        } else {
            if (name == "") {
                val title = "Campos incpmpletos"
                val message = "El nombre es obligatorio"
                showMessage(title, message)
                return false
            }
            if (phone.length < 10) {
                val title = "Campos incpmpletos"
                val message = "El numero de telefono debe ser mayor a 10 digitos"
                showMessage(title, message)
                return false
            }

            if (password.length < 7) {
                val title = "Campos incpmpletos"
                val message = "La contraseña debe ser mayor a 6 caracteres"
                showMessage(title, message)
                return false
            }

            if (urlImg == "") {
                val title = "Campos incpmpletos"
                val message = "Selecciona una foto de perfil"
                showMessage(title, message)
                return false
            }

        }
        return false
    }

    private fun showMessage(title: String, message: String) {
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
        builder?.setMessage(message)?.setTitle(title)
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }
}