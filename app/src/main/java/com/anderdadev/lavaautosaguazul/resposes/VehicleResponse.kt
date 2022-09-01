package com.anderdadev.lavaautosaguazul.resposes

import com.google.gson.annotations.SerializedName

data class VehicleResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("vehicles") val vehicles: List<CheckVehicle>,
)

data class VehicleRegisterResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)

data class DataResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("colors") val colors: List<ColorVehicle>,
    @SerializedName("models") val models: List<ModelVehicle>,
)

data class ModelVehicle(
    @SerializedName("id") val id: Int,
    @SerializedName("model") val model: String,
)

data class ColorVehicle(
    @SerializedName("id") val id: Int,
    @SerializedName("color") val color: String,
)