package com.sopt.geonppang.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.geonppang.databinding.ItemDetailMenuBinding
import com.sopt.geonppang.domain.model.Menu
import com.sopt.geonppang.util.ItemDiffCallback

class DetailMenuAdapter() :
    ListAdapter<Menu, DetailMenuAdapter.DetailMenuViewHolder>(
        ItemDiffCallback<Menu>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.menuId == new.menuId }
        )
    ) {
    class DetailMenuViewHolder(private val binding: ItemDetailMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(menu: Menu) {
            binding.menu = menu
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailMenuViewHolder {
        val binding =
            ItemDetailMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailMenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailMenuViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }
}
