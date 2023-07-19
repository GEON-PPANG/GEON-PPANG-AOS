package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.BreadType
import com.sopt.geonppang.domain.model.Profile
import kotlinx.serialization.Serializable

data class ResponseMypageInfo(
    val code: Int,
    val data: Data,
    val message: String
) {
    @Serializable
    data class Data(
        val memberNickname: String,
        val mainPurpose: String,
        val breadType: BreadType
    ) {
        @Serializable
        data class BreadType(
            val breadTypeId: Int,
            val breadTypeName: String,
            val isGlutenFree: Boolean,
            val isNutFree: Boolean,
            val isSugarFree: Boolean,
            val isVegan: Boolean
        )
    }

    fun toMypageInfo() = Profile(
        memberNickname = data.memberNickname,
        mainPurpose = data.mainPurpose,
        breadType = BreadType(
            breadTypeId = data.breadType.breadTypeId,
            breadTypeName = data.breadType.breadTypeName,
            isGlutenFree = data.breadType.isGlutenFree,
            isVegan = data.breadType.isVegan,
            isNutFree = data.breadType.isNutFree,
            isSugarFree = data.breadType.isSugarFree
        )
    )
}