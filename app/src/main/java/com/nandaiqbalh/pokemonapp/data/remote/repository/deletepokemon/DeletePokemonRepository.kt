package com.nandaiqbalh.pokemonapp.data.remote.repository.deletepokemon

import com.nandaiqbalh.pokemonapp.data.remote.datasource.deletepokemon.DeletePokemonDataSource
import com.nandaiqbalh.pokemonapp.data.remote.model.deletepokemon.request.DeletePokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.deletepokemon.response.DeletePokemonResponse
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import javax.inject.Inject

interface DeletePokemonRepository {
	suspend fun deletePokemon(
		deletePokemonRequestBody: DeletePokemonRequestBody
	): Resource<DeletePokemonResponse>
}

class DeletePokemonRepositoryImpl @Inject constructor(private val dataSource: DeletePokemonDataSource) :
	DeletePokemonRepository {

	override suspend fun deletePokemon(deletePokemonRequestBody: DeletePokemonRequestBody): Resource<DeletePokemonResponse> {
		return proceed {
			dataSource.deletePokemon(deletePokemonRequestBody)
		}
	}

	private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
		return try {
			Resource.Success(coroutines.invoke())
		} catch (e: Exception) {
			Resource.Error(e)
		}
	}


}