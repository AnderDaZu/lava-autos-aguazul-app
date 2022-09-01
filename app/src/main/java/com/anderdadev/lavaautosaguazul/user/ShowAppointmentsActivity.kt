package com.anderdadev.lavaautosaguazul.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.anderdadev.lavaautosaguazul.APIService
import com.anderdadev.lavaautosaguazul.PreferenceHelper
import com.anderdadev.lavaautosaguazul.PreferenceHelper.get
import com.anderdadev.lavaautosaguazul.adapter.AppointmentAdapter
import com.anderdadev.lavaautosaguazul.databinding.ActivityShowAppointmentsBinding
import com.anderdadev.lavaautosaguazul.resposes.AppointmentUser
import com.anderdadev.lavaautosaguazul.util.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShowAppointmentsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityShowAppointmentsBinding
    private var appointments = mutableListOf<AppointmentUser>()
    private lateinit var adapter: AppointmentAdapter
    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mBinding = ActivityShowAppointmentsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        getAppointments()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        adapter = AppointmentAdapter(appointments)
        mBinding.rvAppointments.layoutManager = LinearLayoutManager(this)
        mBinding.rvAppointments.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://sea-lion-app-ffsmx.ondigitalocean.app/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getAppointments() {
        val token = preferences["jwt", ""]
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java)
                .appointments("appointment-morning", token)
            val services = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    if (call.body()!!.success) {
                        if (call.body()!!.appointments.isNotEmpty()) {
                            val appointmentsReturn = services?.appointments ?: emptyList()
                            appointments.clear()
                            appointments.addAll(appointmentsReturn)
                            adapter.notifyDataSetChanged()
                        }else{
                            toast("No hay citas agendadas")
                            return@runOnUiThread
                        }
                    } else {
                        toast("No hay citas agendadas")
                        return@runOnUiThread
                    }
                } else
                    showError()
            }
        }
    }

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }
}