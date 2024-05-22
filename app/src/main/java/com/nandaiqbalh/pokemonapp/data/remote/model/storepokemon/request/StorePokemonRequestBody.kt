package com.nandaiqbalh.pokemonapp.data.remote.model.storepokemon.request

import com.google.gson.annotations.SerializedName

data class StorePokemonRequestBody(
	@SerializedName("user_id") val userId: Int?,
	@SerializedName("pokemon_id") val pokemonId: Int?,
	@SerializedName("name") val name: String?,
	@SerializedName("nickname") val nickname: String?,

	)