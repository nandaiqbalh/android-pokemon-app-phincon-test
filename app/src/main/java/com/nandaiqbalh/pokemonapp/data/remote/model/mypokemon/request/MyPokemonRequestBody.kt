package com.nandaiqbalh.pokemonapp.data.remote.model.mypokemon.request

import com.google.gson.annotations.SerializedName

data class MyPokemonRequestBody(
	@SerializedName("user_id") val userId: Int?,

	)