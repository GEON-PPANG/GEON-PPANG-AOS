package com.sopt.geonppang.presentation.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.sopt.geonppang.databinding.ItemDetailReviewBinding
import com.sopt.geonppang.domain.model.Review
import com.sopt.geonppang.util.ItemDiffCallback

class DetailReviewAdapter(
    private val initChip: (ChipGroup, Int) -> Unit,
    context: Context
) :
    ListAdapter<Review, DetailReviewAdapter.DetailReviewViewHolder>(
        ItemDiffCallback<Review>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old == new }
        )
    ) {
    private val inflater by lazy { LayoutInflater.from(context) }

    class DetailReviewViewHolder(private val binding: ItemDetailReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            review: Review,
            initChip: (ChipGroup, Int) -> Unit,
            position: Int
        ) {
            binding.review = review

            with(binding.chipGroupItemDetailReview) {
                this.removeAllViews()
                initChip(this, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailReviewViewHolder {
        val binding = ItemDetailReviewBinding.inflate(inflater, parent, false)
        return DetailReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailReviewViewHolder, position: Int) {
        holder.onBind(getItem(position), initChip, position)
    }
}
