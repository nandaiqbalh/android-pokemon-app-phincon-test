package com.nandaiqbalh.pokemonapp.presentation.ui.home.mypokemonlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.pokemonapp.data.local.datastore.auth.AuthDataStoreManager
import com.nandaiqbalh.pokemonapp.data.remote.model.deletepokemon.request.DeletePokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.deletepokemon.response.DeletePokemonResponse
import com.nandaiqbalh.pokemonapp.data.remote.model.mypokemon.request.MyPokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.mypokemon.response.MyPokemonResponse
import com.nandaiqbalh.pokemonapp.data.remote.model.releasepokemon.ReleasePokemonResponse
import com.nandaiqbalh.pokemonapp.data.remote.repository.deletepokemon.DeletePokemonRepository
import com.nandaiqbalh.pokemonapp.data.remote.repository.mypokemon.MyPokemonRepository
import com.nandaiqbalh.pokemonapp.data.remote.repository.releasepokemon.ReleasePokemonRepository
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyPokemonViewModel @Inject constructor(
	private val repository: MyPokemonRepository,
	private val releasePokemonRepository: ReleasePokemonRepository,
	private val deletePokemonRepository: DeletePokemonRepository,
	private val authDataStoreManager: AuthDataStoreManager
) : ViewModel() {

	private var _getMyPokemonResult = MutableLiveData<Resource<MyPokemonResponse>>()
	val getMyPokemonResult: LiveData<Resource<MyPokemonResponse>> get() = _getMyPokemonResult

	fun getMyPokemon(myPokemonRequestBody: MyPokemonRequestBody) {

		viewModelScope.launch(Dispatchers.IO) {
			_getMyPokemonResult.postValue(Resource.Loading())

			try {
				val data =
					repository.getMyPokemons(myPokemonRequestBody)

				if (data.payload != null) {

					viewModelScope.launch(Dispatchers.Main) {
						_getMyPokemonResult.postValue(Resource.Success(data.payload))
					}

				} else {
					_getMyPokemonResult.postValue(Resource.Error(data.exception, null))
				}

			} catch (e: Exception) {
				viewModelScope.launch(Dispatchers.Main) {
					_getMyPokemonResult.postValue(Resource.Error(e, null))
				}
			}

		}
	}

	private var _getReleasePokemonResult = MutableLiveData<Resource<ReleasePokemonResponse>>()
	val getReleasePokemonResult: LiveData<Resource<ReleasePokemonResponse>> get() = _getReleasePokemonResult

	fun releasePokemon() {

		viewModelScope.launch(Dispatchers.IO) {
			_getReleasePokemonResult.postValue(Resource.Loading())

			try {
				val data =
					releasePokemonRepository.releasePokemon()

				if (data.payload != null) {

					viewModelScope.launch(Dispatchers.Main) {
						_getReleasePokemonResult.postValue(Resource.Success(data.payload))
					}

				} else {
					_getReleasePokemonResult.postValue(Resource.Error(data.exception, null))
				}

			} catch (e: Exception) {
				viewModelScope.launch(Dispatchers.Main) {
					_getReleasePokemonResult.postValue(Resource.Error(e, null))
				}
			}

		}
	}

	private var _getDeletePokemonResult = MutableLiveData<Resource<DeletePokemonResponse>>()
	val getDeletePokemonResult: LiveData<Resource<DeletePokemonResponse>> get() = _getDeletePokemonResult

	fun deletePokemon(deletePokemonRequestBody: DeletePokemonRequestBody) {

		viewModelScope.launch(Dispatchers.IO) {
			_getDeletePokemonResult.postValue(Resource.Loading())

			try {
				val data =
					deletePokemonRepository.deletePokemon(deletePokemonRequestBody)

				if (data.payload != null) {

					viewModelScope.launch(Dispatchers.Main) {
						_getDeletePokemonResult.postValue(Resource.Success(data.payload))
					}

				} else {
					_getDeletePokemonResult.postValue(Resource.Error(data.exception, null))
				}

			} catch (e: Exception) {
				viewModelScope.launch(Dispatchers.Main) {
					_getDeletePokemonResult.postValue(Resource.Error(e, null))
				}
			}

		}
	}

	fun getUserId(): LiveData<Int?> = authDataStoreManager.getUserId.asLiveData()

}