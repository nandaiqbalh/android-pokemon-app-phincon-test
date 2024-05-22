package com.nandaiqbalh.pokemonapp.data.remote.model.deletepokemon.request

import com.google.gson.annotations.SerializedName

data class DeletePokemonRequestBody(
	@SerializedName("user_id") val userId: Int?,
	@SerializedName("pokemon_id") val pokemonId: Int?,

	)