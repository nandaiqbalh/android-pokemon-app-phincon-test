package com.nandaiqbalh.pokemonapp.presentation.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nandaiqbalh.pokemonapp.R
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.register.request.AuthRegisterRequestBody
import com.nandaiqbalh.pokemonapp.databinding.FragmentRegisterBinding
import com.nandaiqbalh.pokemonapp.presentation.ui.auth.AuthActivity
import com.nandaiqbalh.pokemonapp.util.CustomSnackbar
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

	private var _binding: FragmentRegisterBinding? = null
	private val binding get() = _binding!!
	private val customSnackbar = CustomSnackbar()

	private val registerViewModel: RegisterViewModel by viewModels()
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		cekStatusLogin()

		buttonListener()

	}

	private fun buttonListener() {
		with(binding) {
			btnLogin.setOnClickListener {
				findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
			}
			
			btnRegister.setOnClickListener { 
				if (validateForm()){
					authRegister()
				}
			}
		}
	}

	private fun cekStatusLogin() {
		registerViewModel.getStatusAuth().observe(viewLifecycleOwner) { statusAuth ->
			if (statusAuth == true) {
				val intent = Intent(context, AuthActivity::class.java)
				startActivity(intent)
				requireActivity().finish()

			}
		}
	}

	private fun authRegister() {
		setLoading(true)

		val enteredUsername = binding.edtUserName.text.toString().trim()
		val enteredPassword = binding.edtPassword.text.toString().trim()

		registerViewModel.authRegister(
			AuthRegisterRequestBody(
				username = enteredUsername,
				password = enteredPassword
			)
		)

		registerViewModel.authRegisterResult.observe(viewLifecycleOwner) { authRegisterResult ->

			when (authRegisterResult) {
				is Resource.Loading -> setLoading(true)
				is Resource.Error -> {
					setLoading(false)
					Log.d("Result status", authRegisterResult.payload?.status.toString())

					customSnackbar.showSnackbarWithAction(
						requireActivity().findViewById(android.R.id.content),
						authRegisterResult.payload?.status
							?: "Error occured!",
						"OK"
					) {
						customSnackbar.dismissSnackbar()
					}
				}

				is Resource.Success -> {
					setLoading(false)
					Log.d("Result status", authRegisterResult.payload?.status.toString())

					val registerResult = authRegisterResult.payload

					if (registerResult?.success == true && registerResult.userData != null) {

						// show snackbar
						customSnackbar.showSnackbarWithAction(
							requireActivity().findViewById(android.R.id.content),
							registerResult.status ?: "Authentication successful.",
							"OK"
						) {
							customSnackbar.dismissSnackbar()
						}

						findNavController().navigate(R.id.action_registerFragment_to_loginFragment)


					} else {
						// if the success is false, then just show the snackbar
						customSnackbar.showSnackbarWithAction(
							requireActivity().findViewById(android.R.id.content),
							registerResult?.status ?: "Authentication failed.",
							"OK"
						) {
							customSnackbar.dismissSnackbar()
						}
					}
				}

				else -> {}

			}
		}
	}

	private fun validateForm(): Boolean {
		val username = binding.edtUserName.text.toString()
		val password = binding.edtPassword.text.toString()
		val passwordConfirm = binding.edtPasswordConfirm.text.toString()

		var isFormValid = true

		// Validate username
		if (username.isEmpty()) {
			isFormValid = false
			binding.tilUsername.error = getString(R.string.tv_error_input_blank)
		} else {
			binding.tilUsername.error = null
		}

		// Validate password
		if (password.isEmpty()) {
			isFormValid = false
			binding.tilPassword.error = getString(R.string.tv_error_input_blank)
		} else if (password.length < 8) {
			isFormValid = false
			binding.tilPassword.error = getString(R.string.tv_error_password_minimum_length)
		} else {
			binding.tilPassword.error = null
		}

		// Validate password confirmation
		if (passwordConfirm.isEmpty()) {
			isFormValid = false
			binding.tilPasswordConfirm.error = getString(R.string.tv_error_input_blank)
		} else if (password != passwordConfirm) {
			isFormValid = false
			binding.tilPasswordConfirm.error = getString(R.string.tv_error_password_mismatch)
		} else {
			binding.tilPasswordConfirm.error = null
		}

		return isFormValid
	}

	private fun setLoading(isLoading: Boolean) {
		if (isLoading) {
			binding.pbLogin.visibility = View.VISIBLE
		} else {
			binding.pbLogin.visibility = View.GONE
		}
	}


	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}


}