package com.sopt.geonppang.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityMainBinding
import com.sopt.geonppang.presentation.bakeryList.BakeryListFragment
import com.sopt.geonppang.presentation.home.HomeFragment
import com.sopt.geonppang.presentation.mypage.MyPageFragment
import com.sopt.geonppang.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
    }

    private fun initLayout() {
        supportFragmentManager.findFragmentById(R.id.fcv_home_container)
            ?: navigateTo<HomeFragment>()

        binding.bnvHome.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.menu_home -> navigateTo<HomeFragment>()
                R.id.menu_sotrelist -> navigateTo<BakeryListFragment>()
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
