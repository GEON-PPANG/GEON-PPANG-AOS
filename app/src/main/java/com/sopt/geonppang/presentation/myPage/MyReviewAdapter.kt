package com.sopt.geonppang.presentation.myPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ItemMyReviewBinding
import com.sopt.geonppang.domain.model.MyReview
import com.sopt.geonppang.presentation.model.BakeryReviewWritingInfo
import com.sopt.geonppang.presentation.model.MyReviewBakeryInfo
import com.sopt.geonppang.util.ItemDiffCallback
import com.sopt.geonppang.util.extension.loadingImage

class MyReviewAdapter(
    private val moveToReviewDetail: (Int, MyReviewBakeryInfo) -> Unit,
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
        ) {
            binding.review = review
            binding.root.setOnClickListener {
                moveToReviewDetail(
                    review.reviewId,
                    MyReviewBakeryInfo(
                        review.date,
                        review.bakery.bakeryName,
                        review.bakery.bakeryPicture,
                        review.bakery.firstNearStation,
                        review.bakery.secondNearStation,
                        BakeryReviewWritingInfo.BreadType(
                            review.bakery.breadType.isGlutenFree,
                            review.bakery.breadType.isVegan,
                            review.bakery.breadType.isNutFree,
                            review.bakery.breadType.isSugarFree
                        )
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
        holder.onBind(getItem(position), moveToReviewDetail)
    }
}
