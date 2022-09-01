package com.anderdadev.lavaautosaguazul.resposes

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("user") val user: UserResponse,
    @SerializedName("token") val token: String,
    @SerializedName("message") val message: String,
)

data class UserResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("user_name") val user_name: String,
    @SerializedName("name") val name: String,
    @SerializedName("last_name") val last_name: String,
    @SerializedName("email") val email: String,
    @SerializedName("state_id") val state_id: String,
    @SerializedName("role") val role: String,
)

