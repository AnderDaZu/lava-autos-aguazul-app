package com.anderdadev.lavaautosaguazul

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.anderdadev.lavaautosaguazul.PreferenceHelper.set
import com.anderdadev.lavaautosaguazul.PreferenceHelper.get
import com.anderdadev.lavaautosaguazul.databinding.ActivityHomeBinding
import com.anderdadev.lavaautosaguazul.employee.ShowScheduledTaskEmployeeActivity
import com.anderdadev.lavaautosaguazul.employee.ShowTaskEmployeeActivity
import com.anderdadev.lavaautosaguazul.employee.ShowUnscheduledTaskEmployeeActivity
import com.anderdadev.lavaautosaguazul.user.CreateAppointmentUserActivity
import com.anderdadev.lavaautosaguazul.user.CreateVehicleActivity
import com.anderdadev.lavaautosaguazul.user.ShowAppointmentsActivity
import com.anderdadev.lavaautosaguazul.user.ShowVehiclesActivity
import com.anderdadev.lavaautosaguazul.util.toast
import com.anderdadev.lavaautosaguazul.yard.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityHomeBinding
    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        if(preferences["jwt", ""].contains(".")){
            validate()
        }

        when {
            preferences["role", ""] == "user" -> {
                mBinding.btnRegisterTask.isVisible = false
                mBinding.btnListScheduledTasksYard.isVisible = false
                mBinding.btnListUnscheduledTasksYard.isVisible = false
                mBinding.btnListAppointmentsEmployee.isVisible = false
                mBinding.btnListScheduledTasksEmployee.isVisible = false
                mBinding.btnListUnscheduledTasksEmployee.isVisible = false
                mBinding.btnCreateTask.isVisible = false
                mBinding.btnFinishTask.isVisible = false
            }
            preferences["role", ""] == "employee" -> {
                mBinding.btnServices.isVisible = false
                mBinding.btnListVehicles.isVisible = false
                mBinding.btnListAppointments.isVisible = false
                mBinding.btnCreateVehicle.isVisible = false
                mBinding.btnCreateAppointment.isVisible = false
                mBinding.btnRegisterTask.isVisible = false
                mBinding.btnListUnscheduledTasksYard.isVisible = false
                mBinding.btnListScheduledTasksYard.isVisible = false
                mBinding.btnCreateTask.isVisible = false
                mBinding.btnFinishTask.isVisible = false
            }
            preferences["role", ""] == "yard_manager" -> {
                mBinding.btnCreateAppointment.isVisible = false
                mBinding.btnListAppointments.isVisible = false
                mBinding.btnListVehicles.isVisible = false
                mBinding.btnCreateVehicle.isVisible = false
                mBinding.btnListAppointmentsEmployee.isVisible = false
                mBinding.btnListScheduledTasksEmployee.isVisible = false
                mBinding.btnListUnscheduledTasksEmployee.isVisible = false
            }
        }

        mBinding.btnServices.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }

        mBinding.btnCreateAppointment.setOnClickListener {
            goToUserCreateAppointmentActivity()
        }

        mBinding.btnListVehicles.setOnClickListener {
            goToShowVehiclesActivity()
        }

        mBinding.btnCreateVehicle.setOnClickListener {
            goToCreateVehiclesActivity()
        }

//        mBinding.btnAccount.setOnClickListener {
//            goToAccountActivity()
//        }

        mBinding.btnListAppointments.setOnClickListener {
            goToShowAppointmentsActivity()
        }

        mBinding.btnFinishTask.setOnClickListener {
            goToUpdateTaskActivity()
        }

        mBinding.btnLogout.setOnClickListener {
//            clearSessionPreference()
//            goToMainActivity()
            mBinding.btnLogout.isClickable = false
            performLogout()
        }

        mBinding.btnRegisterTask.setOnClickListener {
            goToCreateTaskActivity()
        }

        mBinding.btnCreateTask.setOnClickListener {
            goToUnscheduledTaskActivity()
        }

        mBinding.btnListScheduledTasksYard.setOnClickListener {
            goToScheduledTaskYard()
        }
        mBinding.btnListUnscheduledTasksYard.setOnClickListener {
            goToUnscheduledTaskYard()
        }

        mBinding.btnListAppointmentsEmployee.setOnClickListener {
            goToTaskEmployee()
        }
        mBinding.btnListScheduledTasksEmployee.setOnClickListener {
            goToScheduledTaskEmployee()
        }
        mBinding.btnListUnscheduledTasksEmployee.setOnClickListener {
            goToUnscheduledTaskEmployee()
        }
    }

    private fun validate(){

        CoroutineScope(Dispatchers.IO).launch {
            val token = preferences["jwt", ""]
            val call = getRetrofit().create(APIService::class.java).validate("validate", token)

            runOnUiThread {
                if (call.isSuccessful) {

                    val response = call.body()

                    if (response == null){
                        toast("Se obtuvo un error del servidor")
                        return@runOnUiThread
                    }

                    if (!response.statusToken) {
                        Log.i("token app: ", response.statusToken.toString())
                        preferences["jwt"] = ""
                        preferences["role"] = ""
                        goToMainActivity()
                        toast("Sesión finalizo")
                    }

                } else{
                    showError()
                }
            }
        }
    }

    private fun performLogout() {
        CoroutineScope(Dispatchers.IO).launch {
            val token = preferences["jwt", ""]
            val call = getRetrofit().create(APIService::class.java).logout("logout", token)

            runOnUiThread {
                if (call.isSuccessful) {

                    val response = call.body()

                    if (response == null){
                        toast("Se obtuvo un error del servidor")
                        return@runOnUiThread
                    }

                    if (response.success) {
                        Log.i("success : ", response.success.toString())
                        Log.i("message: ", response.message)
                        clearSessionPreference()
                        toast("Sesión finalizada")
                        goToMainActivity()
                    }else{
                        toast("Ya cerraste sesión")
                    }

                } else{
                    mBinding.btnLogout.isClickable = true
                    showError()
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            //.baseUrl("https://sea-lion-app-ffsmx.ondigitalocean.app/api/")
            //.baseUrl("https://sea-lion-app-ou3yg.ondigitalocean.app/api/")
            .baseUrl("https://lava-autos.anderscode.com/api/")
//            .baseUrl("http://prueba1-los-coches.test:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun showError() {
        toast("Ha ocurrido un error en login")
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToUserCreateAppointmentActivity() {
        val intent = Intent(this, CreateAppointmentUserActivity::class.java)
        startActivity(intent)
    }

//    private fun goToAccountActivity() {
//        val intent = Intent(this, AccountActivity::class.java)
//        startActivity(intent)
//    }

    private fun goToShowVehiclesActivity() {
        val intent = Intent(this, ShowVehiclesActivity::class.java)
        startActivity(intent)
    }

    private fun goToShowAppointmentsActivity() {
        val intent = Intent(this, ShowAppointmentsActivity::class.java)
        startActivity(intent)
    }

    private fun goToCreateTaskActivity() {
        val intent = Intent(this, CreateTaskActivity::class.java)
        startActivity(intent)
    }

    private fun goToUpdateTaskActivity() {
        val intent = Intent(this, UpdateTaskActivity::class.java)
        startActivity(intent)
    }

    private fun goToUnscheduledTaskActivity() {
        val intent = Intent(this, CreateUnscheduledTaskActivity::class.java)
        startActivity(intent)
    }

    private fun goToCreateVehiclesActivity() {
        val intent = Intent(this, CreateVehicleActivity::class.java)
        startActivity(intent)
    }

    // Yard
    private fun goToScheduledTaskYard(){
        val intent = Intent(this, ShowScheduledTaskActivity::class.java)
        startActivity(intent)
    }

    private fun goToUnscheduledTaskYard(){
        val intent = Intent(this, ShowUnscheduledTaskActivity::class.java)
        startActivity(intent)
    }

    // Employee
    private fun goToTaskEmployee(){
        val intent = Intent(this, ShowTaskEmployeeActivity::class.java)
        startActivity(intent)
    }

    private fun goToScheduledTaskEmployee(){
        val intent = Intent(this, ShowScheduledTaskEmployeeActivity::class.java)
        startActivity(intent)
    }

    private fun goToUnscheduledTaskEmployee(){
        val intent = Intent(this, ShowUnscheduledTaskEmployeeActivity::class.java)
        startActivity(intent)
    }

    private fun clearSessionPreference() {
//        val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
//        val editor = preferences.edit()
//        editor.putBoolean("session", false)
//        editor.apply()
//        val preferences = PreferenceHelper.defaultPrefs(this)
//        preferences["session"] = false
        preferences["jwt"] = ""
    }
}