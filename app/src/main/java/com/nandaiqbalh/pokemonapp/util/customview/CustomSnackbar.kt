package com.nandaiqbalh.pokemonapp.util.customview

import android.view.View
import com.google.android.material.snackbar.Snackbar

class CustomSnackbar {

	private var snackbar: Snackbar? = null
	private var text: String? = null
	private var lastSnackbar: Long = 0
	private val SAME_SNACKBAR_DURATION_BEFORE_OVERLAP = 2000 // in ms

	fun showSnackbarWithAction(view: View, text: String, actionText: String, actionListener: View.OnClickListener) {
		if (snackbar == null) {
			snackbar = Snackbar.make(view, text, Snackbar.LENGTH_INDEFINITE)
				.setAction(actionText, actionListener)
			this.text = text
		} else {
			if (this.text == text) {
				if (System.currentTimeMillis() - lastSnackbar > SAME_SNACKBAR_DURATION_BEFORE_OVERLAP) {
					snackbar!!.dismiss()
				} else {
					return
				}
			} else {
				this.text = text
				snackbar!!.dismiss()
				snackbar = Snackbar.make(view, this.text!!, Snackbar.LENGTH_INDEFINITE)
					.setAction(actionText, actionListener)
			}
		}
		lastSnackbar = System.currentTimeMillis()
		snackbar!!.show()
	}


	fun dismissSnackbar() {
		snackbar?.dismiss()
	}
}
