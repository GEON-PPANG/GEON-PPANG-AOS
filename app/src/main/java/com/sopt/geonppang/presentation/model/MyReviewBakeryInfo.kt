package com.sopt.geonppang.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyReviewBakeryInfo(
    val createdAt: String,
    val bakeryName: String,
    val bakeryPicture: String,
    val firstNearStation: String,
    val secondNearStation: String,
    val breadType: BakeryReviewWritingInfo.BreadType
) : Parcelable
