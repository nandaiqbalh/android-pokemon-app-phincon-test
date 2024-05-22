package com.nandaiqbalh.pokemonapp.data.remote.model.pokemondetail.response

import com.google.gson.annotations.SerializedName

data class DetailPokemonResponse(
    @SerializedName("base_experience")
    val baseExperience: Int,

    @SerializedName("height")
    val height: Int,

    @SerializedName("id")
    val id: Int,

    @SerializedName("is_default")
    val isDefault: Boolean,

    @SerializedName("location_area_encounters")
    val locationAreaEncounters: String,

    @SerializedName("moves")
    val moves: List<Move>,

    @SerializedName("name")
    val name: String,

    @SerializedName("order")
    val order: Int,


    @SerializedName("sprites")
    val sprites: Sprites,

    @SerializedName("types")
    val types: List<Type>,

    @SerializedName("weight")
    val weight: Int,


    )
