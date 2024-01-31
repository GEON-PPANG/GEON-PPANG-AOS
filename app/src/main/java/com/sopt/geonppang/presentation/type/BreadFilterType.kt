package com.sopt.geonppang.presentation.type

import androidx.annotation.StringRes
import com.sopt.geonppang.R

enum class BreadFilterType(
    @StringRes val titleRes: Int,
    @StringRes val desRes: Int,
) {
    GLUTENFREE(
        titleRes = R.string.bread_type_gluten_free_title,
        desRes = R.string.bread_type_gluten_free_des
    ),
    VEGAN(
        titleRes = R.string.bread_type_vegan_title,
        desRes = R.string.bread_type_vegan_des
    ),
    NUTFREE(
        titleRes = R.string.bread_type_nut_free_title,
        desRes = R.string.bread_type_nut_free_des
    ),
    SUGARFREE(
        titleRes = R.string.bread_type_sugar_free_title,
        desRes = R.string.bread_type_sugar_free_des,
    )
}
