package com.nandaiqbalh.pokemonapp.presentation.ui.home.pokemonlist

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.nandaiqbalh.pokemonapp.R
import com.nandaiqbalh.pokemonapp.databinding.FragmentPokemonListBinding
import com.nandaiqbalh.pokemonapp.presentation.ui.auth.AuthActivity
import com.nandaiqbalh.pokemonapp.presentation.ui.home.pokemonlist.adapter.PokemonListAdapter
import com.nandaiqbalh.pokemonapp.presentation.ui.splashscreen.SplashscreenActivity
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonListFragment : Fragment() {
	private var _binding: FragmentPokemonListBinding? = null
	private val binding get() = _binding!!

	private val pokemonListViewModel: PokemonListViewModel by viewModels()

	private var isAlertDialogShowing = false

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

		setUsername()
	}

	private fun setUsername() {
		pokemonListViewModel.getUsername().observe(viewLifecycleOwner) { username ->
			if (username != null && username != "") {
				binding.tvUsername.text = username
			}
		}

	}

	// function to trigger the action when the user doing an action
	private fun setButtonListener() {
		pokemonListViewModel.getStatusAuth().observe(viewLifecycleOwner) { statusAuth ->

			if (statusAuth == false){
				binding.icHomeLogout.visibility = View.GONE
			}

			binding.ivHomeSave.setOnClickListener {
				if (statusAuth == true) {
					findNavController().navigate(R.id.action_pokemonListFragment_to_myPokemonListFragment)

				} else {
					// show dialog to ask user
					showCustomAlertDialog(
						"Confirmation",
						"You are not logged in. Do you want to login?",
						{
							val intent = Intent(context, AuthActivity::class.java)
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

		binding.icHomeLogout.setOnClickListener {
			showCustomAlertDialog(
				"Confirmation",
				"Are you sure to logout?",
				{
					pokemonListViewModel.setStatusAuth(false)
					pokemonListViewModel.setUsername("")

					val intent = Intent(context, SplashscreenActivity::class.java)
					startActivity(intent)
					requireActivity().finish()

				},
				{
					// Aksi yang akan dijalankan saat tombol "No" ditekan

				}
			)
		}
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
							override fun onItemClicked(
								pokemonName: String,
								pokemonNikname: String?,
							) {
								val action =
									PokemonListFragmentDirections.actionPokemonListFragmentToDetailPokemonFragment(
										pokemonName, null
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