package com.anderdadev.lavaautosaguazul.yard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import com.anderdadev.lavaautosaguazul.APIService
import com.anderdadev.lavaautosaguazul.PreferenceHelper
import com.anderdadev.lavaautosaguazul.PreferenceHelper.get
import com.anderdadev.lavaautosaguazul.R
import com.anderdadev.lavaautosaguazul.databinding.ActivityCreateUnscheduledTaskBinding
import com.anderdadev.lavaautosaguazul.resposes.*
import com.anderdadev.lavaautosaguazul.util.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CreateUnscheduledTaskActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityCreateUnscheduledTaskBinding
    private var types = mutableListOf<Type>()
    private lateinit var type: Type
    private var services = mutableListOf<Service>()
    private lateinit var serviceG: Service
    private var employees = mutableListOf<Employee>()
    private lateinit var employee: Employee
    private lateinit var plate: String
    private lateinit var price: String
    private lateinit var description: String
    private var selectedRadioButtonType: RadioButton? = null
    private var selectedRadioButtonService: RadioButton? = null
    private var selectedRadioButtonEmployee: RadioButton? = null
    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        mBinding = ActivityCreateUnscheduledTaskBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        mBinding.stepServices.visibility = View.GONE
        mBinding.stepData.visibility = View.GONE
        mBinding.stepFinal.visibility = View.GONE

        mBinding.btnTypeContine.isClickable = false
        mBinding.btnServiceContinue.isClickable = false
        mBinding.btnDataContinue.isClickable = false
        mBinding.btnFinalContinue.isClickable = false

        if (!mBinding.btnTypeContine.isClickable){
            mBinding.btnTypeContine.setOnClickListener {
                toast("Selecciona una opción primero")
            }
        }

        if (!mBinding.btnServiceContinue.isClickable){
            mBinding.btnServiceContinue.setOnClickListener {
                toast("Selecciona un servicio y un empleado para continuar")
            }
        }

        mBinding.btnDataContinue.setOnClickListener {
            loadData()
        }

        loadTypes()
    }

    private fun loadTypes() {
        val token = preferences["jwt", ""]
        val service = getRetrofit().create(APIService::class.java)
        val call = service.getTypes(token)
        call.enqueue(object : Callback<UnscheduledTypeResponse> {
            override fun onResponse(
                call: Call<UnscheduledTypeResponse>,
                response: Response<UnscheduledTypeResponse>
            ) {
                if (response.isSuccessful){
                    if (response.body()!!.success){
                        if (response.body()!!.types.isNotEmpty()){
                            val totalTypes = response.body()!!.types
                            types.clear()
                            totalTypes.forEach {
                                types.add(it)
                            }
                            createButtonTypes(types)
                        }else{
                            toast("No hay registro")
                            return
                        }
                    }else {
                        toast(response.body()!!.message)
                        return
                    }
                }else{
                    showError()
                }
            }

            override fun onFailure(call: Call<UnscheduledTypeResponse>, t: Throwable) {
                toast("Fallo la conexión")
            }
        })
    }

    private fun createButtonTypes(types: List<Type>) {

        selectedRadioButtonType = null
        mBinding.radioButtonParentTypesVehicles.removeAllViews()
        types.forEach { it ->
            val radioButton = RadioButton(this)
            radioButton.id = it.id
            (it.name).also { radioButton.text = it }

            radioButton.setOnClickListener { view ->
                selectedRadioButtonType?.isChecked = false
                selectedRadioButtonType = view as RadioButton
                selectedRadioButtonType?.isChecked = true
                type = it
                if (selectedRadioButtonType?.isChecked == true) {
                    mBinding.btnTypeContine.isClickable
                    mBinding.btnTypeContine.setOnClickListener {
                        mBinding.stepTypes.visibility = View.GONE
                        mBinding.stepServices.visibility = View.VISIBLE
                        loadServices()
                    }
                } else if (selectedRadioButtonType?.isChecked == false) {
                    mBinding.btnTypeContine.isClickable = false
                }
            }

            mBinding.radioButtonParentTypesVehicles.addView(radioButton)
        }
    }

    private fun loadServices() {
        val token = preferences["jwt", ""]
        val service = getRetrofit().create(APIService::class.java)
        val call = service.getServicesTask(type.id, token)
        call.enqueue(object : Callback<UnscheduledServiceResponse> {
            override fun onResponse(
                call: Call<UnscheduledServiceResponse>,
                response: Response<UnscheduledServiceResponse>
            ) {
                if (response.isSuccessful){
                    if (response.body()!!.success){
                        if (response.body()!!.services.isNotEmpty()){
                            val totalServices = response.body()!!.services
                            val totalEmployees = response.body()!!.employees
                            services.clear()
                            totalServices.forEach {
                                services.add(it)
                            }
                            totalEmployees.forEach {
                                employees.add(it)
                            }
                            createButtonServices(services)
                            createButtonEmployees(employees)
                        }else{
                            toast("No hay registro")
                            return
                        }
                    }else {
                        toast(response.body()!!.message)
                        return
                    }
                }else{
                    showError()
                }
            }

            override fun onFailure(call: Call<UnscheduledServiceResponse>, t: Throwable) {
                toast("Fallo la conexión")
            }
        })
    }

    private fun createButtonServices(services: List<Service>) {

        selectedRadioButtonService = null
        mBinding.radioButtonParentServices.removeAllViews()
        services.forEach { it ->
            val radioButton = RadioButton(this)
            radioButton.id = it.id
            (it.name).also { radioButton.text = it }

            radioButton.setOnClickListener { view ->
                selectedRadioButtonService?.isChecked = false
                selectedRadioButtonService = view as RadioButton
                selectedRadioButtonService?.isChecked = true
                serviceG = it
//                if (selectedRadioButtonService?.isChecked == true) {
//                    mBinding.btnServiceContinue.isClickable
//                    mBinding.btnServiceContinue.setOnClickListener {
//                        mBinding.stepServices.visibility = View.GONE
////                        loadServices()
//                    }
//                } else if (selectedRadioButtonService?.isChecked == false) {
//                    mBinding.btnServiceContinue.isClickable = false
//                }
            }
            mBinding.radioButtonParentServices.addView(radioButton)
        }
    }

    private fun createButtonEmployees(employees: List<Employee>) {

        selectedRadioButtonEmployee = null
        mBinding.radioButtonParentEmployees.removeAllViews()
        employees.forEach { it ->
            val radioButton = RadioButton(this)
            radioButton.id = it.id
            (it.name + " " + it.last_name).also { radioButton.text = it }

            radioButton.setOnClickListener { view ->
                selectedRadioButtonEmployee?.isChecked = false
                selectedRadioButtonEmployee = view as RadioButton
                selectedRadioButtonEmployee?.isChecked = true
                employee = it
                if (selectedRadioButtonEmployee?.isChecked == true &&
                    selectedRadioButtonService?.isChecked == true) {
                    mBinding.btnServiceContinue.isClickable
                    mBinding.btnServiceContinue.setOnClickListener {
                        mBinding.stepServices.visibility = View.GONE
                        mBinding.stepData.visibility = View.VISIBLE
//                        loadServices()
                    }
                } else if (selectedRadioButtonService?.isChecked == false) {
                    mBinding.btnServiceContinue.isClickable = false
                }
            }

            mBinding.radioButtonParentEmployees.addView(radioButton)
        }
    }

    private fun loadData(){
        if ( !mBinding.etPlate.text.isNullOrEmpty() &&
            !mBinding.etPrice.text.isNullOrEmpty() &&
            !mBinding.editTextDescription.text.isNullOrEmpty()){
            plate = mBinding.etPlate.text.toString()
            price = mBinding.etPrice.text.toString()
            description = mBinding.editTextDescription.text.toString().replace("\\s+".toRegex()," ")
            mBinding.stepData.visibility = View.GONE
            mBinding.stepFinal.visibility = View.VISIBLE
            loadFinal()
        }else{
            toast("Completa los campos")
        }
    }

    private fun loadFinal(){
        mBinding.tvPlate.text = plate
        mBinding.tvPrice.text = price
        mBinding.textViewDescription.text = description
        (employee.name+" "+employee.last_name).also { mBinding.tvEmployee.text = it }
        mBinding.tvService.text = serviceG.name
        mBinding.tvType.text = type.name

        mBinding.btnFinalContinue.isClickable = true
        mBinding.btnFinalContinue.setOnClickListener {
//            createUnscheduledTask()
            val token = preferences["jwt", ""]
            val service = getRetrofit().create(APIService::class.java)
            val call = service.registerUnscheduledTask(token, plate, price,
                description, employee.id, serviceG.id, type.id)
            call.enqueue(object : Callback<UnscheduledTaskResponse> {
                override fun onResponse(
                    call: Call<UnscheduledTaskResponse>,
                    response: Response<UnscheduledTaskResponse>
                ) {
                    if (response.isSuccessful){
                        if (response.body() == null){
                            toast("Respues null desde creación de citas")
                        }
                        if (response.body()?.success == true){
                            response.body()?.message?.let { toast(it) }
                            finish()
                        }else{
                            mBinding.btnFinalContinue.isClickable = true
                            toast("No se logró registrar servicio")
                        }
                    }else{
                        showError()
                    }
                }

                override fun onFailure(call: Call<UnscheduledTaskResponse>, t: Throwable) {
                    mBinding.btnFinalContinue.isClickable = true
                    toast("Fallo la conexión -> $t")
                }

            })

            mBinding.btnFinalContinue.isClickable = false
        }
    }

    /*private fun createUnscheduledTask(){
        val token = preferences["jwt", ""]
        val service = getRetrofit().create(APIService::class.java)
        val call = service.registerUnscheduledTask(token, plate, price,
            description, employee.id, serviceG.id, type.id)
        call.enqueue(object : Callback<UnscheduledTaskResponse> {
            override fun onResponse(
                call: Call<UnscheduledTaskResponse>,
                response: Response<UnscheduledTaskResponse>
            ) {
                if (response.isSuccessful){
                    if (response.body() == null){
                        toast("Respues null desde creación de citas")
                    }
                    if (response.body()?.success == true){
                        response.body()?.message?.let { toast(it) }
                        finish()
                    }else{
                        mBinding.btnFinalContinue.isClickable = true
                        toast("No se logró registrar servicio")
                    }
                }else{
                    showError()
                }
            }

            override fun onFailure(call: Call<UnscheduledTaskResponse>, t: Throwable) {
                toast("Fallo la conexión -> $t")
            }

        })
    }*/

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://sea-lion-app-ffsmx.ondigitalocean.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun showError() {
        toast("Ha ocurrido un error")
    }

    override fun onBackPressed() {
        when {
            mBinding.stepServices.visibility == View.VISIBLE -> {
                mBinding.stepServices.visibility = View.GONE
                mBinding.stepTypes.visibility = View.VISIBLE
            }
            mBinding.stepData.visibility == View.VISIBLE -> {
                mBinding.stepData.visibility = View.GONE
                mBinding.stepServices.visibility = View.VISIBLE
            }
            mBinding.stepFinal.visibility == View.VISIBLE -> {
                mBinding.stepFinal.visibility = View.GONE
                mBinding.stepData.visibility = View.VISIBLE
            }
            mBinding.stepTypes.visibility == View.VISIBLE -> {
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

}