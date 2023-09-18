package com.sopt.geonppang.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ItemBakeryBinding
import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.util.ItemDiffCallback
import com.sopt.geonppang.util.extension.loadingImage

class BakeryAdapter(
    private val moveToDetail: (Int) -> Unit,
) : ListAdapter<Bakery, BakeryAdapter.BakeryViewHolder>(
    ItemDiffCallback<Bakery>(
        onItemsTheSame = { old, new -> old.bakeryId == new.bakeryId },
        onContentsTheSame = { old, new -> old == new }
    )
) {

    class BakeryViewHolder(
        private val binding: ItemBakeryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            bakery: Bakery,
            moveToDetail: (Int) -> Unit,
        ) {
            binding.bakery = bakery
            binding.executePendingBindings()

            binding.root.setOnClickListener {
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
        holder.onBind(getItem(position), moveToDetail)
    }
}
