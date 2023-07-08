package com.sopt.geonppang.presentation.dummy

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityDummyBinding
import com.sopt.geonppang.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DummyActivity : BindingActivity<ActivityDummyBinding>(R.layout.activity_signup_email) {
    private val viewModel: DummyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        // binding.lifecycleOwner = this
        // binding에서 LiveData를 사용할 경우 해당 코드 필요
    }

    private fun initLayout() {
        TODO("Not yet implemented")
    }

    private fun addListeners() {
        TODO("Not yet implemented")
    }

    private fun addObservers() {
        TODO("Not yet implemented")
    }
}
