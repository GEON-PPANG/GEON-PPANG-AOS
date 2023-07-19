package com.sopt.geonppang.presentation.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.FragmentMyPageBinding
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val viewModel by viewModels<MyPageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListeners()
        initLayout()
        collectData()
    }

    private fun addListeners() {
        binding.layoutMyPageBookmark.setOnClickListener {
            moveToStoreBakeryList()
        }

        binding.layoutMyPageReview.setOnClickListener {
            moveToMyReview()
        }
    }

    private fun initLayout() {
        viewModel.fetchMypageInfo()
    }

    private fun collectData() {
        viewModel.mypageInfoState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    binding.viewModel?.profile = it.data
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun moveToStoreBakeryList() {
        startActivity(Intent(requireContext(), MyStoreListActivity::class.java))
    }

    private fun moveToMyReview() {
        startActivity(Intent(requireContext(), MyReviewActivity::class.java))
    }
}
