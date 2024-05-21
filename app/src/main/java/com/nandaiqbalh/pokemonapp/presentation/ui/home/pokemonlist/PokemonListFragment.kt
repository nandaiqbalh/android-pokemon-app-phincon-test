package com.nandaiqbalh.pokemonapp.presentation.ui.home.pokemonlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nandaiqbalh.pokemonapp.databinding.FragmentPokemonListBinding


class PokemonListFragment : Fragment() {
	private var _binding: FragmentPokemonListBinding? = null
	private val binding get() = _binding!!

	private val pokemonListViewModel: PokemonListViewModel by viewModels()
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentPokemonListBinding.inflate(layoutInflater, container, false)
		return binding.root
	}

}