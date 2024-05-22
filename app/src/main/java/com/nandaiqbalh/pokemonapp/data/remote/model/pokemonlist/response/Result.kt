package com.nandaiqbalh.pokemonapp.data.remote.model.pokemonlist.response

import com.google.gson.annotations.SerializedName

data class Result(
	@SerializedName("name")
	val name: String,
	@SerializedName("url")
	val url: String
){
	fun getImageUrl(): String {
		val index = url.split("/".toRegex()).dropLast(1).last()
		return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$index.png"
	}
}