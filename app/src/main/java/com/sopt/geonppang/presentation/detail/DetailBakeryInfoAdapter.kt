package com.sopt.geonppang.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.geonppang.R
import com.sopt.geonppang.databinding.ItemDetailBakeryInfoBinding
import com.sopt.geonppang.domain.model.BakeryInfo
import com.sopt.geonppang.util.CustomSnackbar

class DetailBakeryInfoAdapter :
    RecyclerView.Adapter<DetailBakeryInfoAdapter.DetailBakeryInfoViewHolder>() {
    private var bakeryInfo: BakeryInfo? = null

    class DetailBakeryInfoViewHolder(val binding: ItemDetailBakeryInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            with(binding) {
                ivItemDetailBakeryInfoBookmark.setOnClickListener {
                    CustomSnackbar.makeSnackbar(
                        it,
                        it.context.getString(R.string.snackbar_save)
                    )
                }

                ivItemDetailBakeryInfoCopy.setOnClickListener {
                    CustomSnackbar.makeSnackbar(
                        it,
                        it.context.getString(R.string.snackbar_copy)
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailBakeryInfoViewHolder {
        val binding =
            ItemDetailBakeryInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailBakeryInfoViewHolder(binding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: DetailBakeryInfoViewHolder, position: Int) {
        holder.binding.bakeryInfo = bakeryInfo
        holder.onBind()
    }

    fun setBakeryInfo(bakeryInfo: BakeryInfo) {
        this.bakeryInfo = bakeryInfo
        notifyDataSetChanged()
    }
}
