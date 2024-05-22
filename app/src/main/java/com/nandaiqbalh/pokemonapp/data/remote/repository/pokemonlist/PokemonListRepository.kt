package com.nandaiqbalh.pokemonapp.data.remote.repository.pokemonlist

import com.nandaiqbalh.pokemonapp.data.remote.datasource.pokemonlist.PokemonListDataSource
import com.nandaiqbalh.pokemonapp.data.remote.model.pokemonlist.response.PokemonListResponse
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import javax.inject.Inject

interface PokemonListRepository {

	suspend fun getPokemonList(): Resource<PokemonListResponse>

}

class PokemonListRepositoryImpl @Inject constructor(
	private val dataSource: PokemonListDataSource,
) : PokemonListRepository {

	override suspend fun getPokemonList(): Resource<PokemonListResponse> {
		return proceed {
			dataSource.getPokemonList()
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