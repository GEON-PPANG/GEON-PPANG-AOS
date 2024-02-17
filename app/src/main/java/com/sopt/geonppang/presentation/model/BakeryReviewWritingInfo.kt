package com.sopt.geonppang.presentation.model

import android.os.Parcelable
import com.sopt.geonppang.presentation.type.BreadFilterType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BakeryReviewWritingInfo(
    val bakeryName: String,
    val bakeryPicture: String,
    val breadTypeList: List<BreadFilterType>,
    val firstNearestStation: String,
    val secondNearestStation: String?,
) : Parcelable
