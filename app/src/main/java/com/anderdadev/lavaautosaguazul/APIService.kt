package com.anderdadev.lavaautosaguazul

import com.anderdadev.lavaautosaguazul.resposes.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    // btn -> nuestros servicios
    @GET
    suspend fun getPosts(@Url url: String): Response<PostListResponse>

    // btn -> iniciar sesión
    @POST
    suspend fun register(
        @Url url: String,
        @Query("user_name") user_name: String,
        @Query("name") name: String,
        @Query("last_name") last_name: String,
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<RegisterResponse>

    // btn -> Register
    @POST
    suspend fun login(
        @Url url: String,
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<LoginResponse>

    // btn -> cerrar sesión
    @GET
    suspend fun logout(
        @Url url: String,
        @Query("token") token: String
    ): Response<LogoutResponse>

    // validate token
    @GET
    suspend fun validate(
        @Url url: String,
        @Query("token") token: String
    ): Response<ValidateResponse>

    // btn -> cuenta
//    @GET("user")
//    fun showAccount(@Query("token") token: String): Response<UserProfileResponse>

    // btn -> reservar cita
    @GET("v1/appointment-morning/check-vehicles")
    fun getVehicles(@Query("token") token: String):
            Call<CheckVehiclesResponse>

    @GET("v1/appointment-morning/select-services/{vehicle}")
    fun getServices(@Path("vehicle") vehicle_id: Int, @Query("token") token: String):
            Call<CheckServicesResponse>

    @GET("v1/appointment-morning/check-spaces/{vehicle}/{service}")
    fun getSpaces(
        @Path("vehicle") vehicle_id: Int,
        @Path("service") service_id: Int, @Query("token") token: String
    ): Call<CheckSpacesResponse>

    @GET("v1/appointment-morning/check-employees/{vehicle}/{service}/{space}")
    fun getEmployees(
        @Path("vehicle") vehicle_id: Int, @Path("service") service_id: Int,
        @Path("space") space_id: Int, @Query("token") token: String
    ): Call<CheckEmployeeResponse>

    @POST("v1/appointment-morning/create-appointment")
    fun createAppointment(
        @Query("token") token: String,
        @Query("hour_start") hour_start: String,
        @Query("hour_end") hour_end: String,
        @Query("service_id") service_id: Int,
        @Query("vehicle_id") vehicle_id: Int,
        @Query("employee_id") employee_id: Int,
    ): Call<AppointmentCreateUserResponse>

    // btn -> registrar vehículo
    @GET("v1/vehicles-data")
    fun getDataVehicle(
        @Query("token") token: String
    ): Call<DataResponse>

    @POST("v1/vehicles-user")
    fun registerVehicle(
        @Query("token") token: String,
        @Query("plate") plate: String,
        @Query("color_id") color_id: Int,
        @Query("modelcar_id") modelcar_id: Int
    ): Call<VehicleRegisterResponse>

    // btn -> ver vehículos
    @GET
    suspend fun vehicles(
        @Url url: String,
        @Query("token") token: String
    ): Response<VehicleResponse>

    // btn -> ver citas
    @GET
    suspend fun appointments(
        @Url url: String,
        @Query("token") token: String
    ): Response<AppointmentsUserResponse>


    // btn -> registrar servicio/tarea
    @GET("v1/appointments-scheduled")
    fun getAppointments(
        @Query("token") token: String
    ): Call<AppointmentYardResponse>

    @POST("v1/tasks")
    fun registerTask(
        @Query("token") token: String,
        @Query("price") price: String,
        @Query("stocktaking") stocktaking: String,
        @Query("appointment_id") appointment_id: Int
    ): Call<AppointmentCreateYardResponse>

    @GET("v1/tasks")
    fun getAppointmentsStarted(
        @Query("token") token: String
    ): Call<TaskYardResponse>

    @GET
    suspend fun getScheduledTasksYard(
        @Url url: String,
        @Query("token") token: String
    ): Response<ShowTaskYardResponse>

    @GET
    suspend fun getUnscheduledTasksYard(
        @Url url: String,
        @Query("token") token: String
    ): Response<ShowUnscheduledTaskYardResponse>

    @GET
    suspend fun getTasksEmployee(
        @Url url: String,
        @Query("token") token: String
    ): Response<ShowTaskEmployeeResponse>

    @GET
    suspend fun getScheduledTasksEmployee(
        @Url url: String,
        @Query("token") token: String
    ): Response<ShowScheduledTaskEmployeeResponse>

    @GET
    suspend fun getUnscheduledTasksEmployee(
        @Url url: String,
        @Query("token") token: String
    ): Response<ShowUnscheduledTaskEmployeeResponse>

    @PUT("v1/tasks/{task}")
    fun updateTask(
        @Path("task") task: Int,
        @Query("token") token: String
    ): Call<TaskUpdateYardResponse>

    // btn -> registrar servicio completado/no agendado
    @GET("v1/unscheduled-appointments/types")
    fun getTypes(
        @Query("token") token: String
    ): Call<UnscheduledTypeResponse>

    @GET("v1/unscheduled-appointments/services/{type}")
    fun getServicesTask(
        @Path("type") type_id: Int, @Query("token") token: String
    ): Call<UnscheduledServiceResponse>

    @POST("v1/unscheduled-appointments/create")
    fun registerUnscheduledTask(
        @Query("token") token: String,
        @Query("plate") plate: String,
        @Query("price") price: String,
        @Query("stocktaking") stocktaking: String,
        @Query("employee_id") employee_id: Int,
        @Query("servicio_id") servicio_id: Int,
        @Query("type_id") type_id: Int
    ): Call<UnscheduledTaskResponse>

}