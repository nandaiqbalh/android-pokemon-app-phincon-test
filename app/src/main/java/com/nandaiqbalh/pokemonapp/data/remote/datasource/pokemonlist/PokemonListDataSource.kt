package com.nandaiqbalh.pokemonapp.data.remote.datasource.pokemonlist

import com.nandaiqbalh.pokemonapp.data.remote.model.pokemonlist.response.PokemonListResponse
import com.nandaiqbalh.pokemonapp.data.remote.service.PokemonApiService
import javax.inject.Inject

interface PokemonListDataSource {

	suspend fun getPokemonList(
	): PokemonListResponse

}

class PokemonListDataSourceImpl @Inject constructor(
	private val apiService: PokemonApiService,
) : PokemonListDataSource {

	override suspend fun getPokemonList(): PokemonListResponse {
		return apiService.getPokemonList()
	}
}
