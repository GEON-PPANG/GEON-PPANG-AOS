package com.sopt.geonppang.presentation.type

import androidx.annotation.StringRes
import com.sopt.geonppang.R

enum class ReportType(
    @StringRes val reasonRes: Int
) {
    ADVERTISING(
        R.string.report_reason_advertising
    ),
    HATE(
        R.string.report_reason_hate
    ),
    COPYRIGHT(
        R.string.report_reason_copyright
    ),
    ETC(
        R.string.report_reason_etc
    )
}
