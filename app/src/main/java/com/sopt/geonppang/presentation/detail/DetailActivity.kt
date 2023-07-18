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

    private val String.toChip: Chip
        get() = ChipFactory.create(layoutInflater).also {
            it.text = this
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListeners()
        collectData()
        initLayout()
    }

    private fun initLayout() {
        detailBakeryInfoAdapter = DetailBakeryInfoAdapter()
        detailMenuAdapter = DetailMenuAdapter()
        val detailReviewDataAdapter = DetailReviewDataAdapter()
        val detailReviewAdapter = DetailReviewAdapter(::initChip)
        val detailNoReviewAdapter = DetailNoReviewAdapter()
        var concatAdapter = ConcatAdapter()

        if (viewModel.mockDetailReviewData.reviewCount == 0) {
            concatAdapter = ConcatAdapter(
                detailBakeryInfoAdapter,
                detailMenuAdapter,
                detailReviewDataAdapter,
                detailNoReviewAdapter
            )
        } else {
            concatAdapter = ConcatAdapter(
                detailBakeryInfoAdapter,
                detailMenuAdapter,
                detailReviewDataAdapter,
                detailReviewAdapter
            )
        }

        detailMenuAdapter.submitList(viewModel.mockMenuList)
        detailReviewDataAdapter.setReviewData(viewModel.mockDetailReviewData)
        detailReviewAdapter.submitList(viewModel.mockDetailReviewData.detailReviewList)

        binding.rvDetail.adapter = concatAdapter
    }

    private fun addListeners() {
        binding.ivDetailBottomAppBarBookmark.setOnClickListener {
            CustomSnackbar.makeSnackbar(binding.root, getString(R.string.snackbar_save))
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.layoutDetailBottomAppBarCreateReview.setOnClickListener {
            startActivity(Intent(this, ReviewWritingActivity::class.java))
        }
    }

    private fun collectData() {
        viewModel.bakeryListState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> {
                    detailBakeryInfoAdapter.setBakeryInfo(it.data)
                    detailMenuAdapter.submitList(it.data.menuList)
                    binding.bakeryInfo = it.data
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun initChip(chipGroup: ChipGroup, position: Int) {
        for (recommendKeyword in viewModel.mockDetailReviewData.detailReviewList[position].recommendKeywordList) {
            chipGroup.addView(
                recommendKeyword.recommendKeywordName.toChip
            )
        }
    }
}
