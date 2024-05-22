package com.nandaiqbalh.pokemonapp.data.remote.model.renamepokemon.request

import com.google.gson.annotations.SerializedName

data class RenamePokemonRequestBody(
	@SerializedName("user_id") val userId: Int?,
	@SerializedName("pokemon_id") val pokemonId: Int?,
	@SerializedName("nickname") val nickname: String?,

	)