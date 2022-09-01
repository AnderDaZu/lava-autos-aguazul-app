package com.anderdadev.lavaautosaguazul.resposes

import com.google.gson.annotations.SerializedName

data class CheckVehiclesResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("cantidad") val cantidad: Int,
    @SerializedName("vehicles") val vehicles: List<CheckVehicle>,
)

data class CheckVehicle(
    @SerializedName("id") val id: Int,
    @SerializedName("plate") val plate: String,
    @SerializedName("color") val color: String,
    @SerializedName("mark") val mark: String,
    @SerializedName("model") val model: String,
    @SerializedName("client") val client: String,
    @SerializedName("client_id") val client_id: Int,
)