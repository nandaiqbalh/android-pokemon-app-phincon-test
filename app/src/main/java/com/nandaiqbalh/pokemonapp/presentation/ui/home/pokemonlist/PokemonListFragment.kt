package com.nandaiqbalh.pokemonapp.presentation.ui.home.pokemonlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.nandaiqbalh.pokemonapp.databinding.FragmentPokemonListBinding
import com.nandaiqbalh.pokemonapp.presentation.ui.home.pokemonlist.adapter.PokemonListAdapter
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		// call the function
		setButtonListener()
		setPokemonRecyclerView()
	}

	// function to trigger the action when the user doing an action
	private fun setButtonListener() {

	}

	@SuppressLint("SetTextI18n")
	private fun setPokemonRecyclerView() {

		// set initial state to loading
		setLoading(true)

		// do networking to get pokemon data
		pokemonListViewModel.getPokemonList()

		// observe the result of our networking
		pokemonListViewModel.getPokemonListResult.observe(viewLifecycleOwner) { pokemonListResult ->

			when (pokemonListResult) {
				is Resource.Loading -> {
					setLoading(true)
				}

				is Resource.Error -> {
					setLoading(false)

					// Log and show the message

					with(binding) {
						setViewVisibility(cvErrorPokemon, true)
						tvErrorPokemon.text = "Error occured!"

						setViewVisibility(cvPokemon, false)

					}

				}

				is Resource.Success -> {
					setLoading(false)
					if (pokemonListResult.payload == null) {
						setLoading(false)
						with(binding) {
							setViewVisibility(cvErrorPokemon, true)
							tvErrorPokemon.text = "Error occured!"
							setViewVisibility(cvPokemon, false)
						}
					} else {
						val pokemonListAdapter = PokemonListAdapter()

						pokemonListAdapter.setList(pokemonListResult.payload.results)
						binding.rvPokemon.layoutManager = LinearLayoutManager(
							requireContext(),
							LinearLayoutManager.VERTICAL,
							false
						)
						binding.rvPokemon.adapter = pokemonListAdapter

						// navigate to detail
						pokemonListAdapter.setOnItemClickCallback(object :
							PokemonListAdapter.OnItemClickCallBack {
							override fun onItemClicked(pokemonName: String) {
								val action =
									PokemonListFragmentDirections.actionPokemonListFragmentToDetailPokemonFragment(
										pokemonName
									)

								// Menggunakan findNavController() dari fragment saat ini untuk navigasi
								findNavController().navigate(action)
							}
						})

					}


				}

				else -> {}

			}
		}
	}

	private fun setLoading(isLoading: Boolean) {
		with(binding) {
			setShimmerVisibility(shimmerCvPokemon, isLoading)
		}
	}

	private fun setShimmerVisibility(shimmerView: View, isLoading: Boolean) {
		shimmerView.visibility = if (isLoading) View.VISIBLE else View.GONE
		(shimmerView as? ShimmerFrameLayout)?.run {
			if (isLoading) startShimmer() else stopShimmer()
		}
	}

	private fun setViewVisibility(view: View, isVisible: Boolean) {
		view.visibility = if (isVisible) View.VISIBLE else View.GONE
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

}