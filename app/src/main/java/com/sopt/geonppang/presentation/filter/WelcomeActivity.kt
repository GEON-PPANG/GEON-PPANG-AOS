package com.sopt.geonppang.presentation.filter

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityWelcomeBinding
import com.sopt.geonppang.presentation.type.FilterInfoType
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
        val intent = Intent(this, FilterActivity::class.java)
        intent.putExtra(FILTER_INFO, FilterInfoType.HOME.activityName)
        startActivity(intent)
        finish()
    }

    companion object {
        const val NICKNAME = "nickName"
        const val FILTER_INFO = "filterInfo"
    }
}
