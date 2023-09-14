package com.sopt.geonppang.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.geonppang.databinding.ItemDetailBakeryInfoBinding
import com.sopt.geonppang.domain.model.BakeryInfo

class DetailBakeryInfoAdapter(
    private val moveToWebPage: (String) -> Unit
) : RecyclerView.Adapter<DetailBakeryInfoAdapter.DetailBakeryInfoViewHolder>() {
    private val bakeryInfoList: MutableList<BakeryInfo> = mutableListOf()

    class DetailBakeryInfoViewHolder(val binding: ItemDetailBakeryInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            bakeryInfo: BakeryInfo,
            moveToWebPage: (String) -> Unit
        ) {
            with(binding) {
                binding.bakeryInfo = bakeryInfo

                tvItemDetailBakeryInfoHomepage.setOnClickListener {
                    moveToWebPage(bakeryInfo.homepageUrl)
                }

                tvItemDetailBakeryInfoInstagram.setOnClickListener {
                    moveToWebPage(bakeryInfo.instagramUrl)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailBakeryInfoViewHolder {
        val binding =
            ItemDetailBakeryInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailBakeryInfoViewHolder(binding)
    }

    override fun getItemCount(): Int = bakeryInfoList.size

    override fun onBindViewHolder(holder: DetailBakeryInfoViewHolder, position: Int) {
        holder.onBind(bakeryInfoList[position], moveToWebPage)
    }

    fun setBakeryInfo(bakeryInfo: BakeryInfo) {
        bakeryInfoList.clear()
        bakeryInfoList.add(bakeryInfo)
        notifyDataSetChanged()
    }
}
