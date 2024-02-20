package com.sopt.geonppang.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.sopt.geonppang.data.datasource.local.GPDataSource
import com.sopt.geonppang.databinding.ItemDetailReviewBinding
import com.sopt.geonppang.domain.model.DetailReview
import com.sopt.geonppang.presentation.type.UserRoleType
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.ItemDiffCallback
import com.sopt.geonppang.util.extension.setOnSingleClickListener

class DetailReviewAdapter(
    private val initChip: (ChipGroup, Int) -> Unit,
    private val moveToReport: (Int) -> Unit,
    private val showLoginNeedDialogReportReview: () -> Unit
) :
    ListAdapter<DetailReview, DetailReviewAdapter.DetailReviewViewHolder>(
        ItemDiffCallback<DetailReview>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.reviewId == new.reviewId }
        )
    ) {
    class DetailReviewViewHolder(private val binding: ItemDetailReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var gpDataSource: GPDataSource
        fun onBind(
            detailReview: DetailReview,
            initChip: (ChipGroup, Int) -> Unit,
            moveToReport: (Int) -> Unit,
            showLoginNeedDialogReportReview: () -> Unit,
            position: Int
        ) {
            binding.review = detailReview

            with(binding.chipGroupItemDetailReview) {
                this.removeAllViews()
                initChip(this, position)
            }

            binding.tvItemDetailReviewReport.setOnSingleClickListener {
                AmplitudeUtils.trackEvent(START_REVIEW_REPORT)
                gpDataSource = GPDataSource(it.context)
                if (gpDataSource.userRoleType == UserRoleType.NONE_MEMBER.name) {
                    showLoginNeedDialogReportReview()
                } else
                    moveToReport(detailReview.reviewId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailReviewViewHolder {
        val binding =
            ItemDetailReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailReviewViewHolder, position: Int) {
        holder.onBind(
            getItem(position),
            initChip,
            moveToReport,
            showLoginNeedDialogReportReview,
            position
        )
    }

    companion object {
        const val START_REVIEW_REPORT = "start_reviewreport"
    }
}
