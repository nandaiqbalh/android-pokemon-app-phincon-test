package com.nandaiqbalh.pokemonapp.presentation.ui.auth.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.pokemonapp.data.local.datastore.auth.AuthDataStoreManager
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.login.request.AuthLoginRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.login.response.AuthLoginRemoteResponse
import com.nandaiqbalh.pokemonapp.data.remote.repository.auth.login.AuthLoginRemoteRepository
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
	private val authLoginRemoteRepository: AuthLoginRemoteRepository,
	private val authDataStoreManager: AuthDataStoreManager
) : ViewModel() {

	// login
	private val _authLoginResult = MutableLiveData<Resource<AuthLoginRemoteResponse>>()
	val authLoginResult: LiveData<Resource<AuthLoginRemoteResponse>> get() = _authLoginResult // LiveData untuk diobserve di luar kelas

	fun authLogin(authLoginRequestBody: AuthLoginRequestBody) {
		viewModelScope.launch(Dispatchers.IO) {
			_authLoginResult.postValue(Resource.Loading())

			try {
				val data = authLoginRemoteRepository.authLogin(authLoginRequestBody)

				Log.d("PAYLOAD", data.payload.toString())
				if (data.payload != null) {
					viewModelScope.launch(Dispatchers.Main) {
						_authLoginResult.postValue(Resource.Success(data.payload))

					}
				} else {
					_authLoginResult.postValue(Resource.Error(data.exception, null))
				}
			} catch (e: Exception) {
				viewModelScope.launch(Dispatchers.Main) {
					_authLoginResult.postValue(Resource.Error(e, null))
				}
			}
		}
	}

	fun getStatusAuth(): LiveData<Boolean?> = authDataStoreManager.getStatusAuth.asLiveData()
	fun setStatusAuth(statusAuth: Boolean) = CoroutineScope(Dispatchers.IO).launch {
		authDataStoreManager.setStatusAuth(statusAuth)
	}

	fun getUserId(): LiveData<Int?> = authDataStoreManager.getUserId.asLiveData()

	fun setUserId(userId: Int) = CoroutineScope(Dispatchers.IO).launch {
		authDataStoreManager.setUserId(userId)
	}

	fun getUsername(): LiveData<String?> = authDataStoreManager.getUsername.asLiveData()

	fun setUsername(username: String) = CoroutineScope(Dispatchers.IO).launch {
		authDataStoreManager.setUsername(username)
	}

}