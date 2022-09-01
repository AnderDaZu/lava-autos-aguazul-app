package com.anderdadev.lavaautosaguazul.resposes

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(
    @SerializedName("succes") val success: Boolean,
    @SerializedName("user") val user: User
)

data class  User(
    @SerializedName("name") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("last_name") val last_name: String,
    @SerializedName("email") val email: String,
    @SerializedName("role") val role: String,
)