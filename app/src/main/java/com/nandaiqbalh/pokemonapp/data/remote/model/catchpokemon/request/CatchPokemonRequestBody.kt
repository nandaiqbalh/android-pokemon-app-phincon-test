package com.nandaiqbalh.pokemonapp.data.remote.model.catchpokemon.request

import com.google.gson.annotations.SerializedName

data class CatchPokemonRequestBody(
	@SerializedName("user_id") val userId: Int?,
	@SerializedName("pokemon_id") val pokemonId: Int?,
)