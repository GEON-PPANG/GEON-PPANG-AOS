package com.sopt.geonppang.presentation.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.geonppang.databinding.ItemDetailNoReviewBinding
import com.sopt.geonppang.domain.model.ReviewData
import com.sopt.geonppang.util.ItemDiffCallback

class DetailNoReviewAdapter(context: Context) :
    ListAdapter<ReviewData, DetailNoReviewAdapter.DetailNoReviewDataViewHolder>(
        ItemDiffCallback<ReviewData>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old == new }
        )
    ) {
    private val inflater by lazy { LayoutInflater.from(context) }

    class DetailNoReviewDataViewHolder(private val binding: ItemDetailNoReviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailNoReviewDataViewHolder {
        val binding = ItemDetailNoReviewBinding.inflate(inflater, parent, false)
        return DetailNoReviewDataViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: DetailNoReviewDataViewHolder,
        position: Int
    ) {
        // Bind 할 내용 없음
    }

    override fun getItemCount(): Int {
        return 1
    }
}
