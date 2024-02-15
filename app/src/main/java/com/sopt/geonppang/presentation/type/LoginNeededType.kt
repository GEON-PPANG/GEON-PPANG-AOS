package com.sopt.geonppang.presentation.type

import androidx.annotation.StringRes
import com.sopt.geonppang.R

enum class LoginNeededType(
    @StringRes val titleRes: Int,
) {
    LOGIN_NEEDED_FILTER(
        R.string.login_needed_phrase
    ),
    LOGIN_NEEDED_BOOKMARK(
        R.string.login_needed_phrase_bakery_bookmark
    ),
    LOGIN_NEEDED_REPORT_REVIEW(
        R.string.login_needed_phrase_bakery_report_review
    ),
    LOGIN_NEEDED_WRITE_REVIEW(
        R.string.login_needed_phrase_bakery_write_review
    ),
    LOGIN_NEEDED_MY_PAGE(
        R.string.login_needed_phrase_bakery_write_review
    )
}
