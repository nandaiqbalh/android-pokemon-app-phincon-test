package com.nandaiqbalh.pokemonapp.data.remote.model.auth.login.response

import org.junit.Assert.assertEquals
import org.junit.Test

class AuthLoginRemoteResponseTest {

	@Test
	fun `authLoginRemoteResponse should have correct properties`() {
		// Arrange
		val status = "success"
		val success = true
		val id = 123
		val username = "test_user"

		// Act
		val userData = UserData(id, username)
		val response = AuthLoginRemoteResponse(status, success, userData)

		// Assert
		assertEquals("status should be $status", status, response.status)
		assertEquals("success should be $success", success, response.success)
		assertEquals("id should be $id", id, response.userData?.id)
		assertEquals("username should be $username", username, response.userData?.username)
	}
}
