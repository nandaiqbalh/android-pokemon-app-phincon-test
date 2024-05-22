package com.nandaiqbalh.pokemonapp.presentation.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nandaiqbalh.pokemonapp.R
import com.nandaiqbalh.pokemonapp.databinding.FragmentRegisterBinding
import com.nandaiqbalh.pokemonapp.presentation.ui.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

	private var _binding: FragmentRegisterBinding? = null
	private val binding get() = _binding!!

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

	private fun buttonListener(){
		with(binding){
			btnLogin.setOnClickListener {
				findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
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


}