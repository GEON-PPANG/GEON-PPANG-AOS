package com.sopt.geonppang.presentation.myPage

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityMyReviewBinding
import com.sopt.geonppang.presentation.detail.DetailActivity.Companion.SOURCE
import com.sopt.geonppang.presentation.detail.DetailActivity.Companion.VIEW_DETAIL_PAGE_AT
import com.sopt.geonppang.presentation.model.MyReviewBakeryInfo
import com.sopt.geonppang.presentation.myReviewDetail.MyReviewDetailActivity
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.CustomItemDecoration
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class MyReviewActivity : BindingActivity<ActivityMyReviewBinding>(R.layout.activity_my_review) {
    private val viewModel: MyPageViewModel by viewModels()
    private lateinit var myReviewAdapter: MyReviewAdapter

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
        binding.rvMyReviewList.apply {
            adapter = myReviewAdapter
            addItemDecoration(CustomItemDecoration(this@MyReviewActivity))
        }
    }

    private fun addListeners() {
        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun collectData() {
        viewModel.myPageReviewListState.flowWithLifecycle(lifecycle).onEach {
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
        AmplitudeUtils.trackEventWithProperties(VIEW_DETAIL_PAGE_AT, SOURCE, MY_PAGE_MY_REVIEW)
        val intent = Intent(this, MyReviewDetailActivity::class.java)
        intent.putExtra(REVIEW_ID, reviewId)
        intent.putExtra(BAKERY_INFO, myReviewDetailInfo)
        startActivity(intent)
    }

    companion object {
        const val REVIEW_ID = "reviewId"
        const val BAKERY_INFO = "bakeryInfo"
        const val MY_PAGE_MY_REVIEW = "MYPAGE_MYREVIEW"
    }
}
