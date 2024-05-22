package com.nandaiqbalh.pokemonapp.data.remote.datasource.mypokemon

import com.nandaiqbalh.pokemonapp.data.remote.model.mypokemon.request.MyPokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.mypokemon.response.MyPokemonResponse
import com.nandaiqbalh.pokemonapp.data.remote.service.BackendApiService
import javax.inject.Inject

interface MyPokemonDataSource {
	suspend fun getMyPokemons(myPokemonRequestBody: MyPokemonRequestBody): MyPokemonResponse
}

class MyPokemonDataSourceImpl @Inject constructor(private val apiService: BackendApiService) :
	MyPokemonDataSource {
	override suspend fun getMyPokemons(myPokemonRequestBody: MyPokemonRequestBody): MyPokemonResponse {
		return apiService.getMyPokemons(myPokemonRequestBody)
	}
}