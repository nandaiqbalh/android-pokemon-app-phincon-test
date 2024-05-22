package com.nandaiqbalh.pokemonapp.data.remote.model.auth.login.response

import com.google.gson.annotations.SerializedName

data class AuthLoginRemoteResponse(
    @SerializedName("status")
    val status: String?,

    @SerializedName("success")
    val success: Boolean?,

    @SerializedName("data")
    val userData: UserData? = null,

    )

data class UserData(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("username")
    val username: String?,
)
