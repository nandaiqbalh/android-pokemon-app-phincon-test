package com.nandaiqbalh.pokemonapp.data.remote.datasource.storepokemon

import com.nandaiqbalh.pokemonapp.data.remote.model.storepokemon.request.StorePokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.storepokemon.response.StorePokemonResponse
import com.nandaiqbalh.pokemonapp.data.remote.service.BackendApiService
import javax.inject.Inject

interface StorePokemonDataSource {
	suspend fun storePokemon(storePokemonRequestBody: StorePokemonRequestBody): StorePokemonResponse
}

class StorePokemonDataSourceImpl @Inject constructor(private val apiService: BackendApiService) :
	StorePokemonDataSource {
	override suspend fun storePokemon(storePokemonRequestBody: StorePokemonRequestBody): StorePokemonResponse {
		return apiService.storePokemon(storePokemonRequestBody)
	}
}