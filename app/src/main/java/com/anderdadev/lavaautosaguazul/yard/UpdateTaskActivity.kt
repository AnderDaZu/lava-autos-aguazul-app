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
import com.anderdadev.lavaautosaguazul.databinding.ActivityUpdateTaskBinding
import com.anderdadev.lavaautosaguazul.resposes.Task
import com.anderdadev.lavaautosaguazul.resposes.TaskUpdateYardResponse
import com.anderdadev.lavaautosaguazul.resposes.TaskYardResponse
import com.anderdadev.lavaautosaguazul.util.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UpdateTaskActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityUpdateTaskBinding
    private var selectedRadioButtonTask: RadioButton? = null
    private lateinit var task: Task
    private var tasks = mutableListOf<Task>()
    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        mBinding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        loadTasks()

        mBinding.btnUpdateTask.isClickable = false

    }

    private fun loadTasks() {
        val token = preferences["jwt", ""]
        val service = getRetrofit().create(APIService::class.java)
        val call = service.getAppointmentsStarted(token)
        call.enqueue(object : Callback<TaskYardResponse> {
            override fun onResponse(
                call: Call<TaskYardResponse>,
                response: Response<TaskYardResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        val totalAppointments = response.body()!!.tasks
                        tasks.clear()
                        totalAppointments.forEach {
//                            if (it.finished.isEmpty()) {
                                tasks.add(it)
//                            }
                        }
                        createButton(tasks)
                    } else {
                        toast(response.body()!!.message)
                        return
                    }
                } else {
                    showError()
                }
            }

            override fun onFailure(call: Call<TaskYardResponse>, t: Throwable) {
                toast("Fallo la conexión")
            }
        })
    }

    private fun createButton(tasks: List<Task>) {

        selectedRadioButtonTask = null
        mBinding.btnUpdateTask.isClickable = false
        mBinding.radioButtonParentTasks.removeAllViews()
        tasks.forEach { it ->

            val radioButton = RadioButton(this)
            radioButton.id = it.id
            (it.date + ", " + it.hour + ", " + it.service + ", "+
                    it.price + ", " + it.plate + ", " + it.modelcar)
                .also { radioButton.text = it }

            radioButton.setOnClickListener { view ->
                selectedRadioButtonTask?.isChecked = false
                selectedRadioButtonTask = view as RadioButton
                selectedRadioButtonTask?.isChecked = true
                mBinding.btnUpdateTask.isClickable = true
                task = it
                mBinding.btnUpdateTask.setOnClickListener {
                    if (selectedRadioButtonTask?.isChecked == false) {
                        toast("Selecciona un servicio primero")
                    }
//                    if (selectedRadioButtonTask == null){
//                        toast("No hay servicios para terminar")
//                    }
                    if (selectedRadioButtonTask?.isChecked == true) {
//                        toast("vas bien")
                        if (mBinding.stepTasks.visibility == View.VISIBLE) {
                            val builder = AlertDialog.Builder(this)
                            builder.setTitle(getString(R.string.dialog_finish_service_save_title))
                            builder.setMessage(getString(R.string.dialog_finish_service_stop_message))
                            builder.setPositiveButton(getString(R.string.dialog_finish_service_save_btn)) { _, _ ->
//                                finish()
                                finishTask()
                            }
                            builder.setNegativeButton(getString(R.string.dialog_finish_service_stop_btn)) { dialog, _ ->
                                dialog.dismiss()
                            }
                            val dialog = builder.create()
                            dialog.show()
                        }
                    }
                }
            }
            mBinding.radioButtonParentTasks.addView(radioButton)
        }
    }

    private fun finishTask() {
        val token = preferences["jwt", ""]
        val service = getRetrofit().create(APIService::class.java)
        val call = service.updateTask(task.id, token)
        call.enqueue(object : Callback<TaskUpdateYardResponse> {
            override fun onResponse(
                call: Call<TaskUpdateYardResponse>,
                response: Response<TaskUpdateYardResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        toast(response.body()!!.finished)
                        toast("Se finalizó servicio")
                        finish()
                    } else {
                        toast(response.body()!!.message)
                        return
                    }
                } else {
                    showError()
                }
            }

            override fun onFailure(call: Call<TaskUpdateYardResponse>, t: Throwable) {
                toast("Fallo la conexión")
            }
        })
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