package com.sopt.geonppang.presentation.type

import androidx.annotation.StringRes
import com.sopt.geonppang.R

enum class ReportCategoryType(
    @StringRes val reasonRes: Int
) {
    ADVERTISING(
        R.string.report_category_advertising
    ),
    HATE(
        R.string.report_category_hate
    ),
    COPYRIGHT(
        R.string.report_category_copyright
    ),
    ETC(
        R.string.report_category_etc
    )
}
