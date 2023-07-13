package com.sopt.geonppang.presentation.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.geonppang.databinding.ItemDetailReviewGraphBinding
import com.sopt.geonppang.domain.model.ReviewData
import com.sopt.geonppang.util.ItemDiffCallback

class DetailReviewDataAdapter(context: Context) :
    ListAdapter<ReviewData, DetailReviewDataAdapter.DetailReviewDataViewHolder>(
        ItemDiffCallback<ReviewData>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old == new }
        )
    ) {
    private val inflater by lazy { LayoutInflater.from(context) }

    class DetailReviewDataViewHolder(private val binding: ItemDetailReviewGraphBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(reviewData: ReviewData) {
            binding.reviewData = reviewData
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailReviewDataViewHolder {
        val binding = ItemDetailReviewGraphBinding.inflate(inflater, parent, false)
        return DetailReviewDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailReviewDataViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }
}
