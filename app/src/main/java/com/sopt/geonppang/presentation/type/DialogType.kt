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
        R.string.my_page_logout,
        R.string.dialog_type_logout_context,
        R.string.dialog_type_logout_left_btn,
        R.string.dialog_type_logout_right_btn
    ),
    WITHDRAW(
        R.drawable.ic_crying,
        R.string.my_page_withdraw,
        R.string.dialog_type_withdraw_context,
        R.string.dialog_type_withdraw_left_btn,
        R.string.dialog_type_withdraw_right_btn
    )
}
