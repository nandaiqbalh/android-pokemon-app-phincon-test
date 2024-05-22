package com.nandaiqbalh.pokemonapp.data.remote.repository.renamepokemon

import com.nandaiqbalh.pokemonapp.data.remote.datasource.renamepokemon.RenamePokemonDataSource
import com.nandaiqbalh.pokemonapp.data.remote.model.renamepokemon.request.RenamePokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.renamepokemon.response.RenamePokemonResponse
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import javax.inject.Inject

interface RenamePokemonRepository {
	suspend fun renamePokemon(
		renamePokemonRequestBody: RenamePokemonRequestBody
	): Resource<RenamePokemonResponse>
}

class RenamePokemonRepositoryImpl @Inject constructor(private val dataSource: RenamePokemonDataSource) :
	RenamePokemonRepository {
	override suspend fun renamePokemon(renamePokemonRequestBody: RenamePokemonRequestBody): Resource<RenamePokemonResponse> {
		return proceed {
			dataSource.renamePokemon(renamePokemonRequestBody)
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