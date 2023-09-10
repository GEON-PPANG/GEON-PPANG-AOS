package com.sopt.geonppang.presentation.mypage

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityMyBookmarksBinding
import com.sopt.geonppang.presentation.common.BakeryAdapter
import com.sopt.geonppang.presentation.detail.DetailActivity
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class MyBookMarksActivity :
    BindingActivity<ActivityMyBookmarksBinding>(R.layout.activity_my_bookmarks) {
    private val myPageViewModel: MyPageViewModel by viewModels()
    private lateinit var bakeryAdapter: BakeryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = myPageViewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        bakeryAdapter = BakeryAdapter(::moveToDetail)
        binding.rvStoreList.adapter = bakeryAdapter
    }

    private fun addListeners() {
        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun collectData() {
        myPageViewModel.mypageBookmarkListState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    myPageViewModel.fetchMypageBookmarkList()
                    bakeryAdapter.submitList(it.data)
                }

                is UiState.Error -> {
                    Timber.e(it.message)
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun moveToDetail(bakeryId: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(BAKERY_ID, bakeryId)
        startActivity(intent)
    }

    companion object {
        const val BAKERY_ID = "bakeryId"
    }
}
