package com.sopt.geonppang.presentation.type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.sopt.geonppang.R

enum class DialogType(
    @DrawableRes val iconRes: Int,
    @StringRes val titleRes: Int,
    @StringRes val contextRes: Int,
    @StringRes val leftBtnRes: Int,
    @StringRes val rightBtnRes: Int
) {
    LOGOUT(
        R.drawable.ic_sad,
        R.string.dialog_type_logout_title,
        R.string.dialog_type_logout_context,
        R.string.dialog_type_logout_left_btn,
        R.string.dialog_type_logout_right_btn
    ),
    DELETION(
        R.drawable.ic_crying,
        R.string.dialog_type_deletion_title,
        R.string.dialog_type_deletion_context,
        R.string.dialog_type_deletion_left_btn,
        R.string.dialog_type_deletion_right_btn
    )
}
