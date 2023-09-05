package com.sopt.geonppang.presentation.filterSetting

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FilterViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val filterFragments =
        listOf(MainPurposeFilterFragment(), BreadTypeFilterFragment(), NutrientTypeFilterFragment())

    override fun getItemCount(): Int = filterFragments.size

    override fun createFragment(position: Int): Fragment {
        return filterFragments[position]
    }
}
