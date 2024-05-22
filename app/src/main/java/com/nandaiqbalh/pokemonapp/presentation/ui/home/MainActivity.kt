package com.nandaiqbalh.pokemonapp.presentation.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.nandaiqbalh.pokemonapp.R
import com.nandaiqbalh.pokemonapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	private lateinit var navController: NavController
	private var _binding: ActivityMainBinding? = null
	private val binding get() = _binding!!

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		supportActionBar?.hide()

		val navHostFragment = supportFragmentManager
			.findFragmentById(R.id.fragmentContainerViewHome) as NavHostFragment

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