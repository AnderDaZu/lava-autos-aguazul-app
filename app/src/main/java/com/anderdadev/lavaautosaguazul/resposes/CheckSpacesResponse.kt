package com.anderdadev.lavaautosaguazul.resposes

import com.google.gson.annotations.SerializedName

class CheckSpacesResponse(
    @SerializedName("vehicle") val vehicle: CheckVehicle,
    @SerializedName("service") val service: CheckSpace,
    @SerializedName("amount") val amount: Int,
    @SerializedName("spaces") val spaces: List<CheckSpace>,
    @SerializedName("message") val message: String
)

data class  CheckSpace(
    @SerializedName("id") val id: Int,
    @SerializedName("start_hour") val start_hour: String,
    @SerializedName("end_hour") val end_hour: String,
    @SerializedName("group") val group: Int,
    @SerializedName("times_taken") val times_taken: Int,
    @SerializedName("horario_id") val horario_id: Int,
    @SerializedName("duration_id") val duration_id: Int,
)