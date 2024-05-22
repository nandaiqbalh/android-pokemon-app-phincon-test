package com.nandaiqbalh.pokemonapp.data.remote.model.auth.login.request

import org.junit.Assert.assertEquals
import org.junit.Test

class AuthLoginRemoteRequestBodyTest {

	@Test
	fun `authLoginRequestBody should have correct properties`() {
		// Arrange
		val username = "test_user"
		val password = "test_password"

		// Act
		val requestBody = AuthLoginRequestBody(username, password)

		// Assert
		assertEquals("username should be $username", username, requestBody.username)
		assertEquals("password should be $password", password, requestBody.password)
	}
}
