package com.anderdadev.lavaautosaguazul.resposes

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)