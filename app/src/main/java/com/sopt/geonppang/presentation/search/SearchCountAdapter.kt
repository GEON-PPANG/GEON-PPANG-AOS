package com.sopt.geonppang.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.geonppang.databinding.LayoutSearchCountBinding
import com.sopt.geonppang.domain.model.Search

class SearchCountAdapter : RecyclerView.Adapter<SearchCountAdapter.SearchCountViewHolder>() {
    private var searchData: Search? = null

    class SearchCountViewHolder(
        val binding: LayoutSearchCountBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCountViewHolder {
        val binding =
            LayoutSearchCountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchCountViewHolder(binding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: SearchCountViewHolder, position: Int) {
        holder.binding.search = searchData
    }

    fun setSearchData(search: Search) {
        this.searchData = search
        notifyDataSetChanged()
    }
}
