package com.anderdadev.lavaautosaguazul.yard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.anderdadev.lavaautosaguazul.APIService
import com.anderdadev.lavaautosaguazul.PreferenceHelper
import com.anderdadev.lavaautosaguazul.PreferenceHelper.get
import com.anderdadev.lavaautosaguazul.adapter.ShowScheduledTaskYardAdapter
import com.anderdadev.lavaautosaguazul.databinding.ActivityScheduledTasksBinding
import com.anderdadev.lavaautosaguazul.resposes.ScheduledTaskYard
import com.anderdadev.lavaautosaguazul.util.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShowScheduledTaskActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityScheduledTasksBinding
    private lateinit var adapter: ShowScheduledTaskYardAdapter
    private var tasks = mutableListOf<ScheduledTaskYard>()
    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        mBinding = ActivityScheduledTasksBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        getTasks()
        initRecyclerView()
    }

    private fun getTasks(){
        CoroutineScope(Dispatchers.IO).launch {
            val token = preferences["jwt", ""]
            val call = getRetrofit().create(APIService::class.java).getScheduledTasksYard("tasks-scheduled", token)
            val tasksRes = call.body()

            runOnUiThread {
                if (call.isSuccessful){
                    if (tasksRes!!.success){
                        tasks.clear()
                        tasks.addAll(tasksRes.tasks)
                        adapter.notifyDataSetChanged()
                    }else{
                        toast(tasksRes.message)
                    }
                }else
                    showError()
            }
        }
    }

    private fun initRecyclerView(){
        adapter = ShowScheduledTaskYardAdapter(tasks)
        mBinding.rvScheduledYard.layoutManager = LinearLayoutManager(this)
        mBinding.rvScheduledYard.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://sea-lion-app-ffsmx.ondigitalocean.app/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }
}