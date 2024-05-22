package com.nandaiqbalh.pokemonapp.data.remote.repository.mypokemon

import com.nandaiqbalh.pokemonapp.data.remote.datasource.mypokemon.MyPokemonDataSource
import com.nandaiqbalh.pokemonapp.data.remote.model.mypokemon.request.MyPokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.mypokemon.response.MyPokemonResponse
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import javax.inject.Inject

interface MyPokemonRepository {
	suspend fun getMyPokemons(
		myPokemonRequestBody: MyPokemonRequestBody
	): Resource<MyPokemonResponse>
}

class MyPokemonRepositoryImpl @Inject constructor(private val dataSource: MyPokemonDataSource) :
	MyPokemonRepository {
	override suspend fun getMyPokemons(myPokemonRequestBody: MyPokemonRequestBody): Resource<MyPokemonResponse> {
		return proceed {
			dataSource.getMyPokemons(myPokemonRequestBody)
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