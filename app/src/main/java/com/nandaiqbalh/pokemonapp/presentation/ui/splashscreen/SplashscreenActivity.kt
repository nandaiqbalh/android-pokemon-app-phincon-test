package com.nandaiqbalh.pokemonapp.presentation.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.nandaiqbalh.pokemonapp.databinding.ActivitySplashscreenBinding
import com.nandaiqbalh.pokemonapp.presentation.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashscreenActivity : AppCompatActivity() {

	private var _binding: ActivitySplashscreenBinding? = null
	private val binding get() = _binding!!


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		requestWindowFeature(Window.FEATURE_NO_TITLE)
		window.setFlags(
			WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN
		)

		_binding = ActivitySplashscreenBinding.inflate(layoutInflater)
		setContentView(binding.root)

		doSplashscreen()
	}

	private fun doSplashscreen() {
		Handler().postDelayed({

			val intent = Intent(this@SplashscreenActivity, MainActivity::class.java)
			startActivity(intent)
			finish()
		}, 3000)
	}


	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}