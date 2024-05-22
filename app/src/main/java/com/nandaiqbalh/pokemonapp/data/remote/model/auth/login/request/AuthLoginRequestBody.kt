package com.nandaiqbalh.pokemonapp.data.remote.model.auth.login.request

import com.google.gson.annotations.SerializedName

data class AuthLoginRequestBody(
	@SerializedName("username") val username: String?,
	@SerializedName("password") val password: String?
)