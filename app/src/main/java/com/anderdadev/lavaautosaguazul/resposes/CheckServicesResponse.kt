package com.anderdadev.lavaautosaguazul.resposes

import com.google.gson.annotations.SerializedName

data class CheckServicesResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("vehicle") val vehicle: CheckVehicle,
    @SerializedName("service") val service: List<CheckService>
)

data class CheckService(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: String
)