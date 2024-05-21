package com.nandaiqbalh.pokemonapp.presentation.ui.home.detailpokemon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nandaiqbalh.pokemonapp.databinding.FragmentDetailPokemonBinding
import com.nandaiqbalh.pokemonapp.databinding.FragmentMyPokemonListBinding
import com.nandaiqbalh.pokemonapp.databinding.FragmentPokemonListBinding


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

}