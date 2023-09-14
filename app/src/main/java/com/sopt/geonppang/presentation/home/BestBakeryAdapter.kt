package com.sopt.geonppang.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.geonppang.databinding.ItemHomeBestBakeryBinding
import com.sopt.geonppang.domain.model.BestBakery
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.ItemDiffCallback
import com.sopt.geonppang.util.setVisibility

class BestBakeryAdapter(
    private val moveToDetail: (Int) -> Unit,
) : ListAdapter<BestBakery, BestBakeryAdapter.BakeryViewHolder>(
    ItemDiffCallback<BestBakery>(
        onItemsTheSame = { old, new -> old.bakeryId == new.bakeryId },
        onContentsTheSame = { old, new -> old == new }
    )
) {

    class BakeryViewHolder(
        private val binding: ItemHomeBestBakeryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            bakery: BestBakery,
            moveToDetail: (Int) -> Unit,
        ) {
            binding.bakery = bakery
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                AmplitudeUtils.trackEvent(CLICK_RECOMMEND_STORE)
                moveToDetail(bakery.bakeryId)
            }

            binding.chipHomeSecondNearStation.setVisibility(bakery.secondNearStation != "")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BakeryViewHolder {
        val binding =
            ItemHomeBestBakeryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BakeryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BakeryViewHolder, position: Int) {
        holder.onBind(getItem(position), moveToDetail)
    }

    companion object {
        const val CLICK_RECOMMEND_STORE = "click_recommend_store"
    }
}
