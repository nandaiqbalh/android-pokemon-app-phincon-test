package com.nandaiqbalh.pokemonapp.presentation.ui.home.detailpokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.pokemonapp.data.local.datastore.auth.AuthDataStoreManager
import com.nandaiqbalh.pokemonapp.data.remote.model.catchpokemon.request.CatchPokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.catchpokemon.response.CatchPokemonResponse
import com.nandaiqbalh.pokemonapp.data.remote.model.pokemondetail.response.DetailPokemonResponse
import com.nandaiqbalh.pokemonapp.data.remote.model.storepokemon.request.StorePokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.storepokemon.response.StorePokemonResponse
import com.nandaiqbalh.pokemonapp.data.remote.repository.catchpokemon.CatchPokemonRepository
import com.nandaiqbalh.pokemonapp.data.remote.repository.detailpokemon.DetailPokemonRepository
import com.nandaiqbalh.pokemonapp.data.remote.repository.storepokemon.StorePokemonRepository
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPokemonViewModel @Inject constructor(
	private val repository: DetailPokemonRepository,
	private val catchPokemonRepository: CatchPokemonRepository,
	private val storePokemonRepository: StorePokemonRepository,
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

	private var _getCatchPokemonResult = MutableLiveData<Resource<CatchPokemonResponse>>()
	val getCatchPokemonResult: LiveData<Resource<CatchPokemonResponse>> get() = _getCatchPokemonResult

	fun catchPokemon(catchPokemonRequestBody: CatchPokemonRequestBody) {

		viewModelScope.launch(Dispatchers.IO) {
			_getCatchPokemonResult.postValue(Resource.Loading())

			try {
				val data =
					catchPokemonRepository.catchPokemon(catchPokemonRequestBody)

				if (data.payload != null) {

					viewModelScope.launch(Dispatchers.Main) {
						_getCatchPokemonResult.postValue(Resource.Success(data.payload))
					}

				} else {
					_getCatchPokemonResult.postValue(Resource.Error(data.exception, null))
				}

			} catch (e: Exception) {
				viewModelScope.launch(Dispatchers.Main) {
					_getCatchPokemonResult.postValue(Resource.Error(e, null))
				}
			}

		}
	}

	private var _getStorePokemonResult = MutableLiveData<Resource<StorePokemonResponse>>()
	val getStorePokemonResult: LiveData<Resource<StorePokemonResponse>> get() = _getStorePokemonResult

	fun storePokemon(storePokemonRequestBody: StorePokemonRequestBody) {

		viewModelScope.launch(Dispatchers.IO) {
			_getStorePokemonResult.postValue(Resource.Loading())

			try {
				val data =
					storePokemonRepository.storePokemon(storePokemonRequestBody)

				if (data.payload != null) {

					viewModelScope.launch(Dispatchers.Main) {
						_getStorePokemonResult.postValue(Resource.Success(data.payload))
					}

				} else {
					_getStorePokemonResult.postValue(Resource.Error(data.exception, null))
				}

			} catch (e: Exception) {
				viewModelScope.launch(Dispatchers.Main) {
					_getStorePokemonResult.postValue(Resource.Error(e, null))
				}
			}

		}
	}
	fun getStatusAuth(): LiveData<Boolean?> = authDataStoreManager.getStatusAuth.asLiveData()
	fun getUserId(): LiveData<Int?> = authDataStoreManager.getUserId.asLiveData()

}