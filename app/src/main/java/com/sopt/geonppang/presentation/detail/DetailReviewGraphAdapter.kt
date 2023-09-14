package com.sopt.geonppang.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.geonppang.databinding.ItemDetailReviewGraphBinding
import com.sopt.geonppang.domain.model.ReviewData

class DetailReviewGraphAdapter :
    RecyclerView.Adapter<DetailReviewGraphAdapter.DetailReviewDataViewHolder>() {
    private var reviewData: ReviewData? = null

    class DetailReviewDataViewHolder(val binding: ItemDetailReviewGraphBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailReviewDataViewHolder {
        val binding =
            ItemDetailReviewGraphBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailReviewDataViewHolder(binding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: DetailReviewDataViewHolder, position: Int) {
        holder.binding.reviewData = reviewData
    }

    fun setReviewData(reviewData: ReviewData) {
        this.reviewData = reviewData
        notifyDataSetChanged()
    }
}
