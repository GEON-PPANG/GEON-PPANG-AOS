package com.sopt.geonppang.util.extension

/*
    author: dana
    description: chip을 inflate 하는 것과 관련이 있는 확장함수들 모음
 */

import android.view.LayoutInflater
import androidx.core.content.ContextCompat.getString
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.sopt.geonppang.R
import com.sopt.geonppang.presentation.type.BreadFilterType
import com.sopt.geonppang.util.ChipFactory

fun String.toBreadTypeGrayChip(layoutInflater: LayoutInflater): Chip {
    return ChipFactory.create(layoutInflater, R.layout.view_bread_type_chip_gray)
}

fun String.toBreadTypePointM1Chip(layoutInflater: LayoutInflater): Chip {
    return ChipFactory.create(layoutInflater, R.layout.view_bread_type_chip_point_m1)
}

fun String.toBreadTypePointM2Chip(layoutInflater: LayoutInflater): Chip {
    return ChipFactory.create(layoutInflater, R.layout.view_bread_type_chip_point_m2)
}

fun String.toReviewRecommendedKeyWordChip(layoutInflater: LayoutInflater): Chip {
    return ChipFactory.create(layoutInflater, R.layout.view_chip_review_recommended_keyword).also {
        it.text = this
    }
}

fun ChipGroup.breadTypeListToChips(
    breadTypeList: List<BreadFilterType>,
    toChip: String.() -> Chip
) {

    for (breadType in breadTypeList) {
        val breadTypeTitle = getString(context, breadType.titleRes)

        val chip = breadTypeTitle.toChip().apply {
            text = breadTypeTitle
        }

        this.addView(chip)
    }
}
