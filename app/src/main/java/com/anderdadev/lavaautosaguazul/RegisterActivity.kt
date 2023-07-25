package com.anderdadev.lavaautosaguazul

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.anderdadev.lavaautosaguazul.PreferenceHelper.get
import com.anderdadev.lavaautosaguazul.PreferenceHelper.set
import com.anderdadev.lavaautosaguazul.databinding.ActivityRegisterBinding
import com.anderdadev.lavaautosaguazul.util.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RegisterActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityRegisterBinding
    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        supportActionBar?.hide()

        if(preferences["jwt", ""].contains(".")){
            goToHomeActivity()
        }

        mBinding.btnCreateAccount.setOnClickListener {
            mBinding.btnCreateAccount.isClickable = false
            performRegister()
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun performRegister() {

        val userName = mBinding.etUsername.text.toString()
        val name = mBinding.etName.text.toString()
        val lastName = mBinding.etLastName.text.toString()
        val email = mBinding.etEmail.text.toString()
        val password = mBinding.etPassword.text.toString()

        if(email.trim().isEmpty() || password.trim().isEmpty() ||
                lastName.trim().isEmpty() || name.trim().isEmpty() ||
                password.trim().isEmpty()){
            toast("Todos los campos son obligatorio")
            mBinding.btnCreateAccount.isClickable = true
            return
        }

        CoroutineScope(Dispatchers.IO).launch {

            val call = getRetrofit().create(APIService::class.java).register("register", userName, name,
                lastName, email, password)

            runOnUiThread {
                if (call.isSuccessful) {

                    val response = call.body()

                    if (response == null){
                        toast("Se obtuvo un error del servidor")
                        return@runOnUiThread
                    }

                    if (response.success) {
                        Log.i("token app: ", response.token)
                        Log.i("role app: ", response.user.role)
                        createSessionPreferences(response.token, response.user.role)
                        goToHomeActivity()
                    }else{
                        mBinding.btnCreateAccount.isClickable = true
                        toast(response.message)
                        return@runOnUiThread
                    }

                } else{
                    mBinding.btnCreateAccount.isClickable = true
                    showError()
                }
            }
        }
    }

    private fun createSessionPreferences(jwt: String, role: String) {
        preferences["jwt"] = jwt
        preferences["role"] = role
    }

    private fun goToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun getRetrofit(): Retrofit {
//        val gson = GsonBuilder()
//            .setLenient()
//            .create()
        return Retrofit.Builder()
            //.baseUrl("https://sea-lion-app-ffsmx.ondigitalocean.app/api/")
            //.baseUrl("https://sea-lion-app-ou3yg.ondigitalocean.app/api/")
            .baseUrl("https://lava-autos.anderscode.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun showError() {
        toast("Ha ocurrido un error en login")
    }
}