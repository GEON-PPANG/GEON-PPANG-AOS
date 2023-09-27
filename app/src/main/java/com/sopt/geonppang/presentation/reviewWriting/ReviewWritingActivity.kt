package com.sopt.geonppang.presentation.reviewWriting

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityReviewWritingBinding
import com.sopt.geonppang.presentation.detail.DetailActivity
import com.sopt.geonppang.presentation.model.BakeryReviewWritingInfo
import com.sopt.geonppang.presentation.type.KeyWordType
import com.sopt.geonppang.presentation.type.LikeType
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingActivity
import com.sopt.geonppang.util.extension.hideKeyboard
import com.sopt.geonppang.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ReviewWritingActivity :
    BindingActivity<ActivityReviewWritingBinding>(R.layout.activity_review_writing) {
    private val viewModel: ReviewWritingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
        collectData()

        val bakeryId = intent.getIntExtra(BAKERY_ID, -1)
        viewModel.setBakeryId(bakeryId)
    }

    private fun initLayout() {
        val bakeryReviewWritingInfo =
            intent.getParcelableExtra<BakeryReviewWritingInfo>(BAKERY_INFO)
        bakeryReviewWritingInfo?.let { viewModel.setBakeryInfo(it) }
    }

    private fun addListeners() {
        binding.etWriteYourReview.setOnClickListener {
            binding.layoutScrollView.smoothScrollTo(0, binding.etWriteYourReview.top)
        }

        binding.etWriteYourReview.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                AmplitudeUtils.trackEvent(CLICK_REVIEW_WRITING_TEXT)
                binding.layoutScrollView.smoothScrollTo(0, binding.etWriteYourReview.top)
            }
        }

        binding.btnReviewSuccess.setOnSingleClickListener {
            viewModel.writeReview()
        }

        binding.toolbar.ivBack.setOnClickListener {
            AmplitudeUtils.trackEvent(CLICK_REVIEW_WRITING_BACK)
            showReviewCancelDialog()
        }

        binding.layoutParent.setOnClickListener {
            binding.etWriteYourReview.clearFocus()
            hideKeyboard(it)
        }

        binding.layoutScrollViewConstraint.setOnClickListener {
            binding.etWriteYourReview.clearFocus()
            hideKeyboard(it)
        }
    }

    private fun collectData() {
        viewModel.reviewSuccessState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    AmplitudeUtils.trackEvent(CLICK_REVIEW_WRITING_COMPLETE)
                    uiState.data.likeType?.let { likeType ->
                        AmplitudeUtils.trackEventWithMapProperties(
                            COMPLETE_REVIEW_WRITING,
                            mapOf(
                                OPTION to getStringByLikeType(likeType),
                                KEYWORD to getSelectedKeyWord(uiState.data.userKeyWordType),
                                TEXT to uiState.data.reviewText
                            )
                        )
                    }
                    showReviewSuccessDialog()
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
        viewModel.reviewCancelState.flowWithLifecycle(lifecycle).onEach { reviewCancelState ->
            if (reviewCancelState == false) moveToDetail()
        }.launchIn(lifecycleScope)
        viewModel.isLike.flowWithLifecycle(lifecycle).onEach {
            it?.let { likeType ->
                AmplitudeUtils.trackEventWithProperties(
                    CLICK_REVIEW_WRITING_OPTION,
                    OPTION,
                    getStringByLikeType(likeType)
                )
            }
        }.launchIn(lifecycleScope)
        viewModel.userKeyWordType.flowWithLifecycle(lifecycle).onEach { keywordType ->
            AmplitudeUtils.trackEventWithProperties(
                CLICK_RECOMMEND_KEYWORD,
                KEYWORD,
                getSelectedKeyWord(keywordType)
            )
        }.launchIn(lifecycleScope)
    }

    private fun showReviewSuccessDialog() {
        ReviewSuccessBottomDialogFragment(::moveToDetail).show(
            supportFragmentManager,
            REVIEW_SUCCESS_DIALOG
        )
    }

    private fun showReviewCancelDialog() {
        ReviewCancelBottomDialogFragment().show(supportFragmentManager, REVIEW_CANCEL_DIALOG)
    }

    private fun moveToDetail() {
        val intent = Intent(this, DetailActivity::class.java)
        viewModel.bakeryId.value.let { id ->
            intent.putExtra(BAKERY_ID, id)
        }
        startActivity(intent)
        finish()
    }

    private fun getStringByLikeType(likeType: LikeType): String {
        return when (likeType) {
            LikeType.LIKE -> LIKE
            LikeType.BAD -> BAD
        }
    }

    private fun getSelectedKeyWord(inputKeyWordType: Map<KeyWordType, Boolean>): List<String> {
        return inputKeyWordType.entries.filter { it.value }.map { getString(it.key.keywordNameRes) }
    }

    companion object {
        const val BAKERY_ID = "bakeryId"
        const val BAKERY_INFO = "bakeryInfo"
        const val REVIEW_SUCCESS_DIALOG = "reviewSuccessDialog"
        const val REVIEW_CANCEL_DIALOG = "reviewCancelDialog"
        const val LIKE = "좋았어요"
        const val BAD = "아쉬웠어요"
        const val CLICK_REVIEW_WRITING_OPTION = "click_reviewwriting_option"
        const val CLICK_RECOMMEND_KEYWORD = "click_recommend_keyword"
        const val CLICK_REVIEW_WRITING_TEXT = "click_reviewwriting_text"
        const val CLICK_REVIEW_WRITING_BACK = "click_reviewwriting_back"
        const val CLICK_REVIEW_WRITING_COMPLETE = "click_reviewwriting_complete"
        const val COMPLETE_REVIEW_WRITING = "complete_reviewwriting"
        const val OPTION = "option"
        const val KEYWORD = "keyword"
        const val TEXT = "text"
    }
}
