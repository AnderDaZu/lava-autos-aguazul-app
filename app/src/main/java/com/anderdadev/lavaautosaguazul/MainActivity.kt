package com.anderdadev.lavaautosaguazul

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.anderdadev.lavaautosaguazul.PreferenceHelper.get
import com.anderdadev.lavaautosaguazul.PreferenceHelper.set
import com.anderdadev.lavaautosaguazul.databinding.ActivityMainBinding
import com.anderdadev.lavaautosaguazul.util.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        // primera versión de sharepreferences
//        val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
//        val session = preferences.getBoolean("session", false)
//        if(session){
//            goToHomeActivity()
//        }

//        val preferences = PreferenceHelper.defaultPrefs(this)

        Log.i("JWT", preferences["jwt",""])
        // ultima version
        if(preferences["jwt", ""].contains(".")){
            goToHomeActivity()
        }

        mBinding.btnStartSession.setOnClickListener {
            // validates
            mBinding.btnStartSession.isClickable = false
            performLogin()
        }

        mBinding.btnCreateAccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun performLogin() {

        val email = mBinding.etEmail.text.toString()
        val password = mBinding.etPassword.text.toString()

        if(email.trim().isEmpty() || password.trim().isEmpty()){
            toast("Email y Contraseña son obligatorio")
            mBinding.btnStartSession.isClickable = true
            return
        }

        CoroutineScope(Dispatchers.IO).launch {

            val call = getRetrofit().create(APIService::class.java).login("login", email, password)

            runOnUiThread {
                if (call.isSuccessful) {

                    val response = call.body()

                    if (response == null){
                        toast("Se obtuvo un error del servidor")
                        return@runOnUiThread
                    }

                    if (response.success) {
                        Log.i("token app: ", response.token)
                        Log.i("role app: ", response.role)
                        createSessionPreferences(response.token, response.role)
                        goToHomeActivity()
//                        when {
//                            preferences["role", ""] == "user" -> {
//                                goToUserHomeActivity()
//                            }
//                            preferences["role", ""] == "employee" -> {
//                                goToEmployeeHomeActivity()
//                            }
//                            preferences["role", ""] == "yard_manager" -> {
//                                goToYardHomeActivity()
//                            }
//                        }
                    }else{
                        mBinding.btnStartSession.isClickable = true

                        toast("Las credenciales son incorrectas")
                    }

                } else{
                    mBinding.btnStartSession.isClickable = true
                    showError()
                }
            }
        }
    }

    private fun createSessionPreferences(jwt: String, role: String) {
        // primera versión de sharepreferences
//        val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
//        val editor = preferences.edit()
//        editor.putBoolean("session", true)
//        editor.apply()
//        val preferences = PreferenceHelper.defaultPrefs(this)
        preferences["jwt"] = jwt
        preferences["role"] = role
//        preferences["session"] = true

    }

    private fun goToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://sea-lion-app-ffsmx.ondigitalocean.app/api/")
//            .baseUrl("http://prueba1-los-coches.test:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun showError() {
        toast("Ha ocurrido un error en login")
    }
}
