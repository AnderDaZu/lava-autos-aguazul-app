package com.anderdadev.lavaautosaguazul.resposes

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("token") val token: String,
    @SerializedName("role") val role: String
)