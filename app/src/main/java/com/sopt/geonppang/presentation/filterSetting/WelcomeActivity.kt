package com.sopt.geonppang.presentation.filterSetting

import android.content.Intent
import android.os.Bundle
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityWelcomeBinding
import com.sopt.geonppang.presentation.MainActivity
import com.sopt.geonppang.util.binding.BindingActivity

class WelcomeActivity : BindingActivity<ActivityWelcomeBinding>(R.layout.activity_welcome) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        val nickName = intent.getStringExtra(NICKNAME)
        binding.tvStartWelcomeTitle.text = this.getString(R.string.welcome_to_geonppang, nickName)
    }

    private fun addListeners() {
        binding.btnWelcome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        const val NICKNAME = "nickName"
    }
}
