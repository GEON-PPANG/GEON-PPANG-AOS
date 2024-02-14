package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.UserFilter
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetUserFilter(
    val code: Int,
    val data: Data,
    val message: String
) {
    @Serializable
    data class Data(
        val breadTypeList: List<BreadTypeId>,
        val mainPurpose: String,
        val memberId: Int,
        val nickname: String,
        val nutrientTypeList: List<NutrientTypeId>
    ) {
        @Serializable
        data class BreadTypeId(
            val breadTypeId: Int
        )

        @Serializable
        data class NutrientTypeId(
            val nutrientTypeId: Int
        )
    }

    fun toUserFilter() = UserFilter(
        mainPurpose = data.mainPurpose,
        nickName = data.nickname
    )
}
