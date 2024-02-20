package com.sopt.geonppang.presentation.bakeryList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ItemBakeryBinding
import com.sopt.geonppang.domain.model.BakeryInformation
import com.sopt.geonppang.presentation.type.BreadFilterType
import com.sopt.geonppang.util.ItemDiffCallback
import com.sopt.geonppang.util.extension.loadingImage
import com.sopt.geonppang.util.extension.setOnSingleClickListener

class BakeryListPagingDataAdapter(
    private val moveToDetail: (Int) -> Unit,
    private val initBreadTypeChips: (ChipGroup, List<BreadFilterType>) -> Unit
) : PagingDataAdapter<BakeryInformation, BakeryListPagingDataAdapter.BakeryViewHolder>(
    ItemDiffCallback(
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
            initBreadTypeChips: (ChipGroup, List<BreadFilterType>) -> Unit,
        ) {
            binding.bakery = bakery
            binding.executePendingBindings()

            with(binding.cgBakeryBreadTypes) {
                this.removeAllViews()
                initBreadTypeChips(this, bakery.breadTypeList)
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
        getItem(position)?.let { holder.onBind(it, moveToDetail, initBreadTypeChips) }
    }
}
