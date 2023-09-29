package com.sopt.geonppang.presentation.auth

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityWelcomeBinding
import com.sopt.geonppang.presentation.filterSetting.FilterSettingActivity
import com.sopt.geonppang.presentation.type.FilterInfoType
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.binding.BindingActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WelcomeActivity : BindingActivity<ActivityWelcomeBinding>(R.layout.activity_welcome) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        loadWelcomeScreen()
    }

    private fun initLayout() {
        val nickName = intent.getStringExtra(NICKNAME)
        binding.tvStartWelcomeTitle.text = this.getString(R.string.welcome_to_geonppang, nickName)
    }

    private fun loadWelcomeScreen() {
        lifecycleScope.launch {
            delay(1500L)
            moveToFilter()
            finish()
        }
    }

    private fun moveToFilter() {
        val intent = Intent(this, FilterSettingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        intent.putExtra(FILTER_INFO, FilterInfoType.ONBOARDING.name)
        startActivity(intent)
        finish()
    }

    companion object {
        const val NICKNAME = "nickname"
        const val FILTER_INFO = "filterInfo"
    }
}
