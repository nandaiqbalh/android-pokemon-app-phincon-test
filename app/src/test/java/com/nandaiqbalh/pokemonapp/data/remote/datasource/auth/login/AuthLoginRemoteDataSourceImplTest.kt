package com.nandaiqbalh.pokemonapp.data.remote.datasource.auth.login

import com.nandaiqbalh.pokemonapp.data.remote.model.auth.login.request.AuthLoginRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.login.response.AuthLoginRemoteResponse
import com.nandaiqbalh.pokemonapp.data.remote.service.BackendApiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class AuthLoginRemoteDataSourceImplTest {

	private lateinit var dataSource: AuthLoginRemoteDataSource
	private lateinit var apiService: BackendApiService

	@Before
	fun setUp() {
		apiService = mock(BackendApiService::class.java)
		dataSource = AuthLoginRemoteDataSourceImpl(apiService)
	}

	@Test
	fun `authLogin should return correct response`() = runBlocking {
		// Arrange
		val requestBody = AuthLoginRequestBody("username", "password")
		val expectedResponse = AuthLoginRemoteResponse("", true)
		`when`(apiService.authLogin(requestBody)).thenReturn(expectedResponse)

		// Act
		val actualResponse = dataSource.authLogin(requestBody)

		// Assert
		assertEquals(
			"Returned response should match expected response",
			expectedResponse,
			actualResponse
		)
	}
}
