package com.sopt.geonppang.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ItemBakeryBinding
import com.sopt.geonppang.domain.model.BakeryInformation
import com.sopt.geonppang.util.ItemDiffCallback
import com.sopt.geonppang.util.extension.loadingImage
import com.sopt.geonppang.util.extension.setOnSingleClickListener

class BakeryAdapter(
    private val moveToDetail: (Int) -> Unit,
    private val initBreadTypeChips: (ChipGroup, Int) -> Unit,
) : ListAdapter<BakeryInformation, BakeryAdapter.BakeryViewHolder>(
    ItemDiffCallback<BakeryInformation>(
        onItemsTheSame = { old, new -> old.bakeryId == new.bakeryId },
        onContentsTheSame = { old, new -> old == new }
    )
) {

    class BakeryViewHolder(
        private val binding: ItemBakeryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            bakery: BakeryInformation,
            moveToDetail: (Int) -> Unit,
            initBreadTypeChips: (ChipGroup, Int) -> Unit,
            position: Int
        ) {
            binding.bakery = bakery
            binding.executePendingBindings()

            // TODO: dana 다른 방식이 있는지 고민, 매 바인딩마다 removeAllViews 해야하는가 ?
            with(binding.cgBakeryBreadTypes) {
                this.removeAllViews()
                initBreadTypeChips(this, position)
            }

            binding.root.setOnSingleClickListener {
                moveToDetail(bakery.bakeryId)
            }

            binding.root.context.loadingImage(
                imageUrl = bakery.bakeryPicture,
                imageView = binding.ivBakery,
                loadingImage = R.drawable.img_bakery_image_loading
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BakeryViewHolder {
        val binding =
            ItemBakeryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BakeryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BakeryViewHolder, position: Int) {
        holder.onBind(getItem(position), moveToDetail, initBreadTypeChips, position)
    }
}
