package com.sopt.geonppang.presentation.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ActivityDetailBinding
import com.sopt.geonppang.util.ChipFactory
import com.sopt.geonppang.util.CustomSnackbar
import com.sopt.geonppang.util.binding.BindingActivity

class DetailActivity : BindingActivity<ActivityDetailBinding>(R.layout.activity_detail) {
    private val viewModel by viewModels<DetailViewModel>()
    private val String.toChip: Chip
        get() = ChipFactory.create(layoutInflater).also {
            it.text = this
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        val detailBakeryInfoAdapter = DetailBakeryInfoAdapter()
        val detailMenuAdapter = DetailMenuAdapter()
        val detailReviewDataAdapter = DetailReviewDataAdapter()
        val detailReviewAdapter = DetailReviewAdapter(::initChip)
        val detailNoReviewAdapter = DetailNoReviewAdapter()
        var concatAdapter = ConcatAdapter()

        if (viewModel.mockReviewData.reviewCount == 0) {
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

        detailBakeryInfoAdapter.setBakeryInfo(viewModel.mockBakeryInfo)
        detailMenuAdapter.submitList(viewModel.mockMenuList)
        detailReviewDataAdapter.setReviewData(viewModel.mockReviewData)
        detailReviewAdapter.submitList(viewModel.mockReviewData.reviewList)

        binding.rvDetail.adapter = concatAdapter
        binding.bakeryInfo = viewModel.mockBakeryInfo
    }

    private fun addListeners() {
        binding.ivDetailBottomAppBarBookmark.setOnClickListener {
            CustomSnackbar.makeSnackbar(binding.root, getString(R.string.snackbar_save))
        }
    }

    private fun initChip(chipGroup: ChipGroup, position: Int) {
        for (recommendKeyword in viewModel.mockReviewData.reviewList[position].recommendKeywordList) {
            chipGroup.addView(
                recommendKeyword.recommendKeywordName.toChip
            )
        }
    }
}
