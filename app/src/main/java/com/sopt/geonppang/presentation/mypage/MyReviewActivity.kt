package com.sopt.geonppang.presentation.mypage

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityMyReviewBinding
import com.sopt.geonppang.presentation.model.MyReviewBakeryInfo
import com.sopt.geonppang.presentation.myReviewDetail.MyReviewDetailActivity
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class MyReviewActivity : BindingActivity<ActivityMyReviewBinding>(R.layout.activity_my_review) {
    private val viewModel: MyPageViewModel by viewModels()

    lateinit var myReviewAdapter: MyReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        myReviewAdapter = MyReviewAdapter(::moveToReviewDetail)
        binding.rvMyReviewList.adapter = myReviewAdapter
    }

    private fun addListeners() {
        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun collectData() {
        viewModel.mypageReviewListState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    myReviewAdapter.submitList(it.data)
                }

                is UiState.Error -> {
                    Timber.e(it.message)
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun moveToReviewDetail(reviewId: Int, myReviewDetailInfo: MyReviewBakeryInfo) {
        val intent = Intent(this, MyReviewDetailActivity::class.java)
        intent.putExtra(REVIEW_ID, reviewId)
        intent.putExtra(BAKERY_INFO, myReviewDetailInfo)
        startActivity(intent)
    }

    companion object {
        const val REVIEW_ID = "reviewId"
        const val BAKERY_INFO = "bakeryInfo"
    }
}
