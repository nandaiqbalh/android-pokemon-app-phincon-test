package com.nandaiqbalh.pokemonapp.data.remote.model.auth.register.request

import com.google.gson.annotations.SerializedName

data class AuthRegisterRequestBody(
	@SerializedName("username") val username: String?,
	@SerializedName("password") val password: String?
)