package com.anderdadev.lavaautosaguazul.resposes

import com.google.gson.annotations.SerializedName

class ValidateResponse(
    @SerializedName("success") val statusToken: Boolean
)