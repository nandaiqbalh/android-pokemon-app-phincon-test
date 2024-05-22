package com.nandaiqbalh.pokemonapp.presentation.ui.auth.login

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
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.login.request.AuthLoginRequestBody
import com.nandaiqbalh.pokemonapp.databinding.FragmentLoginBinding
import com.nandaiqbalh.pokemonapp.presentation.ui.auth.AuthActivity
import com.nandaiqbalh.pokemonapp.presentation.ui.home.MainActivity
import com.nandaiqbalh.pokemonapp.util.CustomSnackbar
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

	private var _binding: FragmentLoginBinding? = null
	private val binding get() = _binding!!
	private val customSnackbar = CustomSnackbar()

	private val loginViewModel: LoginViewModel by viewModels()
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		cekStatusLogin()

		buttonListener()

	}

	private fun buttonListener() {
		with(binding) {
			btnRegister.setOnClickListener {
				findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
			}

			btnLogin.setOnClickListener {
				if (validateForm()) {

					authLogin()
				}
			}
		}
	}

	private fun cekStatusLogin() {
		loginViewModel.getStatusAuth().observe(viewLifecycleOwner) { statusAuth ->
			if (statusAuth == true) {
				val intent = Intent(context, AuthActivity::class.java)
				startActivity(intent)
				requireActivity().finish()

			}
		}
	}

	private fun authLogin() {
		setLoading(true)

		val enteredUsername = binding.edtUserName.text.toString().trim()
		val enteredPassword = binding.edtPassword.text.toString().trim()

		loginViewModel.authLogin(
			AuthLoginRequestBody(
				username = enteredUsername,
				password = enteredPassword
			)
		)

		loginViewModel.authLoginResult.observe(viewLifecycleOwner) { authLoginResult ->

			when (authLoginResult) {
				is Resource.Loading -> setLoading(true)
				is Resource.Error -> {
					setLoading(false)
					Log.d("Result status", authLoginResult.payload?.status.toString())

					customSnackbar.showSnackbarWithAction(
						requireActivity().findViewById(android.R.id.content),
						authLoginResult.payload?.status ?: "Error occured!",
						"OK"
					) {
						customSnackbar.dismissSnackbar()
					}
				}

				is Resource.Success -> {
					setLoading(false)
					Log.d("Result status", authLoginResult.payload?.status.toString())

					val loginResult = authLoginResult.payload

					if (loginResult?.success == true && loginResult.userData != null) {

						// show snackbar
						customSnackbar.showSnackbarWithAction(
							requireActivity().findViewById(android.R.id.content),
							loginResult.status ?: "Authentication successful.",
							"OK"
						) {
							customSnackbar.dismissSnackbar()
						}

						// get user data response
						val userId = loginResult.userData.id
						val username = loginResult.userData.username

						// set auth data store
						loginViewModel.setStatusAuth(true)
						loginViewModel.setUserId(userId!!)
						loginViewModel.setUsername(username ?: "username")

						val intent = Intent(context, MainActivity::class.java)
						startActivity(intent)
						requireActivity().finish()

					} else {
						// if the success is false, then just show the snackbar
						customSnackbar.showSnackbarWithAction(
							requireActivity().findViewById(android.R.id.content),
							loginResult?.status ?: "Authentication failed!",
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

		var isFormValid = true

		if (username.isEmpty()) {
			isFormValid = false
			binding.tilUsername.error = getString(R.string.tv_error_input_blank)
		} else {
			binding.tilUsername.error = null
		}

		if (password.isEmpty()) {
			isFormValid = false
			binding.tilPassword.error = getString(R.string.tv_error_input_blank)
		} else if (password.length < 8) {
			isFormValid = false
			binding.tilPassword.error = getString(R.string.tv_error_password_minimum_length)
		} else {
			binding.tilPassword.error = null
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