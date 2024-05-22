package com.nandaiqbalh.pokemonapp.presentation.ui.auth.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.pokemonapp.data.local.datastore.auth.AuthDataStoreManager
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.register.request.AuthRegisterRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.register.response.AuthRegisterRemoteResponse
import com.nandaiqbalh.pokemonapp.data.remote.repository.auth.register.AuthRegisterRemoteRepository
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
	private val authRegisterRemoteRepository: AuthRegisterRemoteRepository,
	private val authDataStoreManager: AuthDataStoreManager,
) : ViewModel() {

	fun getStatusAuth(): LiveData<Boolean?> = authDataStoreManager.getStatusAuth.asLiveData()

	// Register
	private val _authRegisterResult = MutableLiveData<Resource<AuthRegisterRemoteResponse>>()
	val authRegisterResult: LiveData<Resource<AuthRegisterRemoteResponse>> get() = _authRegisterResult // LiveData untuk diobserve di luar kelas

	fun authRegister(authRegisterRequestBody: AuthRegisterRequestBody) {
		viewModelScope.launch(Dispatchers.IO) {
			_authRegisterResult.postValue(Resource.Loading())

			try {
				val data = authRegisterRemoteRepository.authRegister(authRegisterRequestBody)

				Log.d("PAYLOAD", data.payload.toString())
				if (data.payload != null) {
					viewModelScope.launch(Dispatchers.Main) {
						_authRegisterResult.postValue(Resource.Success(data.payload))

					}
				} else {
					_authRegisterResult.postValue(Resource.Error(data.exception, null))
				}
			} catch (e: Exception) {
				viewModelScope.launch(Dispatchers.Main) {
					_authRegisterResult.postValue(Resource.Error(e, null))
				}
			}
		}
	}
}