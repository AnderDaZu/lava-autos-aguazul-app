package com.anderdadev.lavaautosaguazul.resposes

import com.google.gson.annotations.SerializedName

data class ShowTaskEmployeeResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("tasks") val tasks: List<TaskEmployee>,
    @SerializedName("message") val message: String,
)

data class TaskEmployee(
    @SerializedName("date") val date: String,
    @SerializedName("hour") val hour: String,
    @SerializedName("service") val service: String,
    @SerializedName("plate") val plate: String,
    @SerializedName("model") val model: String,
    @SerializedName("mark") val mark:String
)

data class ShowScheduledTaskEmployeeResponse(
    @SerializedName("success") val success:Boolean,
    @SerializedName("tasks") val tasks: List<TaskScheduledEmployee>,
    @SerializedName("message") val message: String,
)

data class TaskScheduledEmployee(
    @SerializedName("finished") val finished: String,
    @SerializedName("service") val service: String,
    @SerializedName("model") val model: String,
    @SerializedName("mark") val mark: String,
    @SerializedName("plate") val plate: String,
)

data class ShowUnscheduledTaskEmployeeResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("tasks") val tasks: List<TaskUnscheduledEmployee>,
    @SerializedName("message") val message: String,
)

data class TaskUnscheduledEmployee(
    @SerializedName("date") val date: String,
    @SerializedName("type") val type: String,
    @SerializedName("plate") val plate: String,
    @SerializedName("service") val service: String,
)