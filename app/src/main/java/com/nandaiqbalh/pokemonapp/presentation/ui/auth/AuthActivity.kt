package com.nandaiqbalh.pokemonapp.presentation.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.nandaiqbalh.pokemonapp.R
import com.nandaiqbalh.pokemonapp.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
	private lateinit var navController: NavController
	private var _binding: ActivityAuthBinding? = null
	private val binding get() = _binding!!

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivityAuthBinding.inflate(layoutInflater)
		setContentView(binding.root)

		supportActionBar?.hide()

		val navHostFragment = supportFragmentManager
			.findFragmentById(R.id.fragmentContainerViewAuth) as NavHostFragment

		navController = navHostFragment.navController

	}

	override fun onSupportNavigateUp(): Boolean {
		return navController.navigateUp() || super.onSupportNavigateUp()
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}