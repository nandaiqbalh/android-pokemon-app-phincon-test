package com.nandaiqbalh.pokemonapp.presentation.ui.home.detailpokemon

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.facebook.shimmer.ShimmerFrameLayout
import com.nandaiqbalh.pokemonapp.data.remote.model.pokemondetail.response.Move
import com.nandaiqbalh.pokemonapp.data.remote.model.pokemondetail.response.Type
import com.nandaiqbalh.pokemonapp.databinding.FragmentDetailPokemonBinding
import com.nandaiqbalh.pokemonapp.util.GlideApp
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
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

		getPokemonDetailData()
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

	@SuppressLint("SetTextI18n")
	private fun getPokemonDetailData() {

		// get name from the adapter with NavArgs
		val pokemonName = DetailPokemonFragmentArgs.fromBundle(requireArguments()).pokemonName


		// set initial state to loading
		setLoading(true)

		// do networking to get pokemon data
		detailPokemonViewModel.getDetailPokemon(pokemonName.toString())

		// observe the result of our networking
		detailPokemonViewModel.getDetailPokemonResult.observe(viewLifecycleOwner) { detailPokemonResult ->

			when (detailPokemonResult) {

				is Resource.Loading -> {
					// set loading when still loading the data from network
					setLoading(true)
				}

				is Resource.Error -> {
					// set loading to false (not loading anymore) if the result is error
					setLoading(false)

					with(binding) {
						setViewVisibility(cvErrorDetailPokemon, true)
						tvErrorDetailPokemon.text = "Error occured!"

						setViewVisibility(linearLayoutDetailPokemon, false)
						setViewVisibility(shimmerDetailPokemonFragment, false)

					}
				}

				is Resource.Success -> {

					// set loading to false if we got response from the network
					setLoading(false)


					// data is not null, then set the view with the data
					if (detailPokemonResult.payload?.id != null) {

						binding.apply {

							GlideApp.with(this@DetailPokemonFragment).asBitmap()
								.load(detailPokemonResult.payload.sprites.frontDefault)
								.into(ivDetailPokemon)

							tvTitlePokemonDetail.text = detailPokemonResult.payload.name
							tvBaseExp.text =
								"Base exp: ${detailPokemonResult.payload.baseExperience}"

							// Extract move names and concatenate into a single line of text
							val types: List<Type> = detailPokemonResult.payload.types
							val typesText = types.joinToString(", ") { it.type.name }
							tvType.text =
								"Types: $typesText"

							// Extract move names and concatenate into a single line of text
							val moves: List<Move> = detailPokemonResult.payload.moves
							val movesText = moves.joinToString(", ") { it.move.name }
							tvMoves.text =
								"Moves: $movesText"

							setViewVisibility(cvErrorDetailPokemon, false)
						}
					} else {
						setLoading(false)

						with(binding) {
							setViewVisibility(cvErrorDetailPokemon, true)
							tvErrorDetailPokemon.text = "Error occured!"

							setViewVisibility(linearLayoutDetailPokemon, false)
							setViewVisibility(shimmerDetailPokemonFragment, false)

						}

					}
				}

				else -> {}

			}
		}
	}

	private fun setLoading(isLoading: Boolean) {
		with(binding) {
			setShimmerVisibility(shimmerDetailPokemonFragment, isLoading)
			linearLayoutDetailPokemon.visibility = if (isLoading) View.GONE else View.VISIBLE
			cvErrorDetailPokemon.visibility = if (isLoading) View.GONE else View.VISIBLE
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