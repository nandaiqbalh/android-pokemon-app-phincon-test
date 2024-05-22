package com.nandaiqbalh.pokemonapp.data.remote.datasource.auth.login

import com.nandaiqbalh.pokemonapp.data.remote.model.auth.login.request.AuthLoginRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.login.response.AuthLoginRemoteResponse
import com.nandaiqbalh.pokemonapp.data.remote.service.BackendApiService
import javax.inject.Inject

interface AuthLoginRemoteDataSource {
	suspend fun authLogin(authLoginRequestBody: AuthLoginRequestBody): AuthLoginRemoteResponse
}

class AuthLoginRemoteDataSourceImpl @Inject constructor(private val apiService: BackendApiService) :
	AuthLoginRemoteDataSource {
	override suspend fun authLogin(
		authLoginRequestBody: AuthLoginRequestBody
	): AuthLoginRemoteResponse {
		return apiService.authLogin(authLoginRequestBody)
	}


}