package com.anderdadev.lavaautosaguazul

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anderdadev.lavaautosaguazul.databinding.ActivityAccountBinding
import com.anderdadev.lavaautosaguazul.resposes.User

class AccountActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAccountBinding
    private var account = User(1,"Andres", "diaz", "andres@gmail", "user")
    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        mBinding = ActivityAccountBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

    }

}