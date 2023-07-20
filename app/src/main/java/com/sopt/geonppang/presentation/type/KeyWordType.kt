package com.sopt.geonppang.presentation.type

import androidx.annotation.StringRes
import com.sopt.geonppang.R

enum class KeyWordType(
    val keywordType: String,
    @StringRes val keywordNameRes: Int,
) {
    DELICIOUS(
        "DELICIOUS",
        R.string.review_key_word_type_taste
    ),
    KIND(
        "KIND",
        R.string.review_key_word_type_kind
    ),
    SPECIAL_MENU(
        "SPECIAL_MENU",
        R.string.review_key_word_type_special_menu
    ),
    ZERO_WASTE(
        "ZERO_WASTE",
        R.string.review_key_word_type_zero_waste
    ),
}
