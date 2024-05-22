package com.nandaiqbalh.pokemonapp.presentation.ui.auth.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nandaiqbalh.pokemonapp.databinding.FragmentSplashscreenBinding
import com.nandaiqbalh.pokemonapp.presentation.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashscreenFragment : Fragment() {

	private var _binding: FragmentSplashscreenBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentSplashscreenBinding.inflate(layoutInflater, container, false)

		return binding.root
	}


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		doSplashscreen()

	}

	private fun doSplashscreen() {
		Handler().postDelayed({

			val intent = Intent(context, MainActivity::class.java)
			startActivity(intent)
			requireActivity().finish()

		}, 3000)
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

}