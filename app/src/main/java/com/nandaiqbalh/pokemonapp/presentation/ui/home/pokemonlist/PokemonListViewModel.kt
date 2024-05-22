package com.nandaiqbalh.pokemonapp.presentation.ui.home.pokemonlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.pokemonapp.data.local.datastore.auth.AuthDataStoreManager
import com.nandaiqbalh.pokemonapp.data.remote.model.pokemonlist.response.PokemonListResponse
import com.nandaiqbalh.pokemonapp.data.remote.repository.pokemonlist.PokemonListRepository
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
	private val repository: PokemonListRepository,
	private val authDataStoreManager: AuthDataStoreManager
) : ViewModel() {

	private var _getPokemonListResult = MutableLiveData<Resource<PokemonListResponse>>()
	val getPokemonListResult: LiveData<Resource<PokemonListResponse>> get() = _getPokemonListResult

	fun getPokemonList() {

		viewModelScope.launch(Dispatchers.IO) {
			_getPokemonListResult.postValue(Resource.Loading())

			try {
				val data =
					repository.getPokemonList()

				if (data.payload != null) {

					viewModelScope.launch(Dispatchers.Main) {
						_getPokemonListResult.postValue(Resource.Success(data.payload))
					}

				} else {
					_getPokemonListResult.postValue(Resource.Error(data.exception, null))
				}

			} catch (e: Exception) {
				viewModelScope.launch(Dispatchers.Main) {
					_getPokemonListResult.postValue(Resource.Error(e, null))
				}
			}

		}
	}

	fun getStatusAuth(): LiveData<Boolean?> = authDataStoreManager.getStatusAuth.asLiveData()
	fun setStatusAuth(statusAuth: Boolean) = CoroutineScope(Dispatchers.IO).launch {
		authDataStoreManager.setStatusAuth(statusAuth)
	}

	fun getUsername(): LiveData<String?> = authDataStoreManager.getUsername.asLiveData()

	fun setUsername(username: String) = CoroutineScope(Dispatchers.IO).launch {
		authDataStoreManager.setUsername(username)
	}

}