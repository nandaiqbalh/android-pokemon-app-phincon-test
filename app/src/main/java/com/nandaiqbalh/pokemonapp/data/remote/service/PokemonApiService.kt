package com.nandaiqbalh.pokemonapp.data.remote.service

import com.nandaiqbalh.pokemonapp.data.remote.model.pokemonlist.response.PokemonListResponse
import retrofit2.http.GET


interface PokemonApiService {

	@GET("pokemon")
	suspend fun getPokemonList() : PokemonListResponse

}