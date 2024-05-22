package com.nandaiqbalh.pokemonapp.presentation.ui.home.detailpokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nandaiqbalh.pokemonapp.databinding.FragmentDetailPokemonBinding


class DetailPokemonFragment : Fragment() {
	private var _binding: FragmentDetailPokemonBinding? = null
	private val binding get() = _binding!!

	private val detailPokemonViewModel: DetailPokemonViewModel by viewModels()
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentDetailPokemonBinding.inflate(layoutInflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setButtonListener()
	}

	// function to trigger action when the user press the button or called an action
	private fun setButtonListener() {

		// back to home
		binding.ivCircleBackArrow.setOnClickListener {
			findNavController().popBackStack()
		}

		// back to home
		binding.icBackArrow.setOnClickListener {
			findNavController().popBackStack()
		}
	}

}