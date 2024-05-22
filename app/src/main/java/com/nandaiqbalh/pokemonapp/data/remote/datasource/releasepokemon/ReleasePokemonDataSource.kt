package com.nandaiqbalh.pokemonapp.data.remote.datasource.releasepokemon

import com.nandaiqbalh.pokemonapp.data.remote.model.releasepokemon.ReleasePokemonResponse
import com.nandaiqbalh.pokemonapp.data.remote.service.BackendApiService
import javax.inject.Inject

interface ReleasePokemonDataSource {
	suspend fun releasePokemon(): ReleasePokemonResponse
}

class ReleasePokemonDataSourceImpl @Inject constructor(private val apiService: BackendApiService) :
	ReleasePokemonDataSource {

	override suspend fun releasePokemon(): ReleasePokemonResponse {
		return apiService.releasePokemon()
	}
}