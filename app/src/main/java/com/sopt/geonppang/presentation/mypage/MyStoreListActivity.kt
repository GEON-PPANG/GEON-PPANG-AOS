package com.sopt.geonppang.presentation.mypage

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityMyStoreListBinding
import com.sopt.geonppang.presentation.search.BakeryAdapter
import com.sopt.geonppang.util.binding.BindingActivity

class MyStoreListActivity :
    BindingActivity<ActivityMyStoreListBinding>(R.layout.activity_my_store_list) {
    private val myPageViewModel: MyPageViewModel by viewModels()
    lateinit var bakeryAdapter: BakeryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = myPageViewModel

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        bakeryAdapter = BakeryAdapter()
        binding.rvStoreList.adapter = bakeryAdapter
        bakeryAdapter.submitList(myPageViewModel.mockStoreList)
    }

    private fun addListeners() {
        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }
    }
}
