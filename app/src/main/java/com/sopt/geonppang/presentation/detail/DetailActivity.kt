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
        val detailBakeryInfoAdapter = DetailBakeryInfoAdapter(this)
        val detailMenuAdapter = DetailMenuAdapter(this)
        val detailReviewDataAdapter = DetailReviewDataAdapter(this)
        val detailReviewAdapter = DetailReviewAdapter(::initChip, this)
        val concatAdapter = ConcatAdapter(
            detailBakeryInfoAdapter,
            detailMenuAdapter,
            detailReviewDataAdapter,
            detailReviewAdapter
        )

        detailBakeryInfoAdapter.submitList(viewModel.mockBakeryInfo)
        detailMenuAdapter.submitList(viewModel.mockMenuList)
        detailReviewDataAdapter.submitList(viewModel.mockReviewData)
        detailReviewAdapter.submitList(viewModel.mockReview)

        binding.rvDetail.adapter = concatAdapter
        binding.bakeryInfo = viewModel.mockBakeryInfo[0]
    }

    private fun addListeners() {
        binding.ivDetailBottomAppBarBookmark.setOnClickListener {
            CustomSnackbar.makeSnackbar(binding.root, getString(R.string.snackbar_save), 138)
        }
    }

    private fun initChip(chipGroup: ChipGroup, position: Int) {
        for (recommendKeyword in viewModel.mockReview[position].recommendKeywordList) {
            chipGroup.addView(
                recommendKeyword.recommendKeywordName.toChip
            )
        }
    }
}
