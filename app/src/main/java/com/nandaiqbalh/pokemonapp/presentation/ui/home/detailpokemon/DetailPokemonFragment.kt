package com.nandaiqbalh.pokemonapp.presentation.ui.home.detailpokemon

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.facebook.shimmer.ShimmerFrameLayout
import com.nandaiqbalh.pokemonapp.R
import com.nandaiqbalh.pokemonapp.data.remote.model.catchpokemon.request.CatchPokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.pokemondetail.response.Move
import com.nandaiqbalh.pokemonapp.data.remote.model.pokemondetail.response.Type
import com.nandaiqbalh.pokemonapp.data.remote.model.storepokemon.request.StorePokemonRequestBody
import com.nandaiqbalh.pokemonapp.databinding.DialogNicknameBinding
import com.nandaiqbalh.pokemonapp.databinding.FragmentDetailPokemonBinding
import com.nandaiqbalh.pokemonapp.presentation.ui.auth.AuthActivity
import com.nandaiqbalh.pokemonapp.util.CustomSnackbar
import com.nandaiqbalh.pokemonapp.util.GlideApp
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailPokemonFragment : Fragment() {
	private var _binding: FragmentDetailPokemonBinding? = null
	private val binding get() = _binding!!

	private val detailPokemonViewModel: DetailPokemonViewModel by viewModels()

	private var isAlertDialogShowing = false
	private val customSnackbar = CustomSnackbar()
	private lateinit var storePokemonRequestBody: StorePokemonRequestBody
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
		val pokemonNickname =
			DetailPokemonFragmentArgs.fromBundle(requireArguments()).pokemonNickname


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

							if (pokemonNickname != null) {
								tvTitlePokemonDetail.text =
									"(${pokemonNickname}) ${detailPokemonResult.payload.name}"
							} else {
								tvTitlePokemonDetail.text = detailPokemonResult.payload.name
							}

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

							// catch pokemon
							binding.btnCatch.setOnClickListener {
								detailPokemonViewModel.getStatusAuth()
									.observe(viewLifecycleOwner) { statusAuth ->
										if (statusAuth == true) {
											// do networking to catch pokemon
											detailPokemonViewModel.getUserId()
												.observe(viewLifecycleOwner) { userId ->
													if (userId != null) {
														detailPokemonViewModel.catchPokemon(
															CatchPokemonRequestBody(
																userId = userId,
																pokemonId = detailPokemonResult.payload.id
															)
														)
														catchPokemon()

													}
												}
										} else {
											// show dialog to ask user
											showCustomAlertDialog(
												"Confirmation",
												"You are not logged in. Do you want to login?",
												{
													val intent =
														Intent(context, AuthActivity::class.java)
													startActivity(intent)
													requireActivity().finish()
												},
												{
													// Aksi yang akan dijalankan saat tombol "No" ditekan

												}
											)

										}

									}
							}

							// make request body form
							detailPokemonViewModel.getUserId()
								.observe(viewLifecycleOwner) { userId ->
									if (userId != null) {
										storePokemonRequestBody = StorePokemonRequestBody(
											userId = userId,
											pokemonId = detailPokemonResult.payload.id,
											name = detailPokemonResult.payload.name,
											nickname = null
										)

									}
								}


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

	private fun catchPokemon() {

		// observe the result of our networking
		detailPokemonViewModel.getCatchPokemonResult.observe(viewLifecycleOwner) { getCatchPokemonResult ->

			when (getCatchPokemonResult) {

				is Resource.Loading -> {
					// set loading when still loading the data from network
					setLoadingCatch(true)
				}

				is Resource.Error -> {
					// set loading to false (not loading anymore) if the result is error
					setLoadingCatch(false)

					customSnackbar.showSnackbarWithAction(
						requireActivity().findViewById(android.R.id.content),
						getCatchPokemonResult.payload?.status ?: "Error occured!",
						"OK"
					) {
						customSnackbar.dismissSnackbar()
					}
				}

				is Resource.Success -> {

					// set loading to false if we got response from the network
					setLoadingCatch(false)

					if (getCatchPokemonResult.payload?.success == true) {
						// show dialog to give nickname
						showDialogNickname(
							title = "Successful",
							message = "Pokemon caught! Please give a nickname."
						) { nickname ->
							// do store pokemon

							// stored request body
							val updatedRequestBody =
								storePokemonRequestBody.copy(nickname = nickname)

							storePokemon(updatedRequestBody)
						}

					} else {
						setLoadingCatch(false)

						// show snackbar
						customSnackbar.showSnackbarWithAction(
							requireActivity().findViewById(android.R.id.content),
							getCatchPokemonResult.payload?.status ?: "Error occured!",
							"OK"
						) {
							customSnackbar.dismissSnackbar()
						}
					}
				}

				else -> {}
			}
		}
	}

	private fun storePokemon(storePokemonRequestBody: StorePokemonRequestBody) {

		detailPokemonViewModel.storePokemon(storePokemonRequestBody)

		detailPokemonViewModel.getStorePokemonResult.observe(viewLifecycleOwner) { getStorePokemonResult ->

			when (getStorePokemonResult) {
				is Resource.Loading -> setLoadingCatch(true)
				is Resource.Error -> {
					setLoadingCatch(false)
					Log.d("Result status", getStorePokemonResult.payload?.status.toString())

					customSnackbar.showSnackbarWithAction(
						requireActivity().findViewById(android.R.id.content),
						getStorePokemonResult.payload?.status ?: "Error occured!",
						"OK"
					) {
						customSnackbar.dismissSnackbar()
					}
				}

				is Resource.Success -> {
					setLoadingCatch(false)
					Log.d("Result status", getStorePokemonResult.payload?.status.toString())

					val storePokemonResult = getStorePokemonResult.payload

					if (storePokemonResult?.success == true) {

						// show snackbar
						customSnackbar.showSnackbarWithAction(
							requireActivity().findViewById(android.R.id.content),
							storePokemonResult.status,
							"OK"
						) {
							customSnackbar.dismissSnackbar()
						}


					} else {
						// if the success is false, then just show the snackbar
						customSnackbar.showSnackbarWithAction(
							requireActivity().findViewById(android.R.id.content),
							storePokemonResult?.status ?: "Failed to store Pokémon (Insert failed)",
							"OK"
						) {
							customSnackbar.dismissSnackbar()
						}
					}
				}

				else -> {}

			}
		}
	}

	private fun showDialogNickname(
		title: String,
		message: String,
		positiveAction: (nickname: String) -> Unit,
	) {
		if (isAlertDialogShowing) {
			// Jika alert dialog sedang ditampilkan, keluar dari fungsi
			return
		}
		isAlertDialogShowing = true

		val builder = AlertDialog.Builder(requireContext()).create()

		// Use ViewBinding to inflate the layout
		val binding = DialogNicknameBinding.inflate(layoutInflater)
		builder.setView(binding.root)
		builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

		binding.tvAlertTitle.text = title
		binding.tvAlertMessage.text = message

		binding.btnAlertYes.setOnClickListener {
			val nickname = binding.edtNickname.text.toString() // Retrieve the value from EditText
			positiveAction.invoke(nickname) // Pass the value to the positiveAction callback
			builder.dismiss()
			isAlertDialogShowing = false // Setelah menutup dialog, atur kembali flag
		}

		builder.setOnDismissListener {
			isAlertDialogShowing = false // Atur kembali flag saat dialog ditutup
		}

		builder.setCanceledOnTouchOutside(false)
		builder.show()
	}


	private fun showCustomAlertDialog(
		title: String,
		message: String,
		positiveAction: () -> Unit,
		negativeAction: () -> Unit,
	) {
		if (isAlertDialogShowing) {
			// Jika alert dialog sedang ditampilkan, keluar dari fungsi
			return
		}
		isAlertDialogShowing = true

		val builder = AlertDialog.Builder(requireContext()).create()
		val view = layoutInflater.inflate(R.layout.dialog_custom_alert_dialog, null)
		builder.setView(view)
		builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

		val buttonYes = view.findViewById<Button>(R.id.btn_alert_yes)
		val buttonNo = view.findViewById<Button>(R.id.btn_alert_no)
		val alertTitle = view.findViewById<TextView>(R.id.tv_alert_title)
		val alertMessage = view.findViewById<TextView>(R.id.tv_alert_message)

		alertTitle.text = title
		alertMessage.text = message

		buttonYes.setOnClickListener {
			positiveAction.invoke()
			builder.dismiss()
			isAlertDialogShowing = false // Setelah menutup dialog, atur kembali flag
		}

		buttonNo.setOnClickListener {
			negativeAction.invoke() // Panggil aksi "No" di sini
			builder.dismiss()
			isAlertDialogShowing = false // Setelah menutup dialog, atur kembali flag
		}
		builder.setOnDismissListener {
			isAlertDialogShowing = false // Atur kembali flag saat dialog ditutup
		}

		builder.setCanceledOnTouchOutside(false)
		builder.show()
	}

	private fun setLoadingCatch(isLoading: Boolean) {
		if (isLoading) {
			binding.pbCatch.visibility = View.VISIBLE
		} else {
			binding.pbCatch.visibility = View.GONE
		}
	}

	private fun setLoading(isLoading: Boolean) {
		with(binding) {
			setShimmerVisibility(shimmerDetailPokemonFragment, isLoading)
			linearLayoutDetailPokemon.visibility =
				if (isLoading) View.GONE else View.VISIBLE
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