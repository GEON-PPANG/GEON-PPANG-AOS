package com.sopt.geonppang.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityMainBinding
import com.sopt.geonppang.util.binding.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView() {
        supportFragmentManager.findFragmentById(R.id.fcv_home_container)
            ?: navigateTo<HomeFragment>()

        binding.bnvHome.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                // TODO 해당하는 Fragment 연결
                R.id.menu_home -> navigateTo<HomeFragment>()
                R.id.menu_sotrelist -> {}
                R.id.menu_mypage -> navigateTo<MyPageFragment>()
            }
            true
        }
    }

    private inline fun <reified T : Fragment> navigateTo() {
        supportFragmentManager.commit {
            replace<T>(R.id.fcv_home_container, T::class.java.canonicalName)
        }
    }
}
