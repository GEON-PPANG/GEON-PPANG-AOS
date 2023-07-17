package com.sopt.geonppang.presentation.filter

import android.content.Intent
import android.os.Bundle
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityWelcomeBinding
import com.sopt.geonppang.presentation.MainActivity
import com.sopt.geonppang.util.binding.BindingActivity

class WelcomeActivity : BindingActivity<ActivityWelcomeBinding>(R.layout.activity_welcome) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addListeners()
    }

    private fun addListeners() {
        binding.btnWelcome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
