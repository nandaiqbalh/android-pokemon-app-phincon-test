package com.nandaiqbalh.pokemonapp.data.remote.service

import com.nandaiqbalh.pokemonapp.data.remote.model.auth.login.request.AuthLoginRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.login.response.AuthLoginRemoteResponse
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.register.request.AuthRegisterRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.register.response.AuthRegisterRemoteResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface BackendApiService {
	@POST("auth/login")
	suspend fun authLogin(
		@Body authLoginRequestBody: AuthLoginRequestBody
	): AuthLoginRemoteResponse

	@POST("auth/register")
	suspend fun authRegister(
		@Body authRegisterRequestBody: AuthRegisterRequestBody
	): AuthRegisterRemoteResponse

}