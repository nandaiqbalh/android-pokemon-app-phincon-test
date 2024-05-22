package com.nandaiqbalh.pokemonapp.data.remote.datasource.renamepokemon

import com.nandaiqbalh.pokemonapp.data.remote.model.renamepokemon.request.RenamePokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.renamepokemon.response.RenamePokemonResponse
import com.nandaiqbalh.pokemonapp.data.remote.service.BackendApiService
import javax.inject.Inject

interface RenamePokemonDataSource {
	suspend fun renamePokemon(renamePokemonRequestBody: RenamePokemonRequestBody): RenamePokemonResponse
}

class RenamePokemonDataSourceImpl @Inject constructor(private val apiService: BackendApiService) :
	RenamePokemonDataSource {

	override suspend fun renamePokemon(renamePokemonRequestBody: RenamePokemonRequestBody): RenamePokemonResponse {
		return apiService.renamePokemon(renamePokemonRequestBody)
	}
}