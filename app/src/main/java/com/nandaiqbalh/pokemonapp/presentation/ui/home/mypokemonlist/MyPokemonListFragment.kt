package com.nandaiqbalh.pokemonapp.presentation.ui.home.mypokemonlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nandaiqbalh.pokemonapp.databinding.FragmentMyPokemonListBinding
import com.nandaiqbalh.pokemonapp.databinding.FragmentPokemonListBinding


class MyPokemonListFragment : Fragment() {
	private var _binding: FragmentMyPokemonListBinding? = null
	private val binding get() = _binding!!

	private val myPokemonListViewModel: MyPokemonListViewModel by viewModels()
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentMyPokemonListBinding.inflate(layoutInflater, container, false)
		return binding.root
	}

}