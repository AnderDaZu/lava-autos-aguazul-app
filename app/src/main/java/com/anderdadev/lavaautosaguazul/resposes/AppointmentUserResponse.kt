package com.anderdadev.lavaautosaguazul.resposes

import com.google.gson.annotations.SerializedName

data class AppointmentsUserResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("citas") val appointments: List<AppointmentUser>
)

data class AppointmentCreateUserResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("response") val response: String,
//    @SerializedName("appointment") val appointment: AppointmentUser
)

data class AppointmentUser(
    @SerializedName("id") val id: Int,
    @SerializedName("date") val date: String,
    @SerializedName("hour_start") val start_hour: String,
    @SerializedName("end_hour") val end_hour: String,
    @SerializedName("name") val name: String,
    @SerializedName("last_name") val last_name: String,
    @SerializedName("service") val service: String,
    @SerializedName("price") val price: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("state") val state: String,
    @SerializedName("plate") val plate: String,
    @SerializedName("mark") val mark: String,
    @SerializedName("model") val model: String,
    @SerializedName("stocktaking") val stock: String?
)