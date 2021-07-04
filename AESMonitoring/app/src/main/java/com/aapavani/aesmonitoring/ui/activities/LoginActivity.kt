package com.aapavani.aesmonitoring.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.aapavani.aesmonitoring.R
import com.aapavani.aesmonitoring.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    val MONITOR_USER_NAME = "monitor123"
    val SERVICER_USER_NAME = "service123"

    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        binding.Login.setOnClickListener{
            if( binding.userNameTV.text.isNullOrEmpty() ){
                binding.userNameTV.setError("Required")
                return@setOnClickListener
            }
            if( binding.passwordTV.text.isNullOrEmpty() ){
                binding.passwordTV.setError("Required")
                return@setOnClickListener
            }
            when ( binding.userNameTV.text.toString() ){
                MONITOR_USER_NAME-> navigateToMonitorPager()
                SERVICER_USER_NAME -> navigateToServicerPage()
            }
        }
    }

    private fun navigateToServicerPage() {
        startActivity( Intent(this, ServicerLandingPageActivity::class.java) )
        finish()
    }

    private fun navigateToMonitorPager() {
        startActivity( Intent(this, MonitorLandingPageActivity::class.java) )
        finish()
    }

//    fun onLoginCl
}