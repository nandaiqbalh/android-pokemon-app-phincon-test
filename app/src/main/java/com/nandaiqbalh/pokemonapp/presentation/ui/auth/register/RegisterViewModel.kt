package com.nandaiqbalh.pokemonapp.presentation.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nandaiqbalh.pokemonapp.data.local.datastore.auth.AuthDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
	private val authDataStoreManager: AuthDataStoreManager
) : ViewModel() {

	fun getStatusAuth(): LiveData<Boolean?> = authDataStoreManager.getStatusAuth.asLiveData()

}