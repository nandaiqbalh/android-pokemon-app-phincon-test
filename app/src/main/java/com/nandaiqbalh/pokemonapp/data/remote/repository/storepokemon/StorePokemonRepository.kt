package com.nandaiqbalh.pokemonapp.data.remote.repository.storepokemon

import com.nandaiqbalh.pokemonapp.data.remote.datasource.storepokemon.StorePokemonDataSource
import com.nandaiqbalh.pokemonapp.data.remote.model.storepokemon.request.StorePokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.storepokemon.response.StorePokemonResponse
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import javax.inject.Inject

interface StorePokemonRepository {
	suspend fun storePokemon(
		storePokemonRequestBody: StorePokemonRequestBody
	): Resource<StorePokemonResponse>
}

class StorePokemonRepositoryImpl @Inject constructor(private val dataSource: StorePokemonDataSource) :
	StorePokemonRepository {
	override suspend fun storePokemon(storePokemonRequestBody: StorePokemonRequestBody): Resource<StorePokemonResponse> {
		return proceed {
			dataSource.storePokemon(storePokemonRequestBody)
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