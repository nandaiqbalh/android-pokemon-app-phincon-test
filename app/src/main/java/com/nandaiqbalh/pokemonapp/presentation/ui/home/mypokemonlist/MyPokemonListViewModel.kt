package com.nandaiqbalh.pokemonapp.presentation.ui.home.myMyPokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.pokemonapp.data.local.datastore.auth.AuthDataStoreManager
import com.nandaiqbalh.pokemonapp.data.remote.model.mypokemon.request.MyPokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.mypokemon.response.MyPokemonResponse
import com.nandaiqbalh.pokemonapp.data.remote.repository.mypokemon.MyPokemonRepository
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyPokemonViewModel @Inject constructor(
	private val repository: MyPokemonRepository,
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

	fun getUserId(): LiveData<Int?> = authDataStoreManager.getUserId.asLiveData()

}