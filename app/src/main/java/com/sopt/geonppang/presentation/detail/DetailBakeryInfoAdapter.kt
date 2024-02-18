package com.sopt.geonppang.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.sopt.geonppang.databinding.ItemDetailBakeryInfoBinding
import com.sopt.geonppang.domain.model.BakeryInfo
import com.sopt.geonppang.util.AmplitudeUtils

class DetailBakeryInfoAdapter(
    private val moveToWebPage: (String) -> Unit,
    private val initBreadTypeChips: (ChipGroup) -> Unit,
) : RecyclerView.Adapter<DetailBakeryInfoAdapter.DetailBakeryInfoViewHolder>() {
    private val bakeryInfoList: MutableList<BakeryInfo> = mutableListOf()

    class DetailBakeryInfoViewHolder(val binding: ItemDetailBakeryInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            bakeryInfo: BakeryInfo,
            moveToWebPage: (String) -> Unit,
            initBreadTypeChips: (ChipGroup) -> Unit,
        ) {
            with(binding) {
                binding.bakeryInfo = bakeryInfo

                initBreadTypeChips(binding.chipGroupItemDetailBakeryInfoBreadType)

                tvItemDetailBakeryInfoHomepage.setOnClickListener {
                    AmplitudeUtils.trackEvent(CLICK_WEBSITE)
                    moveToWebPage(bakeryInfo.homepageUrl)
                }

                tvItemDetailBakeryInfoInstagram.setOnClickListener {
                    AmplitudeUtils.trackEvent(CLICK_INSTAGRAM)
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
        holder.onBind(bakeryInfoList[position], moveToWebPage, initBreadTypeChips)
    }

    fun setBakeryInfo(bakeryInfo: BakeryInfo) {
        bakeryInfoList.clear()
        bakeryInfoList.add(bakeryInfo)
        notifyDataSetChanged()
    }

    companion object {
        const val CLICK_WEBSITE = "click_website"
        const val CLICK_INSTAGRAM = "click_instagram"
    }
}
