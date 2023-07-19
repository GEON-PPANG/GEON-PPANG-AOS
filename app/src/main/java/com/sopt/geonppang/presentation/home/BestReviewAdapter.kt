package com.sopt.geonppang.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.geonppang.databinding.ItemHomeBestReviewBinding
import com.sopt.geonppang.domain.model.BestReview
import com.sopt.geonppang.util.ItemDiffCallback

class BestReviewAdapter(
    private val moveToDetail: (Int) -> Unit,
) : ListAdapter<BestReview, BestReviewAdapter.ReviewViewHolder>(
    ItemDiffCallback<BestReview>(
        onItemsTheSame = { old, new -> old.bakeryId == new.bakeryId },
        onContentsTheSame = { old, new -> old == new }
    )
) {

    class ReviewViewHolder(
        private val binding: ItemHomeBestReviewBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            review: BestReview,
            moveToDetail: (Int) -> Unit,
        ) {
            binding.review = review
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                moveToDetail(review.bakeryId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding =
            ItemHomeBestReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.onBind(getItem(position), moveToDetail)
    }
}
