package com.sopt.geonppang.presentation.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.geonppang.databinding.ItemMyReviewBinding
import com.sopt.geonppang.domain.model.Review
import com.sopt.geonppang.util.ItemDiffCallback

class MyReviewAdapter :
    androidx.recyclerview.widget.ListAdapter<Review, MyReviewAdapter.MyReviewViewHolder>(
        ItemDiffCallback<Review>(
            onItemsTheSame = { old, new -> old.reviewId == new.reviewId },
            onContentsTheSame = { old, new -> old == new }
        )
    ) {

    class MyReviewViewHolder(
        private val binding: ItemMyReviewBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(review: Review) {
            binding.review = review
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewViewHolder {
        val binding =
            ItemMyReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyReviewViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
