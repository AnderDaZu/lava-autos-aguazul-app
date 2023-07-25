package com.anderdadev.lavaautosaguazul.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import com.anderdadev.lavaautosaguazul.APIService
import com.anderdadev.lavaautosaguazul.PreferenceHelper
import com.anderdadev.lavaautosaguazul.PreferenceHelper.get
import com.anderdadev.lavaautosaguazul.databinding.ActivityCreateVehicleBinding
import com.anderdadev.lavaautosaguazul.resposes.ColorVehicle
import com.anderdadev.lavaautosaguazul.resposes.DataResponse
import com.anderdadev.lavaautosaguazul.resposes.ModelVehicle
import com.anderdadev.lavaautosaguazul.resposes.VehicleRegisterResponse
import com.anderdadev.lavaautosaguazul.util.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CreateVehicleActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityCreateVehicleBinding
    private var colors = mutableListOf<ColorVehicle>()
    private lateinit var color: ColorVehicle
    private var models = mutableListOf<ModelVehicle>()
    private lateinit var model: ModelVehicle
    private var selectedRadioButtonColor: RadioButton? = null
    private var selectedRadioButtonModel: RadioButton? = null
    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mBinding = ActivityCreateVehicleBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        mBinding.btnVehicleRegister.setOnClickListener {

            if ( selectedRadioButtonColor?.isChecked == false ||
                selectedRadioButtonModel?.isChecked == false ||
                mBinding.etPlate.text!!.length < 6
            ) {
                toast("Completa el registro")
            }
            if ( selectedRadioButtonModel?.isChecked == true &&
                selectedRadioButtonColor?.isChecked == true &&
                mBinding.etPlate.text!!.length == 6){
                createVehicle()
            }
        }

        loadOptions()

    }

    private fun loadOptions(){
        val token = preferences["jwt", ""]
        val service = getRetrofit().create(APIService::class.java)
        val call = service.getDataVehicle(token)
        call.enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                if ( response.isSuccessful ){
                    val responseSuccess = response.body()
                    if (responseSuccess != null) {
                        if ( responseSuccess.success ){
                            val colorsRes = responseSuccess.colors
                            val modelRes = responseSuccess.models
                            colors.clear()
                            colorsRes.forEach {
                                colors.add(it)
                            }
                            models.clear()
                            modelRes.forEach {
                                models.add(it)
                            }
                            createButtonColors(colors)
                            createButtonModels(models)
                        }
                    }
                }else{
                    toast("Hubo fallo en la conexión")
                }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                toast("Fallo la conexión -> $t")
            }
        })
    }

    private fun createVehicle(){
        val token = preferences["jwt", ""]
        val plate = mBinding.etPlate.text.toString()
        val color = color.id
        val model = model.id
        val service = getRetrofit().create(APIService::class.java)
        val call = service.registerVehicle(token, plate, color, model)
        call.enqueue(object : Callback<VehicleRegisterResponse> {
            override fun onResponse(
                call: Call<VehicleRegisterResponse>,
                response: Response<VehicleRegisterResponse>
            ) {
                if ( response.isSuccessful ){
                    if (response.body()!!.success){
                        finish()
                        toast(response.body()!!.message)
                    }
                }else{
                    toast("Hubo fallo en la conexión")
                }
            }

            override fun onFailure(call: Call<VehicleRegisterResponse>, t: Throwable) {
                toast("Fallo la conexión -> $t")
            }

        })
    }

    private fun createButtonColors(colors: List<ColorVehicle>){
        selectedRadioButtonColor = null
        mBinding.radioButtonParentColors.removeAllViews()
        colors.forEach {
            val radioButton = RadioButton(this)
            radioButton.id = it.id
            radioButton.text = it.color
            radioButton.setOnClickListener { view ->
                selectedRadioButtonColor?.isChecked = false
                selectedRadioButtonColor = view as RadioButton
                selectedRadioButtonColor?.isChecked = true
                color = it
            }
            mBinding.radioButtonParentColors.addView(radioButton)
        }
    }

    private fun createButtonModels(models: List<ModelVehicle>){
        selectedRadioButtonModel = null
        models.forEach {
            val radioButton = RadioButton(this)
            radioButton.id = it.id
            radioButton.text = it.model
            radioButton.setOnClickListener { view ->
                selectedRadioButtonModel?.isChecked = false
                selectedRadioButtonModel = view as RadioButton
                selectedRadioButtonModel?.isChecked = true
                model = it
            }
            mBinding.radioButtonParentModels.addView(radioButton)
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            //.baseUrl("https://sea-lion-app-ffsmx.ondigitalocean.app/api/")
            //.baseUrl("https://sea-lion-app-ou3yg.ondigitalocean.app/api/")
            .baseUrl("https://lava-autos.anderscode.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}