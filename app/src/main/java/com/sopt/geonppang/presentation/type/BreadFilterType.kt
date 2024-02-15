package com.sopt.geonppang.presentation.type

import androidx.annotation.StringRes
import com.sopt.geonppang.R

enum class BreadFilterType(
    val id: Int,
    @StringRes val titleRes: Int,
    @StringRes val desRes: Int,
) {
    GLUTENFREE(
        id = 1,
        titleRes = R.string.bread_type_gluten_free_title,
        desRes = R.string.bread_type_gluten_free_des
    ),
    VEGAN(
        id = 2,
        titleRes = R.string.bread_type_vegan_title,
        desRes = R.string.bread_type_vegan_des
    ),
    NUTFREE(
        id = 3,
        titleRes = R.string.bread_type_nut_free_title,
        desRes = R.string.bread_type_nut_free_des
    ),
    SUGARFREE(
        id = 4,
        titleRes = R.string.bread_type_sugar_free_title,
        desRes = R.string.bread_type_sugar_free_des,
    )
}
