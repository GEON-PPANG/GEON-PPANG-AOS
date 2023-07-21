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
        val isMyPageRequested = intent.getStringExtra(MYPAGE_FRAGMENT) == MYPAGE_FRAGMENT
        val initialFragment = if (isMyPageRequested) {
            navigateTo<MyPageFragment>()
            R.id.menu_mypage
        } else {
            navigateTo<HomeFragment>()
            R.id.menu_home
        }

        binding.bnvHome.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.menu_home -> navigateTo<HomeFragment>()
                R.id.menu_sotrelist -> navigateTo<BakeryListFragment>()
                R.id.menu_mypage -> navigateTo<MyPageFragment>()
            }
            true
        }

        binding.bnvHome.apply {
            menu.findItem(initialFragment)?.isChecked = true
            itemIconTintList = null
        }
    }


    private inline fun <reified T : Fragment> navigateTo() {
        supportFragmentManager.commit {
            replace<T>(R.id.fcv_home_container, T::class.java.canonicalName)
        }
    }

    companion object {
        const val MYPAGE_FRAGMENT = "MyPageFragment"
    }
}
