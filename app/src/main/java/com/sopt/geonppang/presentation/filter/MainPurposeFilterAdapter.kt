package com.sopt.geonppang.presentation.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.geonppang.databinding.ItemMainPurposeFilterButtonBinding
import com.sopt.geonppang.domain.model.Filter
import com.sopt.geonppang.util.ItemDiffCallback

class MainPurposeFilterAdapter(
    context: Context,
    private val viewModel: FilterViewModel
) : ListAdapter<Filter, RecyclerView.ViewHolder>(
    ItemDiffCallback<Filter>(
        onContentsTheSame = { old, new -> old == new },
        onItemsTheSame = { old, new -> old == new }
    )
) {
    private val inflater by lazy { LayoutInflater.from(context) }

    class MainPurposeFilterViewHolder(private val binding: ItemMainPurposeFilterButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(filter: Filter) {
            binding.filter = filter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMainPurposeFilterButtonBinding.inflate(inflater, parent, false)
        return MainPurposeFilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainPurposeFilterViewHolder -> {
                holder.onBind(currentList[position])
                holder.itemView.setOnClickListener { view ->
                    view.isSelected = !view.isSelected
                    if (viewModel.mainPurposeFilterSelectedItemIndex.value != position) {
                        val recyclerView = view.parent as RecyclerView
                        val selectedView =
                            viewModel.mainPurposeFilterSelectedItemIndex.value?.let { position ->
                                recyclerView.layoutManager?.findViewByPosition(position)
                            }
                        if (selectedView != null) {
                            selectedView.isSelected = !selectedView.isSelected
                        }
                        viewModel.setMainPurposeFilterSelectedItemIndex(holder.adapterPosition)
                    } else {
                        viewModel.setMainPurposeFilterSelectedItemIndex(-1)
                    }
                }
            }
        }
    }
}
