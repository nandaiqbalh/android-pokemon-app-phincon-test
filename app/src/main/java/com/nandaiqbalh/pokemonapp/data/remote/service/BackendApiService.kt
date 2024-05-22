package com.nandaiqbalh.pokemonapp.data.remote.service

import com.nandaiqbalh.pokemonapp.data.remote.model.auth.login.request.AuthLoginRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.login.response.AuthLoginRemoteResponse
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.register.request.AuthRegisterRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.register.response.AuthRegisterRemoteResponse
import com.nandaiqbalh.pokemonapp.data.remote.model.catchpokemon.request.CatchPokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.catchpokemon.response.CatchPokemonResponse
import com.nandaiqbalh.pokemonapp.data.remote.model.storepokemon.request.StorePokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.storepokemon.response.StorePokemonResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface BackendApiService {
	@POST("auth/login")
	suspend fun authLogin(
		@Body authLoginRequestBody: AuthLoginRequestBody,
	): AuthLoginRemoteResponse

	@POST("auth/register")
	suspend fun authRegister(
		@Body authRegisterRequestBody: AuthRegisterRequestBody,
	): AuthRegisterRemoteResponse

	@POST("pokemon/catch-pokemon")
	suspend fun catchPokemon(
		@Body catchPokemonRequestBody: CatchPokemonRequestBody
	): CatchPokemonResponse

	@POST("pokemon/store-pokemon")
	suspend fun storePokemon(
		@Body storePokemonRequestBody: StorePokemonRequestBody
	): StorePokemonResponse
}