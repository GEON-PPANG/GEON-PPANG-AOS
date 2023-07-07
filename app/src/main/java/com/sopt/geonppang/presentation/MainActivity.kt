package com.sopt.geonppang.presentation

import android.os.Bundle
import androidx.fragment.app.commit
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityMainBinding
import com.sopt.geonppang.presentation.home.HomeFragment
import com.sopt.geonppang.util.binding.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO 삭제
        supportFragmentManager.commit {
            replace(R.id.fcv_container, HomeFragment())
        }
    }
}
