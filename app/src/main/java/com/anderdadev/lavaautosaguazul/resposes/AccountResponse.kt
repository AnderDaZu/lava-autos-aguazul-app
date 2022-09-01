package com.anderdadev.lavaautosaguazul.resposes

import com.google.gson.annotations.SerializedName

data class AccountResponse(
    @SerializedName("success") val success: Int,
    @SerializedName("user") val user: Account
)

data class Account(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("last_name") val last_name: String,
    @SerializedName("email") val email: String,
)