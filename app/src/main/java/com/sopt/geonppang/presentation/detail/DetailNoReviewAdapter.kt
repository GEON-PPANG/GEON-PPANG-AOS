package com.sopt.geonppang.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.geonppang.databinding.ItemDetailNoReviewBinding

class DetailNoReviewAdapter() :
    RecyclerView.Adapter<DetailNoReviewAdapter.DetailNoReviewDataViewHolder>() {

    class DetailNoReviewDataViewHolder(private val binding: ItemDetailNoReviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailNoReviewDataViewHolder {
        val binding =
            ItemDetailNoReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailNoReviewDataViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: DetailNoReviewDataViewHolder,
        position: Int
    ) {
        // Bind 할 내용 없음
    }

    override fun getItemCount(): Int = 1
}
