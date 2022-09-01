package com.anderdadev.lavaautosaguazul.user

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.anderdadev.lavaautosaguazul.APIService
import com.anderdadev.lavaautosaguazul.PreferenceHelper
import com.anderdadev.lavaautosaguazul.PreferenceHelper.get
import com.anderdadev.lavaautosaguazul.R
import com.anderdadev.lavaautosaguazul.databinding.ActivityCreateAppointmentUserBinding
import com.anderdadev.lavaautosaguazul.resposes.*
import com.anderdadev.lavaautosaguazul.util.toast
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CreateAppointmentUserActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityCreateAppointmentUserBinding
    private var vehicles = mutableListOf<CheckVehicle>()
    private lateinit var vehicle: CheckVehicle
    private var services = mutableListOf<CheckService>()
    private lateinit var serviceG: CheckService
    private var spaces = mutableListOf<CheckSpace>()
    private lateinit var space: CheckSpace
    private var employees = mutableListOf<CheckEmployee>()
    private lateinit var employee: CheckEmployee
    private var selectedRadioButtonVehicle: RadioButton? = null
    private var selectedRadioButtonService: RadioButton? = null
    private var selectedRadioButtonSpace: RadioButton? = null
    private var selectedRadioButtonEmployee: RadioButton? = null
    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        mBinding = ActivityCreateAppointmentUserBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        loadVehicles()

        mBinding.btnVehicleContinue.isClickable = false
        mBinding.btnServiceContinue.isClickable = false
        mBinding.btnSpaceContinue.isClickable = false
        mBinding.btnEmployeeContinue.isClickable = false

        if (!mBinding.btnVehicleContinue.isClickable) {
            mBinding.btnVehicleContinue.setOnClickListener {
                toast("Selecciona un vehículo primero, o registra uno")
            }
        }
        if (!mBinding.btnServiceContinue.isClickable) {
            mBinding.btnServiceContinue.setOnClickListener {
                toast("Selecciona un servicio, si no hay hasta mañana podrás volver a agendar")
            }
        }
        if (!mBinding.btnSpaceContinue.isClickable) {
            mBinding.btnSpaceContinue.setOnClickListener {
                toast("Selecciona un espacio, si no hay hasta mañana podrás volver a agendar")
            }
        }
        if (!mBinding.btnEmployeeContinue.isClickable) {
            mBinding.btnEmployeeContinue.setOnClickListener {
                toast("Selecciona un empleado, si no hay hasta mañana podrás volver a agendar")
            }
        }


        mBinding.stepServices.visibility = View.GONE
        mBinding.stepSpaces.visibility = View.GONE
        mBinding.stepEmployees.visibility = View.GONE
        mBinding.stepFinal.visibility = View.GONE

    }

    private fun loadVehicles() {
        val token = preferences["jwt", ""]
        val service = getRetrofit().create(APIService::class.java)
        val call = service.getVehicles(token)
        call.enqueue(object : Callback<CheckVehiclesResponse> {
            override fun onResponse(
                call: Call<CheckVehiclesResponse>,
                response: Response<CheckVehiclesResponse>
            ) {
                if (response.isSuccessful) {
                    val vehiclesRes = response.body()
                    if (vehiclesRes == null) {
                        Log.i("Respuesta de vehicles", "Null")
                        toast("Respuesta null desde vehicles")
                        return
                    }
                    if (vehiclesRes.success) {

                        if (vehiclesRes.cantidad > 0) {
                            val totalVehicles = vehiclesRes.vehicles
                            vehicles.clear()
                            totalVehicles.forEach { interval ->
                                Log.i("Vehícle: ", "${interval.id} ${interval.model}")
                                vehicles.add(interval)
                            }
                            createButton(vehicles)
                        } else {
                            toast("No tiene vehículos registrados")
                        }
                    } else {
                        Snackbar.make(
                            mBinding.stepVehicles,
                            "No se pudo procesar la solicitud",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                } else {
                    toast("Hubo fallo en la conexión")
                }
            }

            override fun onFailure(call: Call<CheckVehiclesResponse>, t: Throwable) {
                toast("Fallo la conexión")
            }
        })
    }

    private fun createButton(vehicles: List<CheckVehicle>) {

        selectedRadioButtonVehicle = null
        mBinding.radioButtonParentVehicles.removeAllViews()
        vehicles.forEach { it ->
            val radioButton = RadioButton(this)
            radioButton.id = it.id
            (it.plate + " - " + it.mark + " " + it.model).also { radioButton.text = it }
            radioButton.setPadding(8, 12, 8, 12)
            radioButton.ellipsize = TextUtils.TruncateAt.START

            radioButton.setOnClickListener { view ->
                selectedRadioButtonVehicle?.isChecked = false
                selectedRadioButtonVehicle = view as RadioButton
                selectedRadioButtonVehicle?.isChecked = true
                vehicle = it
                if (selectedRadioButtonVehicle?.isChecked == true) {
                    mBinding.btnVehicleContinue.isClickable
                    mBinding.btnVehicleContinue.setOnClickListener {
                        mBinding.stepVehicles.visibility = View.GONE
                        mBinding.stepServices.visibility = View.VISIBLE
//                        toast("Vehículo ${vehicle.id} - ${vehicle.mark} ${vehicle.model}")
                        loadServices()
                    }
                } else if (selectedRadioButtonVehicle?.isChecked == false) {
                    mBinding.btnVehicleContinue.isClickable = false
                }
            }

            mBinding.radioButtonParentVehicles.addView(radioButton)
        }
    }

    private fun loadServices() {
        val token = preferences["jwt", ""]
        val serviceRequest = getRetrofit().create(APIService::class.java)
        val call = serviceRequest.getServices(vehicle.id, token)
        call.enqueue(object : Callback<CheckServicesResponse> {
            override fun onResponse(
                call: Call<CheckServicesResponse>,
                response: Response<CheckServicesResponse>
            ) {
                if (response.isSuccessful) {
                    val servicesRes = response.body()
                    if (servicesRes == null) {
                        Log.i("Respuesta de services", "Null")
                        toast("Respuesta null desde services")
                        return
                    }
                    if (servicesRes.success) {

                        if (servicesRes.service.isNotEmpty()) {
                            val totalServices = servicesRes.service
                            services.clear()
                            totalServices.forEach { interval ->
                                Log.i("Service: ", "${interval.id} ${interval.name}")
                                services.add(interval)
                            }
                            createButtonServices(services)
                        } else {
                            toast("No tiene vehículos registrados")
                        }
                    } else {
                        Snackbar.make(
                            mBinding.stepServices,
                            servicesRes.message,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                } else {
                    toast("Hubo fallo en la conexión")
                }
            }

            override fun onFailure(call: Call<CheckServicesResponse>, t: Throwable) {
                toast("Fallo la conexión -> $t")
            }
        })
    }

    private fun createButtonServices(services: List<CheckService>) {

        selectedRadioButtonService = null
        mBinding.radioButtonParentServices.removeAllViews()
        mBinding.btnServiceContinue.isClickable = false
        services.forEach { it ->
            val radioButton = RadioButton(this)
            radioButton.id = it.id
            (it.name + " - " + it.price).also { radioButton.text = it }
            radioButton.setPadding(8, 12, 8, 12)
            radioButton.ellipsize = TextUtils.TruncateAt.START

            radioButton.setOnClickListener { view ->
                selectedRadioButtonService?.isChecked = false
                selectedRadioButtonService = view as RadioButton
                selectedRadioButtonService?.isChecked = true
                serviceG = it

//                if (selectedRadioButtonService?.isChecked == false) {
//                    mBinding.btnServiceContinue.isClickable = false
//                }

                if (selectedRadioButtonService?.isChecked == true) {
                    mBinding.btnServiceContinue.isClickable = true
                    mBinding.btnServiceContinue.setOnClickListener {
                        if (services.isNotEmpty()) {
//                            toast("Service ${serviceG.id}: ${serviceG.name} ${serviceG.price}")
                            mBinding.stepServices.visibility = View.GONE
                            mBinding.stepSpaces.visibility = View.VISIBLE
                            loadSpaces()
                        }
                    }
                }
            }

            mBinding.radioButtonParentServices.addView(radioButton)
        }
    }

    private fun loadSpaces() {

        val token = preferences["jwt", ""]
        val service = getRetrofit().create(APIService::class.java)
        val call = service.getSpaces(vehicle.id, serviceG.id, token)
        call.enqueue(object : Callback<CheckSpacesResponse> {
            override fun onResponse(
                call: Call<CheckSpacesResponse>,
                response: Response<CheckSpacesResponse>
            ) {
                if (response.isSuccessful) {
                    val spacesRes = response.body()
                    if (spacesRes == null) {
                        Log.i("Respuesta de services", "Null")
                        toast("Respuesta null desde services")
                        return
                    }
                    if (spacesRes.spaces.isNotEmpty()) {
                        val totalSpaces = spacesRes.spaces
                        spaces.clear()
                        totalSpaces.forEach { interval ->
                            Log.i(
                                "Service: ",
                                "${interval.id}, ${interval.start_hour} - ${interval.end_hour}"
                            )
                            spaces.add(interval)
                        }
                        createButtonSpaces(spaces)
                    } else {
                        toast("No hay espacios disponibles para este servicio")
                        mBinding.stepSpaces.visibility = View.GONE
                        mBinding.stepServices.visibility = View.VISIBLE
                        return
                    }

                } else {
                    toast("Hubo fallo en la conexión")
                }
            }

            override fun onFailure(call: Call<CheckSpacesResponse>, t: Throwable) {
                toast("Fallo la conexión -> $t")
            }
        })
    }

    private fun createButtonSpaces(spaces: List<CheckSpace>) {

        selectedRadioButtonSpace = null
        mBinding.radioButtonParentSpaces.removeAllViews()
        mBinding.btnSpaceContinue.isClickable = false
        spaces.forEach { it ->
            val radioButton = RadioButton(this)
            radioButton.id = it.id
            (it.start_hour + " - " + it.end_hour).also { radioButton.text = it }
            radioButton.setPadding(8, 12, 8, 12)
            radioButton.ellipsize = TextUtils.TruncateAt.START

            radioButton.setOnClickListener { view ->
                selectedRadioButtonSpace?.isChecked = false
                selectedRadioButtonSpace = view as RadioButton
                selectedRadioButtonSpace?.isChecked = true
                space = it
                if (selectedRadioButtonSpace?.isChecked == true) {
                    mBinding.btnSpaceContinue.isClickable
                    mBinding.btnSpaceContinue.setOnClickListener {
                        if (spaces.isNotEmpty()) {
//                            toast("Space ${space.id}: ${space.start_hour} - ${space.end_hour}")
                            mBinding.stepSpaces.visibility = View.GONE
                            mBinding.stepEmployees.visibility = View.VISIBLE
                            loadEmployees()
                        }
                    }

                } else if (selectedRadioButtonSpace?.isChecked == false) {
                    mBinding.btnSpaceContinue.isClickable = false
                }
            }

            mBinding.radioButtonParentSpaces.addView(radioButton)
        }
    }

    private fun loadEmployees() {

        val token = preferences["jwt", ""]
        val service = getRetrofit().create(APIService::class.java)
        val call = service.getEmployees(vehicle.id, serviceG.id, space.id, token)
        call.enqueue(object : Callback<CheckEmployeeResponse> {
            override fun onResponse(
                call: Call<CheckEmployeeResponse>,
                response: Response<CheckEmployeeResponse>
            ) {
                if (response.isSuccessful) {
                    val employeesRes = response.body()
                    if (employeesRes == null) {
                        Log.i("Respuesta de empleados", "Null")
                        toast("Respuesta null desde empleados")
                        return
                    }

                    if (!employeesRes.success){
                        Snackbar.make(
                            mBinding.stepServices,
                            employeesRes.response,
                            Snackbar.LENGTH_LONG
                        ).show()
                        mBinding.stepEmployees.visibility = View.GONE
                        mBinding.stepSpaces.visibility = View.VISIBLE
                        return
                    }

                    if (employeesRes.employees.isNotEmpty()) {
                        val totalEmployees = employeesRes.employees
                        employees.clear()
                        totalEmployees.forEach { interval ->
                            Log.i(
                                "Service: ",
                                "${interval.id}, ${interval.name} - ${interval.last_name}"
                            )
                            employees.add(interval)
                        }
                        createButtonEmployees(employees)
                    } else {
                        Snackbar.make(
                            mBinding.stepServices,
                            employeesRes.response,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }

                } else {
                    toast("Hubo fallo en la conexión")
                }
            }

            override fun onFailure(call: Call<CheckEmployeeResponse>, t: Throwable) {
                toast("Fallo la conexión -> $t")
            }
        })
    }

    private fun createButtonEmployees(employees: List<CheckEmployee>) {

        selectedRadioButtonEmployee = null
        mBinding.radioButtonParentEmployees.removeAllViews()
        mBinding.btnEmployeeContinue.isClickable = false
        employees.forEach { it ->
            val radioButton = RadioButton(this)
            radioButton.id = it.id
            (it.name + " " + it.last_name).also { radioButton.text = it }
            radioButton.setPadding(8, 12, 8, 12)
            radioButton.ellipsize = TextUtils.TruncateAt.START

            radioButton.setOnClickListener { view ->

                selectedRadioButtonEmployee?.isChecked = false
                selectedRadioButtonEmployee = view as RadioButton
                selectedRadioButtonEmployee?.isChecked = true
                employee = it

                if (selectedRadioButtonEmployee?.isChecked == true) {
                    mBinding.btnEmployeeContinue.isClickable
                    mBinding.btnEmployeeContinue.setOnClickListener {
                        if (employees.isNotEmpty()) {
//                            toast("Empleado ${employee.id}: ${employee.name} ${employee.last_name}")
                            mBinding.stepEmployees.visibility = View.GONE
                            mBinding.stepFinal.visibility = View.VISIBLE
                            loadInformation()
                        }
                    }
                } else if (selectedRadioButtonEmployee?.isChecked == false) {
                    mBinding.btnEmployeeContinue.isClickable = false
                }
            }

            mBinding.radioButtonParentEmployees.addView(radioButton)
        }
    }

    private fun loadInformation() {
        (vehicle.plate + " - " + vehicle.mark + " " + vehicle.model).also {
            mBinding.tvVehicleSelected.text = it
        }
        serviceG.name.also { mBinding.tvServiceSelected.text = it }
        (space.start_hour + " - " + space.end_hour).also { mBinding.tvSpaceSelected.text = it }
        (employee.name + " " + employee.last_name).also { mBinding.tvEmployeeSelected.text = it }

        mBinding.btnFinalContinue.setOnClickListener {
//            toast("Va a iniciar createAppointment")
            mBinding.btnFinalContinue.isClickable = false
            createAppointment()
        }
    }

    private fun createAppointment() {
        val token = preferences["jwt", ""]
        val service = getRetrofit().create(APIService::class.java)
        val call = service.createAppointment(token, space.start_hour, space.end_hour,
        serviceG.id, vehicle.id, employee.id)
        call.enqueue(object : Callback<AppointmentCreateUserResponse> {
            override fun onResponse(
                call: Call<AppointmentCreateUserResponse>,
                response: Response<AppointmentCreateUserResponse>
            ) {
                if (response.isSuccessful){
                    if (response.body() == null){
                        toast("Respues null desde creación de citas")
                    }
                    if (response.body()?.success == true){
                        response.body()?.response?.let { toast(it) }
                        finish()
                    }else{
                        mBinding.btnFinalContinue.isClickable = true
                        response.body()?.response?.let { toast(it) }
                    }
                }else{
                    showError()
                }
            }

            override fun onFailure(call: Call<AppointmentCreateUserResponse>, t: Throwable) {
                toast("Fallo la conexión -> $t")
            }

        })
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://sea-lion-app-ffsmx.ondigitalocean.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onBackPressed() {
        when {
            mBinding.stepServices.visibility == View.VISIBLE -> {
                mBinding.stepServices.visibility = View.GONE
                mBinding.stepVehicles.visibility = View.VISIBLE
            }
            mBinding.stepSpaces.visibility == View.VISIBLE -> {
                mBinding.stepSpaces.visibility = View.GONE
                mBinding.stepServices.visibility = View.VISIBLE
            }
            mBinding.stepEmployees.visibility == View.VISIBLE -> {
                mBinding.stepEmployees.visibility = View.GONE
                mBinding.stepSpaces.visibility = View.VISIBLE
            }
            mBinding.stepFinal.visibility == View.VISIBLE -> {
                mBinding.stepFinal.visibility = View.GONE
                mBinding.stepEmployees.visibility = View.VISIBLE
            }
            mBinding.stepVehicles.visibility == View.VISIBLE -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.dialog_create_appointment_exit_title))
                builder.setMessage(getString(R.string.dialog_create_appointment_exit_message))
                builder.setPositiveButton(getString(R.string.dialog_create_appointment_exit_positive_btn)) { _, _ ->
                    finish()
                }
                builder.setNegativeButton(getString(R.string.dialog_create_appointment_exit_negative_btn)) { dialog, _ ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            }
        }
    }

    private fun showError() {
        toast("Ha ocurrido un error")
    }
}

