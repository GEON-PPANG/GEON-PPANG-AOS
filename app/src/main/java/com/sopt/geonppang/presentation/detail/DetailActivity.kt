package com.sopt.geonppang.presentation.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityDetailBinding
import com.sopt.geonppang.presentation.MainActivity
import com.sopt.geonppang.presentation.review.ReviewWritingActivity
import com.sopt.geonppang.util.ChipFactory
import com.sopt.geonppang.util.CustomSnackbar
import com.sopt.geonppang.util.UiState
import com.sopt.geonppang.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailActivity : BindingActivity<ActivityDetailBinding>(R.layout.activity_detail) {
    private val viewModel by viewModels<DetailViewModel>()

    lateinit var detailBakeryInfoAdapter: DetailBakeryInfoAdapter
    lateinit var detailMenuAdapter: DetailMenuAdapter
    lateinit var detailReviewDataAdapter: DetailReviewDataAdapter
    lateinit var detailReviewAdapter: DetailReviewAdapter
    lateinit var detailNoReviewAdapter: DetailNoReviewAdapter
    lateinit var concatAdapter: ConcatAdapter
    var bakeryId = -1

    private val String.toChip: Chip
        get() = ChipFactory.create(layoutInflater).also {
            it.text = this
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        bakeryId = intent.getIntExtra(BAKERY_ID, -1)

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        viewModel.fetchDetailBakeryInfo(bakeryId)
        viewModel.fetchDetailReview(bakeryId)

        detailBakeryInfoAdapter = DetailBakeryInfoAdapter()
        detailMenuAdapter = DetailMenuAdapter()
        detailReviewDataAdapter = DetailReviewDataAdapter()
        detailReviewAdapter = DetailReviewAdapter(::initChip)
        detailNoReviewAdapter = DetailNoReviewAdapter()

        concatAdapter = ConcatAdapter(
            detailBakeryInfoAdapter,
            detailMenuAdapter,
            detailReviewDataAdapter,
            detailReviewAdapter
        )
        binding.rvDetail.adapter = concatAdapter
    }

    private fun addListeners() {
        binding.ivBack.setOnClickListener {
            val previousActivityName = intent.getStringExtra(ACTIVITY_NAME)

            when (previousActivityName) {
                MAIN -> {
                    moveToMain()
                }
                else -> {
                    finish()
                }
            }
        }

        binding.layoutDetailBottomAppBarCreateReview.setOnClickListener {
            val intent = Intent(this, ReviewWritingActivity::class.java)
            intent.putExtra(BAKERY_ID, bakeryId)
            intent.putExtra(BAKERY_INFO, viewModel.getBakeryInfo())
            startActivity(intent)
            finish()
        }
    }

    private fun collectData() {
        viewModel.bakeryListState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    detailBakeryInfoAdapter.setBakeryInfo(it.data)
                    detailMenuAdapter.submitList(it.data.menuList)

                    binding.ivDetailBottomAppBarBookmark.setOnClickListener {
                        if (viewModel.bookMarkState.value?.isBookMarked == false) {
                            CustomSnackbar.makeSnackbar(
                                binding.root,
                                getString(R.string.snackbar_save)
                            )
                        }
                        viewModel.bookMarkState.value?.isBookMarked?.let { isBookMarked ->
                            viewModel.doBookMark(bakeryId, !isBookMarked)
                        }
                    }
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)

        viewModel.reviewListState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    detailReviewDataAdapter.setReviewData(it.data)
                    detailReviewAdapter.submitList(it.data.detailReviewList)

                    if (it.data.totalReviewCount == 0) {
                        concatAdapter = ConcatAdapter(
                            detailBakeryInfoAdapter,
                            detailMenuAdapter,
                            detailReviewDataAdapter,
                            detailNoReviewAdapter
                        )
                    }

                    binding.rvDetail.adapter = concatAdapter
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)

        viewModel.bookMarkState.flowWithLifecycle(lifecycle).onEach {
            viewModel.fetchDetailBakeryInfo(bakeryId)
        }.launchIn(lifecycleScope)
    }

    private fun moveToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initChip(chipGroup: ChipGroup, position: Int) {
        viewModel.reviewList.value?.get(position)?.recommendKeywordList?.let { recommendKeywordList ->
            for (recommendKeyword in recommendKeywordList) {
                chipGroup.addView(
                    recommendKeyword.recommendKeywordName.toChip
                )
            }
        }
    }

    companion object {
        const val BAKERY_ID = "bakeryId"
        const val BAKERY_INFO = "bakeryInfo"
        const val ACTIVITY_NAME = "activityName"
        const val MAIN = "mainActivity"
    }
}
