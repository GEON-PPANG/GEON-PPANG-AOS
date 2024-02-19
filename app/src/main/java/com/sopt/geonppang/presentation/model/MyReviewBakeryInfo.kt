package com.sopt.geonppang.presentation.model

import android.os.Parcelable
import com.sopt.geonppang.presentation.type.BreadFilterType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyReviewBakeryInfo(
    val createdAt: String,
    val bakeryName: String,
    val bakeryPicture: String,
    val firstNearStation: String,
    val secondNearStation: String,
    val breadTypeList: List<BreadFilterType>
) : Parcelable
