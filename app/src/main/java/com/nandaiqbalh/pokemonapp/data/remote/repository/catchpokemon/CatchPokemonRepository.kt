package com.nandaiqbalh.pokemonapp.data.remote.repository.catchpokemon

import com.nandaiqbalh.pokemonapp.data.remote.datasource.catchpokemon.CatchPokemonDataSource
import com.nandaiqbalh.pokemonapp.data.remote.model.catchpokemon.request.CatchPokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.catchpokemon.response.CatchPokemonResponse
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import javax.inject.Inject

interface CatchPokemonRepository {
	suspend fun catchPokemon(
		catchPokemonRequestBody: CatchPokemonRequestBody
	): Resource<CatchPokemonResponse>
}

class CatchPokemonRepositoryImpl @Inject constructor(private val dataSource: CatchPokemonDataSource) :
	CatchPokemonRepository {
	override suspend fun catchPokemon(catchPokemonRequestBody: CatchPokemonRequestBody): Resource<CatchPokemonResponse> {
		return proceed {
			dataSource.catchPokemon(catchPokemonRequestBody)
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