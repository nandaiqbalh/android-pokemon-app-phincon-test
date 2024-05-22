package com.nandaiqbalh.pokemonapp.data.remote.service

import com.nandaiqbalh.pokemonapp.data.remote.model.auth.login.request.AuthLoginRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.login.response.AuthLoginRemoteResponse
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.register.request.AuthRegisterRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.register.response.AuthRegisterRemoteResponse
import com.nandaiqbalh.pokemonapp.data.remote.model.catchpokemon.request.CatchPokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.catchpokemon.response.CatchPokemonResponse
import com.nandaiqbalh.pokemonapp.data.remote.model.deletepokemon.request.DeletePokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.deletepokemon.response.DeletePokemonResponse
import com.nandaiqbalh.pokemonapp.data.remote.model.mypokemon.request.MyPokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.mypokemon.response.MyPokemonResponse
import com.nandaiqbalh.pokemonapp.data.remote.model.releasepokemon.ReleasePokemonResponse
import com.nandaiqbalh.pokemonapp.data.remote.model.renamepokemon.request.RenamePokemonRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.renamepokemon.response.RenamePokemonResponse
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

	@POST("pokemon/my-pokemons")
	suspend fun getMyPokemons(
		@Body myPokemonRequestBody: MyPokemonRequestBody
	): MyPokemonResponse

	@POST("pokemon/release-pokemon")
	suspend fun releasePokemon(): ReleasePokemonResponse

	@POST("pokemon/delete-pokemon")
	suspend fun deletePokemon(
		@Body deletePokemonRequestBody: DeletePokemonRequestBody
	): DeletePokemonResponse

	@POST("pokemon/rename-pokemon")
	suspend fun renamePokemon(
		@Body renamePokemonRequestBody: RenamePokemonRequestBody
	): RenamePokemonResponse
}