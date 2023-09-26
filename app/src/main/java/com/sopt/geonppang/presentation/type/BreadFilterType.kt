package com.sopt.geonppang.presentation.type

import androidx.annotation.StringRes
import com.sopt.geonppang.R

enum class BreadFilterType(
    @StringRes val amplitudeRes: Int,
    @StringRes val titleRes: Int,
    @StringRes val desRes: Int,
) {
    GLUTENFREE(
        R.string.bread_type_gluten_free_ampl,
        R.string.bread_type_gluten_free_title,
        R.string.bread_type_gluten_free_des
    ),
    VEGAN(
        R.string.bread_type_vegan_ampl,
        R.string.bread_type_vegan_title,
        R.string.bread_type_vegan_des
    ),
    NUTFREE(
        R.string.bread_type_nut_free_ampl,
        R.string.bread_type_nut_free_title,
        R.string.bread_type_nut_free_des
    ),
    SUGARFREE(
        R.string.bread_type_sugar_free_ampl,
        R.string.bread_type_sugar_free_title,
        R.string.bread_type_sugar_free_des,
    )
}
