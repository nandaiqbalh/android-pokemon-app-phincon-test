package com.nandaiqbalh.pokemonapp.presentation.ui.home.detailpokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.pokemonapp.data.local.datastore.auth.AuthDataStoreManager
import com.nandaiqbalh.pokemonapp.data.remote.model.pokemondetail.response.DetailPokemonResponse
import com.nandaiqbalh.pokemonapp.data.remote.repository.detailpokemon.DetailPokemonRepository
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPokemonViewModel @Inject constructor(
	private val repository: DetailPokemonRepository,
	private val authDataStoreManager: AuthDataStoreManager
) : ViewModel() {

	private var _getDetailPokemonResult = MutableLiveData<Resource<DetailPokemonResponse>>()
	val getDetailPokemonResult: LiveData<Resource<DetailPokemonResponse>> get() = _getDetailPokemonResult

	fun getDetailPokemon(name: String) {

		viewModelScope.launch(Dispatchers.IO) {
			_getDetailPokemonResult.postValue(Resource.Loading())

			try {
				val data =
					repository.getDetailPokemon(name)

				if (data.payload != null) {

					viewModelScope.launch(Dispatchers.Main) {
						_getDetailPokemonResult.postValue(Resource.Success(data.payload))
					}

				} else {
					_getDetailPokemonResult.postValue(Resource.Error(data.exception, null))
				}

			} catch (e: Exception) {
				viewModelScope.launch(Dispatchers.Main) {
					_getDetailPokemonResult.postValue(Resource.Error(e, null))
				}
			}

		}
	}

	fun getStatusAuth(): LiveData<Boolean?> = authDataStoreManager.getStatusAuth.asLiveData()

}