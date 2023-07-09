package com.sopt.geonppang.presentation.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.geonppang.databinding.ItemNutrientTypeFilterButtonBinding
import com.sopt.geonppang.domain.model.NutrientFilter
import com.sopt.geonppang.util.ItemDiffCallback

class NutrientTypeFilterAdapter(context: Context, private val viewModel: FilterViewModel) :
    ListAdapter<NutrientFilter, RecyclerView.ViewHolder>(
        ItemDiffCallback<NutrientFilter>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old == new }
        )
    ) {
    private val inflater by lazy { LayoutInflater.from(context) }

    class NutrientTypeFilterViewHolder(private val binding: ItemNutrientTypeFilterButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(nutrientFilter: NutrientFilter) {
            binding.filter = nutrientFilter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemNutrientTypeFilterButtonBinding.inflate(inflater, parent, false)
        return NutrientTypeFilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NutrientTypeFilterViewHolder -> {
                holder.onBind(currentList[position])
                holder.itemView.setOnClickListener { view ->
                    viewModel.setNutrientTypeFilterSelectedItemList(position)
                    view.isSelected = !view.isSelected
                }
            }
        }
    }
}
