package com.sopt.geonppang.presentation

import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.data.datasource.local.GPDataSource
import com.sopt.geonppang.databinding.ActivitySplashBinding
import com.sopt.geonppang.presentation.auth.SignActivity
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        loadSplashScreen()
    }

    private fun loadSplashScreen() {
        lifecycleScope.launch {
            delay(1500L)
            setAutoLogin()
            finish()
        }
    }

    private fun setAutoLogin() {
        val gpDataSource = GPDataSource(this)
        if (gpDataSource.isLogin) {
            AmplitudeUtils.trackEvent(LOGIN_APP)
            moveToHome()
        } else moveToSign()
    }

    private fun moveToHome() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun moveToSign() {
        startActivity(Intent(this, SignActivity::class.java))
    }

    companion object {
        const val LOGIN_APP = "login_app"
    }
}
