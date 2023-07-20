package com.sopt.geonppang.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BakeryReviewWritingInfo(
    val bakeryName: String,
    val bakeryPicture: String,
    val breadType: BreadType?,
    val firstNearestStation: String,
    val secondNearestStation: String?,
) : Parcelable {
    @Parcelize
    data class BreadType(
        val isGlutenFree: Boolean,
        val isVegan: Boolean,
        val isNutFree: Boolean,
        val isSugarFree: Boolean,
    ) : Parcelable
}
