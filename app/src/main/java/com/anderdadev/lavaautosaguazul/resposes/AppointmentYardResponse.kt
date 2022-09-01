package com.anderdadev.lavaautosaguazul.resposes

import com.google.gson.annotations.SerializedName

data class AppointmentYardResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("appointments") val appointments: List<AppointmentYard>,
    @SerializedName("message") val message: String
)

data class AppointmentCreateYardResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
)

data class AppointmentYard(
    @SerializedName("id") val id: Int,
    @SerializedName("date") val date: String,
    @SerializedName("hour_start") val hour_start: String,
    @SerializedName("hour_end") val hour_end: String,
    @SerializedName("service") val service: String,
    @SerializedName("price") val price: String,
    @SerializedName("plate") val plate: String,
    @SerializedName("model") val model: String,
    @SerializedName("type") val type: String,
    @SerializedName("employee") val employee: String,
    @SerializedName("client") val client: String,
)