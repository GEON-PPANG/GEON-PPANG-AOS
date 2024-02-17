package com.sopt.geonppang.presentation.myPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ItemMyReviewBinding
import com.sopt.geonppang.domain.model.MyReview
import com.sopt.geonppang.presentation.model.MyReviewBakeryInfo
import com.sopt.geonppang.util.ItemDiffCallback
import com.sopt.geonppang.util.extension.loadingImage
import com.sopt.geonppang.util.extension.setOnSingleClickListener

class MyReviewAdapter(
    private val moveToReviewDetail: (Int, MyReviewBakeryInfo) -> Unit,
    private val initBreadTypeChips: (ChipGroup, Int) -> Unit,
) :
    ListAdapter<MyReview, MyReviewAdapter.MyReviewViewHolder>(
        ItemDiffCallback<MyReview>(
            onItemsTheSame = { old, new -> old.reviewId == new.reviewId },
            onContentsTheSame = { old, new -> old == new }
        )
    ) {

    class MyReviewViewHolder(
        private val binding: ItemMyReviewBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            review: MyReview,
            moveToReviewDetail: (Int, MyReviewBakeryInfo) -> Unit,
            initBreadTypeChips: (ChipGroup, Int) -> Unit,
            position: Int
        ) {

            binding.review = review

            // TODO: dana 다른 방식이 있는지 고민, 매 바인딩마다 removeAllViews 해야하는가 ?
            with(binding.cgBakeryBreadTypes) {
                this.removeAllViews()
                initBreadTypeChips(this, position)
            }

            binding.root.setOnSingleClickListener {
                moveToReviewDetail(
                    review.reviewId,
                    MyReviewBakeryInfo(
                        review.date,
                        review.bakery.bakeryName,
                        review.bakery.bakeryPicture,
                        review.bakery.firstNearStation,
                        review.bakery.secondNearStation,
                        review.bakery.breadTypeList
                    )
                )
            }
            binding.executePendingBindings()

            binding.root.context.loadingImage(
                imageUrl = review.bakery.bakeryPicture,
                imageView = binding.ivBakery,
                loadingImage = R.drawable.img_bakery_image_loading
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewViewHolder {
        val binding =
            ItemMyReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyReviewViewHolder, position: Int) {
        holder.onBind(getItem(position), moveToReviewDetail, initBreadTypeChips, position)
    }
}
