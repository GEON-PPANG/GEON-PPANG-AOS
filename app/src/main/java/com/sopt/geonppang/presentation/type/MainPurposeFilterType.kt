package com.sopt.geonppang.presentation.type

import androidx.annotation.StringRes
import com.sopt.geonppang.R

enum class MainPurposeFilterType(
    @StringRes val titleRes: Int,
    @StringRes val desRes: Int,
) {
    HEALTH(
        titleRes = R.string.main_purpose_type_health_title,
        desRes = R.string.main_purpose_type_health_des
    ),
    DIET(
        titleRes = R.string.main_purpose_type_diet_title,
        desRes = R.string.main_purpose_type_diet_des
    ),
    VEGAN(
        titleRes = R.string.main_purpose_type_vegan_title,
        desRes = R.string.main_purpose_type_vegan_des
    )
}
