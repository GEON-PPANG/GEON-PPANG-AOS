package com.sopt.geonppang.presentation.search

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivitySearchBinding
import com.sopt.geonppang.util.binding.BindingActivity

class SearchActivity : BindingActivity<ActivitySearchBinding>(R.layout.activity_search) {
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListeners()
    }

    private fun addListeners() {
        binding.ivSearchDelete.setOnClickListener {
            binding.etSearch.text = null
        }
    }
}
