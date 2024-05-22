package com.nandaiqbalh.pokemonapp.data.remote.datasource.catchpokemon

import com.nandaiqbalh.pokemonapp.data.remote.model.catchpokemon.request.CatchPokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.catchpokemon.response.CatchPokemonResponse
import com.nandaiqbalh.pokemonapp.data.remote.service.BackendApiService
import javax.inject.Inject

interface CatchPokemonDataSource {
	suspend fun catchPokemon(catchPokemonRequestBody: CatchPokemonRequestBody): CatchPokemonResponse
}

class CatchPokemonDataSourceImpl @Inject constructor(private val apiService: BackendApiService) :
	CatchPokemonDataSource {
	override suspend fun catchPokemon(catchPokemonRequestBody: CatchPokemonRequestBody): CatchPokemonResponse {
		return apiService.catchPokemon(catchPokemonRequestBody)
	}
}