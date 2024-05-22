package com.nandaiqbalh.pokemonapp.data.remote.model.mypokemon.response

data class DataX(
    val id: Int,
    val name: String,
    val nickname: String,
    val pokemon_id: Int,
    val rename_flag: Any,
    val user_id: Int
) {
    fun getImageUrl(): String {
        val index = pokemon_id
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$index.png"
    }
}