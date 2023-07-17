package com.sopt.geonppang.presentation.type

import androidx.annotation.StringRes
import com.sopt.geonppang.R

enum class MainPurposeType(
    @StringRes val titleRes: Int,
    @StringRes val desRes: Int,
) {
    HEALTH(
        R.string.main_purpose_type_health_title,
        R.string.main_purpose_type_health_des
    ),
    DIET(
        R.string.main_purpose_type_diet_title,
        R.string.main_purpose_type_diet_des
    ),
    VEGAN(
        R.string.main_purpose_type_vegan_title,
        R.string.main_purpose_type_vegan_des
    )
}
