package com.anderdadev.lavaautosaguazul.resposes

import com.google.gson.annotations.SerializedName

data class ShowTaskYardResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("tasks") val tasks: List<ScheduledTaskYard>,
    @SerializedName("message") val message: String,
)

data class ShowUnscheduledTaskYardResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("tasks") val tasks: List<UnscheduledTaskYard>,
    @SerializedName("message") val message: String,
)

data class ScheduledTaskYard(
    @SerializedName("finished") val finished: String,
    @SerializedName("service") val service: String,
    @SerializedName("employee") val employee: String,
    @SerializedName("price") val price: String,
    @SerializedName("plate") val plate: String,
    @SerializedName("model") val model: String,
    @SerializedName("mark") val mark: String,
)

data class UnscheduledTaskYard(
    @SerializedName("name") val name: String,
    @SerializedName("last_name") val last_name: String,
    @SerializedName("service") val service: String,
    @SerializedName("price") val price: String,
    @SerializedName("plate") val plate: String,
    @SerializedName("type") val type: String,
    @SerializedName("date") val date: String,
    @SerializedName("hour") val hour: String,
)
