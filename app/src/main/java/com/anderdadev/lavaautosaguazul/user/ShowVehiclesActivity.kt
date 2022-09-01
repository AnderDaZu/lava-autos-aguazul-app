package com.anderdadev.lavaautosaguazul.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.anderdadev.lavaautosaguazul.APIService
import com.anderdadev.lavaautosaguazul.PreferenceHelper
import com.anderdadev.lavaautosaguazul.PreferenceHelper.get
import com.anderdadev.lavaautosaguazul.adapter.ShowVehicleAdapter
import com.anderdadev.lavaautosaguazul.databinding.ActivityShowVehiclesBinding
import com.anderdadev.lavaautosaguazul.resposes.CheckVehicle
import com.anderdadev.lavaautosaguazul.util.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShowVehiclesActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityShowVehiclesBinding
    private lateinit var adapter: ShowVehicleAdapter
    private var vehicles = mutableListOf<CheckVehicle>()
    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityShowVehiclesBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        getVehicles()
        initRecyclerView()

    }


    private fun initRecyclerView(){
        adapter = ShowVehicleAdapter(vehicles)
        mBinding.rvVehicles.layoutManager = LinearLayoutManager(this)
        mBinding.rvVehicles.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://sea-lion-app-ffsmx.ondigitalocean.app/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getVehicles(){
        val token = preferences["jwt", ""]
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).vehicles("vehicles-user", token)
            val vehiclesRes = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    if (vehiclesRes != null) {
                        if (vehiclesRes.success) {
                            val vehiclesReturn = vehiclesRes?.vehicles
                            vehicles.clear()
                            vehicles.addAll(vehiclesReturn)
                            adapter.notifyDataSetChanged()
                        } else {
                            toast("No tiene vehículos registrados")
                        }
                    }
                } else {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }
}