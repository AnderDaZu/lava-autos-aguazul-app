package com.anderdadev.lavaautosaguazul.resposes

import com.google.gson.annotations.SerializedName

data class TaskYardResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("tasks") val tasks: List<Task>
)

data class Task(
    @SerializedName("id") val id:Int,
    @SerializedName("price") val price:String,
    @SerializedName("stocktaking") val stocktaking:String,
    @SerializedName("date") val date:String,
    @SerializedName("hour") val hour:String,
    @SerializedName("finished") val finished: String,
    @SerializedName("appointment_id") val appointment_id: Int,
    @SerializedName("yard_id") val yard_id: Int,
    @SerializedName("yard_name") val yard_name: String,
    @SerializedName("service") val service: String,
    @SerializedName("plate") val plate: String,
    @SerializedName("modelcar") val modelcar: String,
)

data class TaskUpdateYardResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("finalizó") val finished: String,
    @SerializedName("message") val message: String
)