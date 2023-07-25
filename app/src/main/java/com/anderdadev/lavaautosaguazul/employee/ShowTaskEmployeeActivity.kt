package com.anderdadev.lavaautosaguazul.employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.anderdadev.lavaautosaguazul.APIService
import com.anderdadev.lavaautosaguazul.PreferenceHelper
import com.anderdadev.lavaautosaguazul.PreferenceHelper.get
import com.anderdadev.lavaautosaguazul.adapter.ShowTaskEmployeeAdapter
import com.anderdadev.lavaautosaguazul.databinding.ActivityTaskEmployeeBinding
import com.anderdadev.lavaautosaguazul.resposes.TaskEmployee
import com.anderdadev.lavaautosaguazul.util.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShowTaskEmployeeActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityTaskEmployeeBinding
    private lateinit var adapter: ShowTaskEmployeeAdapter
    private var tasksG = mutableListOf<TaskEmployee>()
    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mBinding = ActivityTaskEmployeeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        getTasks()
        initRecyclerView()
    }

    private fun getTasks() {
        CoroutineScope(Dispatchers.IO).launch {
            val token = preferences["jwt", ""]
            val call = getRetrofit().create(APIService::class.java)
                .getTasksEmployee("tasks-employee", token)
            val tasksRes = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    if (tasksRes!!.success) {
                        tasksG.clear()
                        tasksG.addAll(tasksRes.tasks)
                        adapter.notifyDataSetChanged()
                    } else {
                        toast(tasksRes.message)
                    }
                } else {
                    showError()
                }
            }
        }
    }

    private fun initRecyclerView() {
        adapter = ShowTaskEmployeeAdapter(tasksG)
        mBinding.rvTaskEmployee.layoutManager = LinearLayoutManager(this)
        mBinding.rvTaskEmployee.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            //.baseUrl("https://sea-lion-app-ffsmx.ondigitalocean.app/api/v1/")
            //.baseUrl("https://sea-lion-app-ou3yg.ondigitalocean.app/api/v1/")
            .baseUrl("https://lava-autos.anderscode.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }
}