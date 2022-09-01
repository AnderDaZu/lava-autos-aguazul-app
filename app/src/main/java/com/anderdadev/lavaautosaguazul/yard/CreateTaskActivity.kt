package com.anderdadev.lavaautosaguazul.yard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import com.anderdadev.lavaautosaguazul.APIService
import com.anderdadev.lavaautosaguazul.PreferenceHelper
import com.anderdadev.lavaautosaguazul.PreferenceHelper.get
import com.anderdadev.lavaautosaguazul.databinding.ActivityCreateTaskBinding
import com.anderdadev.lavaautosaguazul.resposes.*
import com.anderdadev.lavaautosaguazul.util.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CreateTaskActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityCreateTaskBinding
    private var selectedRadioButtonAppointment: RadioButton? = null
    private var appointments = mutableListOf<AppointmentYard>()
    private lateinit var appointment: AppointmentYard
    private lateinit var description: String
    private lateinit var price: String
    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        mBinding = ActivityCreateTaskBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        loadAppointments()

        mBinding.btnInitAppointment.setOnClickListener {
            if (!mBinding.etPrice.text.isNullOrEmpty() &&
                !mBinding.editTextDescription.text.isNullOrEmpty() &&
                selectedRadioButtonAppointment!!.isChecked
            ){
//                toast("vas bien")
                initAppointment()
            }else{
                toast("Debes completar los campos")
            }
        }

    }

    private fun loadAppointments() {
        val token = preferences["jwt", ""]
        val service = getRetrofit().create(APIService::class.java)
        val call = service.getAppointments(token)
        call.enqueue(object : Callback<AppointmentYardResponse> {
            override fun onResponse(
                call: Call<AppointmentYardResponse>,
                response: Response<AppointmentYardResponse>
            ) {
                if (response.isSuccessful){
                    if (response.body()!!.success){
                        val totalAppointments = response.body()!!.appointments
                        appointments.clear()
                        totalAppointments.forEach { it ->
                            appointments.add(it)
                        }
                        createButton(appointments)
                    }else{
                        toast(response.body()!!.message)
                        return
                    }
                }else{
                    showError()
                }
            }
            override fun onFailure(call: Call<AppointmentYardResponse>, t: Throwable) {
                toast("Fallo la conexión")
            }
        })
    }

    private fun initAppointment(){
        val token = preferences["jwt", ""]
        description = mBinding.editTextDescription.text.toString().replace("\\s+".toRegex()," ")
        if (description.length < 4){
            description = "Ninguna"
        }
        price = mBinding.etPrice.text.toString()
        val service2 = getRetrofit().create(APIService::class.java)
        val call2 = service2.registerTask(token, price, description, appointment.id)
        call2.enqueue(object : Callback<AppointmentCreateYardResponse> {
            override fun onResponse(
                call: Call<AppointmentCreateYardResponse>,
                response: Response<AppointmentCreateYardResponse>
            ) {
                if (response.isSuccessful){
                    if (response.body()!!.success){
                        toast(response.body()!!.message)
                        finish()
                    }else{
                        toast("No se pudo iniciar servicio")
                        return
                    }
                }else{
                    showError()
                }
            }
            override fun onFailure(call: Call<AppointmentCreateYardResponse>, t: Throwable) {
                toast("Fallo la conexión")
            }
        })
    }

    private fun createButton(appointments: List<AppointmentYard>) {

        selectedRadioButtonAppointment = null
        mBinding.radioButtonParentAppointments.removeAllViews()
        appointments.forEach { it ->
            val radioButton = RadioButton(this)
            radioButton.id = it.id
            (it.date + ", " + it.hour_start + ", " + it.service +", "+
                    it.employee + ", " + it.plate + ", " + it.model)
                .also { radioButton.text = it }
//            radioButton.setPadding(8, 12, 8, 12)
//            radioButton.ellipsize = TextUtils.TruncateAt.START

            radioButton.setOnClickListener { view ->
                selectedRadioButtonAppointment?.isChecked = false
                selectedRadioButtonAppointment = view as RadioButton
                selectedRadioButtonAppointment?.isChecked = true
                appointment = it
            }
            mBinding.radioButtonParentAppointments.addView(radioButton)
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://sea-lion-app-ffsmx.ondigitalocean.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun showError() {
        toast("Ha ocurrido un error")
    }
}