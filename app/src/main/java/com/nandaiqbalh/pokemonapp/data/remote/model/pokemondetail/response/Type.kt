package com.nandaiqbalh.pokemonapp.data.remote.model.pokemondetail.response

import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("slot")
    val slot: Int,

    @SerializedName("type")
    val type: TypeX
)
