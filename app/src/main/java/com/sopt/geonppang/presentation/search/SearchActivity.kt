package com.sopt.geonppang.presentation.search

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivitySearchBinding
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.hideKeyboard

class SearchActivity : BindingActivity<ActivitySearchBinding>(R.layout.activity_search) {
    private val viewModel: SearchViewModel by viewModels()

    lateinit var bakeryAdapter: BakeryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        bakeryAdapter = BakeryAdapter()
        binding.layoutBakeryList.adapter = bakeryAdapter
        bakeryAdapter.submitList(viewModel.mockBakeryList)
    }

    private fun addListeners() {
        binding.ivSearchDelete.setOnClickListener {
            binding.etSearch.text = null
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        hideKeyboard(binding.root)
        return super.dispatchTouchEvent(ev)
    }
}
