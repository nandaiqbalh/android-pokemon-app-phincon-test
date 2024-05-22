package com.nandaiqbalh.pokemonapp.data.remote.repository.auth.register

import com.nandaiqbalh.pokemonapp.data.remote.datasource.auth.register.AuthRegisterRemoteDataSource
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.register.request.AuthRegisterRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.register.response.AuthRegisterRemoteResponse
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import javax.inject.Inject

interface AuthRegisterRemoteRepository {
	suspend fun authRegister(
		authRegisterRequestBody: AuthRegisterRequestBody
	): Resource<AuthRegisterRemoteResponse>
}

class AuthRegisterRemoteRepositoryImpl @Inject constructor(private val dataSource: AuthRegisterRemoteDataSource) :
	AuthRegisterRemoteRepository {
	override suspend fun authRegister(
		authRegisterRequestBody: AuthRegisterRequestBody
	): Resource<AuthRegisterRemoteResponse> {
		return proceed {
			dataSource.authRegister(authRegisterRequestBody)
		}
	}

	private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
		return try {
			Resource.Success(coroutines.invoke())
		} catch (e: Exception) {
			Resource.Error(e)
		}
	}


}