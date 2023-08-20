package com.sopt.geonppang.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.geonppang.databinding.LayoutSearchCountBinding

class SearchCountAdapter : RecyclerView.Adapter<SearchCountAdapter.SearchCountViewHolder>() {
    class SearchCountViewHolder(
        binding: LayoutSearchCountBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCountViewHolder {
        val binding =
            LayoutSearchCountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchCountViewHolder(binding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: SearchCountViewHolder, position: Int) {
        return
    }
}
