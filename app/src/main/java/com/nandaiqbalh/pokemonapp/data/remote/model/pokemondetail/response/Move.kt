package com.nandaiqbalh.pokemonapp.data.remote.model.pokemondetail.response

import com.google.gson.annotations.SerializedName

data class Move(
    @SerializedName("move")
    val move: MoveX
)
