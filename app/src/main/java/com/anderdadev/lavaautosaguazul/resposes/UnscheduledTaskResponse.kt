package com.anderdadev.lavaautosaguazul.resposes

import com.google.gson.annotations.SerializedName

data class UnscheduledTaskResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("tarea") val message: String,
)

data class UnscheduledTypeResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("types") val types: List<Type>,
)

data class UnscheduledServiceResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("type") val type: Type,
    @SerializedName("services") val services: List<Service>,
    @SerializedName("employees") val employees: List<Employee>,
)

data class Type(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name: String,
)

data class Service(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: String,
)

data class Employee(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name: String,
    @SerializedName("last_name") val last_name: String,
)