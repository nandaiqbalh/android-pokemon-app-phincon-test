package com.nandaiqbalh.pokemonapp.presentation.ui.home.mypokemonlist

import android.annotation.SuppressLint
import android.app.AlertDialog
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
import com.nandaiqbalh.pokemonapp.data.remote.model.mypokemon.request.MyPokemonRequestBody
import com.nandaiqbalh.pokemonapp.databinding.FragmentMyPokemonListBinding
import com.nandaiqbalh.pokemonapp.presentation.ui.home.myMyPokemon.MyPokemonViewModel
import com.nandaiqbalh.pokemonapp.presentation.ui.home.mypokemonlist.adapter.MyPokemonListAdapter
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyPokemonListFragment : Fragment() {
	private var _binding: FragmentMyPokemonListBinding? = null
	private val binding get() = _binding!!

	private val mypokemonViewModel: MyPokemonViewModel by viewModels()

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
		setLoading(true)

		mypokemonViewModel.getUserId().observe(viewLifecycleOwner) { userId->
			if (userId != null) {
				// do networking to get pokemon data
				mypokemonViewModel.getMyPokemon(MyPokemonRequestBody(userId))

			}
		}
		

		// observe the result of our networking
		mypokemonViewModel.getMyPokemonResult.observe(viewLifecycleOwner) { myPokemonListResult ->

			when (myPokemonListResult) {
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
					if (myPokemonListResult.payload == null) {
						setLoading(false)
						with(binding) {
							setViewVisibility(cvErrorPokemon, true)
							tvErrorPokemon.text = "Error occured!"
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
							override fun onItemClicked(pokemonName: String, pokemonNickname: String?) {
								val action =
									MyPokemonListFragmentDirections.actionMyPokemonListFragmentToDetailPokemonFragment(
										pokemonName, pokemonNickname
									)

								// Menggunakan findNavController() dari fragment saat ini untuk navigasi
								findNavController().navigate(action)
							}

							override fun onReleaseClicked(pokemonId: Int) {
								TODO("Not yet implemented")
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