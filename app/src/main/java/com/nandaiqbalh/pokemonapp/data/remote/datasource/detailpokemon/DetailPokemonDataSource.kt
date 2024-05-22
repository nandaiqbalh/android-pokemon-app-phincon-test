package com.nandaiqbalh.pokemonapp.data.remote.datasource.detailpokemon

import com.nandaiqbalh.pokemonapp.data.remote.model.pokemondetail.response.DetailPokemonResponse
import com.nandaiqbalh.pokemonapp.data.remote.service.PokemonApiService
import retrofit2.http.Path
import javax.inject.Inject

interface DetailPokemonDataSource {

	suspend fun getDetailPokemon(
		@Path("name") name : String
	): DetailPokemonResponse

}

class DetailPokemonDataSourceImpl @Inject constructor(
	private val apiService: PokemonApiService,
) : DetailPokemonDataSource {

	override suspend fun getDetailPokemon(
		@Path("name") name : String
	): DetailPokemonResponse {
		return apiService.getDetailPokemon(name)
	}
}
