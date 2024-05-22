package com.nandaiqbalh.pokemonapp.data.remote.service

import com.nandaiqbalh.pokemonapp.data.remote.model.pokemondetail.response.DetailPokemonResponse
import com.nandaiqbalh.pokemonapp.data.remote.model.pokemonlist.response.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface PokemonApiService {

	@GET("pokemon")
	suspend fun getPokemonList() : PokemonListResponse

	@GET("pokemon/{name}")
	suspend fun getDetailPokemon(
		@Path("name") name : String
	) : DetailPokemonResponse

}