package com.sopt.geonppang.presentation.bakeryList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.geonppang.databinding.ItemBakeryBinding
import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.util.setVisibility

class BakeryListAdapter(
    private val moveToDetail: (Int) -> Unit
) : RecyclerView.Adapter<BakeryListAdapter.BakeryViewHolder>() {
    private val bakeryList: MutableList<Bakery> = mutableListOf()

    class BakeryViewHolder(
        private val binding: ItemBakeryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            bakery: Bakery,
            moveToDetail: (Int) -> Unit
        ) {
            binding.bakery = bakery
            binding.root.setOnClickListener {
                moveToDetail(bakery.bakeryId)
            }
            binding.chipStation2.setVisibility(bakery.secondNearStation != "")
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BakeryViewHolder {
        val binding =
            ItemBakeryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BakeryViewHolder(binding)
    }

    override fun getItemCount() = bakeryList.size

    override fun onBindViewHolder(holder: BakeryViewHolder, position: Int) {
        holder.onBind(bakeryList[position], moveToDetail)
    }

    fun setGoalList(bakeries: MutableList<Bakery>) {
        bakeryList.clear()
        bakeryList.addAll(bakeries)
        notifyDataSetChanged()
    }
}
