package com.sopt.geonppang.presentation.filter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityFilterBinding
import com.sopt.geonppang.util.binding.BindingActivity

class FilterActivity : BindingActivity<ActivityFilterBinding>(R.layout.activity_filter) {
    private lateinit var viewModel: FilterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        viewModel = ViewModelProvider(this).get(FilterViewModel::class.java)

        initLayout()
    }

    private fun initLayout() {
        supportFragmentManager.findFragmentById(R.id.fcv_filter_container)
            ?: navigateTo<MainPurposeFilterFragment>()
    }

    private inline fun <reified T : Fragment> navigateTo() {
        supportFragmentManager.commit {
            replace<T>(R.id.fcv_filter_container, T::class.java.canonicalName)
        }
    }
}
