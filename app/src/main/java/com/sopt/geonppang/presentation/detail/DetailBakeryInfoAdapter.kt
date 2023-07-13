package com.sopt.geonppang.presentation.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.geonppang.databinding.ItemDetailBakeryInfoBinding
import com.sopt.geonppang.domain.model.BakeryInfo
import com.sopt.geonppang.util.ItemDiffCallback

class DetailBakeryInfoAdapter(context: Context) : ListAdapter<BakeryInfo, RecyclerView.ViewHolder>(
    ItemDiffCallback<BakeryInfo>(
        onContentsTheSame = { old, new -> old == new },
        onItemsTheSame = { old, new -> old.phoneNumber == new.phoneNumber }
    )
) {
    private val inflater by lazy { LayoutInflater.from(context) }

    class DetailBakeryInfoViewHolder(private val binding: ItemDetailBakeryInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(bakeryInfo: BakeryInfo) {
            binding.bakeryInfo = bakeryInfo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemDetailBakeryInfoBinding.inflate(inflater, parent, false)
        return DetailBakeryInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DetailBakeryInfoViewHolder -> {
                holder.onBind((currentList[position]))
            }
        }
    }
}
