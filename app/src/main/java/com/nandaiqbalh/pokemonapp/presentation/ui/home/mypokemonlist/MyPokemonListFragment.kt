package com.nandaiqbalh.pokemonapp.presentation.ui.home.mypokemonlist

import android.annotation.SuppressLint
import android.app.AlertDialog
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.nandaiqbalh.pokemonapp.R
import com.nandaiqbalh.pokemonapp.data.remote.model.deletepokemon.request.DeletePokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.mypokemon.request.MyPokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.renamepokemon.request.RenamePokemonRequestBody
import com.nandaiqbalh.pokemonapp.databinding.DialogNicknameBinding
import com.nandaiqbalh.pokemonapp.databinding.FragmentMyPokemonListBinding
import com.nandaiqbalh.pokemonapp.presentation.ui.home.mypokemonlist.adapter.MyPokemonListAdapter
import com.nandaiqbalh.pokemonapp.util.customview.CustomSnackbar
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyPokemonListFragment : Fragment() {
	private var _binding: FragmentMyPokemonListBinding? = null
	private val binding get() = _binding!!

	private val mypokemonViewModel: MyPokemonViewModel by viewModels()
	private val customSnackbar = CustomSnackbar()

	private var isAlertDialogShowing = false

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentMyPokemonListBinding.inflate(layoutInflater, container, false)
		return binding.root
	}


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		// call the function
		setPokemonRecyclerView()
		setButtonListener()

	}

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
	private fun setPokemonRecyclerView() {

		// set initial state to loading
		setLoadingList(true)

		mypokemonViewModel.getUserId().observe(viewLifecycleOwner) { userId ->
			if (userId != null) {
				// do networking to get pokemon data
				mypokemonViewModel.getMyPokemon(MyPokemonRequestBody(userId))

			}
		}


		// observe the result of our networking
		mypokemonViewModel.getMyPokemonResult.observe(viewLifecycleOwner) { myPokemonListResult ->

			when (myPokemonListResult) {
				is Resource.Loading -> {
					setLoadingList(true)
				}

				is Resource.Error -> {
					setLoadingList(false)

					// Log and show the message

					with(binding) {
						setViewVisibility(cvErrorPokemon, true)
						tvErrorPokemon.text = "Error occured!"

						setViewVisibility(cvPokemon, false)

					}

				}

				is Resource.Success -> {
					setLoadingList(false)
					if (myPokemonListResult.payload!!.data == null) {
						setLoadingList(false)
						with(binding) {
							setViewVisibility(cvErrorPokemon, true)
							tvErrorPokemon.text = myPokemonListResult.data.status
							setViewVisibility(cvPokemon, false)
						}
					} else {
						val pokemonListAdapter = MyPokemonListAdapter()

						pokemonListAdapter.setList(myPokemonListResult.payload.data.data)
						binding.rvPokemon.layoutManager = LinearLayoutManager(
							requireContext(),
							LinearLayoutManager.VERTICAL,
							false
						)
						binding.rvPokemon.adapter = pokemonListAdapter

						// navigate to detail
						pokemonListAdapter.setOnItemClickCallback(object :
							MyPokemonListAdapter.OnItemClickCallBack {
							override fun onItemClicked(
								pokemonName: String,
								pokemonNickname: String?,
							) {
								val action =
									MyPokemonListFragmentDirections.actionMyPokemonListFragmentToDetailPokemonFragment(
										pokemonName, pokemonNickname
									)

								// Menggunakan findNavController() dari fragment saat ini untuk navigasi
								findNavController().navigate(action)
							}

							override fun onReleaseClicked(pokemonId: Int) {
								showCustomAlertDialog(
									"Confirmation",
									"Are you sure release this pokemon?",
									{
										mypokemonViewModel.releasePokemon()

										releasePokemonResult(pokemonId)
									},
									{
										// Aksi yang akan dijalankan saat tombol "No" ditekan

									}
								)
							}

							override fun onRenameClicked(
								userId: Int?,
								pokemonId: Int?,
								nickname: String?,
							) {
								showDialogNickname(
									title = "Rename",
									message = "Enter new nickname for this pokemon."
								) { enteredNickname ->
									// do store pokemon

									// stored request body
									mypokemonViewModel.renamePokemon(
										RenamePokemonRequestBody(
											userId,
											pokemonId,
											enteredNickname
										)
									)
									renamePokemonResult()
								}
							}
						})

					}


				}

				else -> {}

			}
		}
	}

	private fun releasePokemonResult(pokemonId: Int) {
		mypokemonViewModel.getReleasePokemonResult.observe(viewLifecycleOwner) { releasePokemonResult ->

			when (releasePokemonResult) {
				is Resource.Loading -> setLoadingList(true)
				is Resource.Error -> {
					setLoadingList(false)
					Log.d("Result status", releasePokemonResult.payload?.status.toString())

					customSnackbar.showSnackbarWithAction(
						requireActivity().findViewById(android.R.id.content),
						releasePokemonResult.payload?.status ?: "Error occured!",
						"OK"
					) {
						customSnackbar.dismissSnackbar()
					}
				}

				is Resource.Success -> {
					setLoadingList(false)
					Log.d("Result status", releasePokemonResult.payload?.status.toString())

					val releaseResult = releasePokemonResult.payload

					if (isPrime(releaseResult?.data ?: 0)) {
						// do networking to remove pokemon from database
						mypokemonViewModel.getUserId().observe(viewLifecycleOwner) { userId ->
							if (userId != null) {
								// do networking to get pokemon data
								mypokemonViewModel.deletePokemon(
									DeletePokemonRequestBody(
										userId = userId,
										pokemonId = pokemonId
									)
								)

								deletePokemonResult()
							}
						}
					} else {
						// if the success is false, then just show the snackbar
						customSnackbar.showSnackbarWithAction(
							requireActivity().findViewById(android.R.id.content),
							"Release failed! [${releaseResult?.data}]",
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

	private fun renamePokemonResult() {
		mypokemonViewModel.getRenamePokemonResult.observe(viewLifecycleOwner) { getRenamePokemonResult ->

			when (getRenamePokemonResult) {
				is Resource.Loading -> setLoadingList(true)
				is Resource.Error -> {
					setLoadingList(false)
					Log.d("Result status", getRenamePokemonResult.payload?.status.toString())

					customSnackbar.showSnackbarWithAction(
						requireActivity().findViewById(android.R.id.content),
						getRenamePokemonResult.payload?.status ?: "Error occured!",
						"OK"
					) {
						customSnackbar.dismissSnackbar()
					}
				}

				is Resource.Success -> {
					setLoadingList(false)
					Log.d("Result status", getRenamePokemonResult.payload?.status.toString())

					val deletePokemonResult = getRenamePokemonResult.payload

					if (deletePokemonResult?.success == true) {

						// show snackbar
						customSnackbar.showSnackbarWithAction(
							requireActivity().findViewById(android.R.id.content),
							deletePokemonResult.status,
							"OK"
						) {
							customSnackbar.dismissSnackbar()
						}

						val action = MyPokemonListFragmentDirections.actionMyPokemonListFragmentToPokemonListFragment()
						findNavController().navigate(action)
					} else {
						// if the success is false, then just show the snackbar
						customSnackbar.showSnackbarWithAction(
							requireActivity().findViewById(android.R.id.content),
							deletePokemonResult?.status ?: "Authentication failed!",
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

	private fun deletePokemonResult() {
		mypokemonViewModel.getDeletePokemonResult.observe(viewLifecycleOwner) { getDeletePokemonResult ->

			when (getDeletePokemonResult) {
				is Resource.Loading -> setLoadingList(true)
				is Resource.Error -> {
					setLoadingList(false)
					Log.d("Result status", getDeletePokemonResult.payload?.status.toString())

					customSnackbar.showSnackbarWithAction(
						requireActivity().findViewById(android.R.id.content),
						getDeletePokemonResult.payload?.status ?: "Error occured!",
						"OK"
					) {
						customSnackbar.dismissSnackbar()
					}
				}

				is Resource.Success -> {
					setLoadingList(false)
					Log.d("Result status", getDeletePokemonResult.payload?.status.toString())

					val deletePokemonResult = getDeletePokemonResult.payload

					if (deletePokemonResult?.success == true) {

						// show snackbar
						customSnackbar.showSnackbarWithAction(
							requireActivity().findViewById(android.R.id.content),
							deletePokemonResult.status,
							"OK"
						) {
							customSnackbar.dismissSnackbar()
						}

						val action = MyPokemonListFragmentDirections.actionMyPokemonListFragmentToPokemonListFragment()
						findNavController().navigate(action)

					} else {
						// if the success is false, then just show the snackbar
						customSnackbar.showSnackbarWithAction(
							requireActivity().findViewById(android.R.id.content),
							deletePokemonResult?.status ?: "Authentication failed!",
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

	private fun isPrime(number: Int): Boolean {
		if (number <= 1) {
			return false
		}
		for (i in 2 until number) {
			if (number % i == 0) {
				return false
			}
		}
		return true
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
	
	private fun setLoadingList(isLoading: Boolean) {
		if (isLoading) {
			binding.pbList.visibility = View.VISIBLE
		} else {
			binding.pbList.visibility = View.GONE
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