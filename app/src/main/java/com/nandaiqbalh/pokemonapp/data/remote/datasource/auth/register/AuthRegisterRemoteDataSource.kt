package com.nandaiqbalh.pokemonapp.data.remote.datasource.auth.register

import com.nandaiqbalh.pokemonapp.data.remote.model.auth.register.request.AuthRegisterRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.register.response.AuthRegisterRemoteResponse
import com.nandaiqbalh.pokemonapp.data.remote.service.BackendApiService
import javax.inject.Inject

interface AuthRegisterRemoteDataSource {
	suspend fun authRegister(authRegisterRequestBody: AuthRegisterRequestBody): AuthRegisterRemoteResponse
}

class AuthRegisterRemoteDataSourceImpl @Inject constructor(private val apiService: BackendApiService) :
	AuthRegisterRemoteDataSource {
	override suspend fun authRegister(
		authRegisterRequestBody: AuthRegisterRequestBody
	): AuthRegisterRemoteResponse {
		return apiService.authRegister(authRegisterRequestBody)
	}


}