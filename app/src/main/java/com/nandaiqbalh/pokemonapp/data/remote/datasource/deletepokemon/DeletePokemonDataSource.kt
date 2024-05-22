package com.nandaiqbalh.pokemonapp.data.remote.datasource.deletepokemon

import com.nandaiqbalh.pokemonapp.data.remote.model.deletepokemon.request.DeletePokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.deletepokemon.response.DeletePokemonResponse
import com.nandaiqbalh.pokemonapp.data.remote.service.BackendApiService
import javax.inject.Inject

interface DeletePokemonDataSource {
	suspend fun deletePokemon(deletePokemonRequestBody: DeletePokemonRequestBody): DeletePokemonResponse
}

class DeletePokemonDataSourceImpl @Inject constructor(private val apiService: BackendApiService) :
	DeletePokemonDataSource {
	override suspend fun deletePokemon(deletePokemonRequestBody: DeletePokemonRequestBody): DeletePokemonResponse {
		return apiService.deletePokemon(deletePokemonRequestBody)
	}
}