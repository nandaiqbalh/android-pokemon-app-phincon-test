package com.nandaiqbalh.pokemonapp.data.remote.repository.releasepokemon

import com.nandaiqbalh.pokemonapp.data.remote.datasource.releasepokemon.ReleasePokemonDataSource
import com.nandaiqbalh.pokemonapp.data.remote.model.releasepokemon.ReleasePokemonResponse
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import javax.inject.Inject

interface ReleasePokemonRepository {
	suspend fun releasePokemon(
	): Resource<ReleasePokemonResponse>
}

class ReleasePokemonRepositoryImpl @Inject constructor(private val dataSource: ReleasePokemonDataSource) :
	ReleasePokemonRepository {
	override suspend fun releasePokemon(): Resource<ReleasePokemonResponse> {
		return proceed {
			dataSource.releasePokemon()
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