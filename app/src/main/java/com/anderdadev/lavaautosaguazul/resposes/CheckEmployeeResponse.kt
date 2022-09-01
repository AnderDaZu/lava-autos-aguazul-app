package com.anderdadev.lavaautosaguazul.resposes

import com.google.gson.annotations.SerializedName

data class CheckEmployeeResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("vehicle") val vehicle: CheckVehicle,
    @SerializedName("service") val service: CheckService,
    @SerializedName("space") val space: CheckSpace,
    @SerializedName("employees") val employees: List<CheckEmployee>,
    @SerializedName("response") val response: String
)

data class CheckEmployee(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("last_name") val last_name: String,
    @SerializedName("profile_photo_url") val photo: String
)