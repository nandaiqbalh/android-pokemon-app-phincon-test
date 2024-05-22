package com.nandaiqbalh.pokemonapp.data.remote.repository.auth.login

import com.nandaiqbalh.pokemonapp.data.remote.datasource.auth.login.AuthLoginRemoteDataSource
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.login.request.AuthLoginRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.login.response.AuthLoginRemoteResponse
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class AuthLoginRemoteRepositoryImplTest {

	private lateinit var repository: AuthLoginRemoteRepository
	private lateinit var dataSource: AuthLoginRemoteDataSource

	@Before
	fun setUp() {
		dataSource = mock(AuthLoginRemoteDataSource::class.java)
		repository = AuthLoginRemoteRepositoryImpl(dataSource)
	}

	@Test
	fun `authLogin should return success resource`() = runBlocking {
		// Arrange
		val requestBody = AuthLoginRequestBody("username", "password")
		val expectedResponse = AuthLoginRemoteResponse("", true)
		`when`(dataSource.authLogin(requestBody)).thenReturn(expectedResponse)

		// Act
		val result = repository.authLogin(requestBody)

		// Assert
		assertEquals("Returned resource should be success", Resource.Success(expectedResponse), result)
	}

	@Test
	fun `authLogin should return error resource`() = runBlocking {
		// Arrange
		val requestBody = AuthLoginRequestBody("username", "password")
		val exception = Exception("Login failed")
		`when`(dataSource.authLogin(requestBody)).thenThrow(exception)

		// Act
		val result = repository.authLogin(requestBody)

		// Assert
		assertEquals("Returned resource should be error", Resource.Error<AuthLoginRemoteResponse>(exception), result)
	}
}
