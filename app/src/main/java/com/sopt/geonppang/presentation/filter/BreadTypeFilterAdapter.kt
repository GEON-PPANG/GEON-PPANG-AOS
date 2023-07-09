package com.sopt.geonppang.presentation.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.geonppang.databinding.ItemBreadTypeFilterButtonBinding
import com.sopt.geonppang.domain.model.Filter
import com.sopt.geonppang.util.ItemDiffCallback

class BreadTypeFilterAdapter(context: Context, private val viewModel: FilterViewModel) :
    ListAdapter<Filter, RecyclerView.ViewHolder>(
        ItemDiffCallback<Filter>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old == new }
        )
    ) {
    private val inflater by lazy { LayoutInflater.from(context) }

    class BreadTypeFilterViewHolder(private val binding: ItemBreadTypeFilterButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(filter: Filter) {
            binding.filter = filter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBreadTypeFilterButtonBinding.inflate(inflater, parent, false)
        return BreadTypeFilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BreadTypeFilterViewHolder -> {
                holder.onBind(currentList[position])
                holder.itemView.setOnClickListener { view ->
                    viewModel.setBreadTypeFilterSelectedItemList(position)
                    view.isSelected = !view.isSelected
                }
            }
        }
    }
}
