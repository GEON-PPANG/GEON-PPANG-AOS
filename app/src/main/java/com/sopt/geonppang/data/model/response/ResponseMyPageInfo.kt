package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.Profile
import com.sopt.geonppang.presentation.type.BreadFilterType
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMyPageInfo(
    val code: Int,
    val data: Data,
    val message: String
) {
    @Serializable
    data class Data(
        val memberNickname: String,
        val mainPurpose: String,
        val breadTypeList: List<BreadTypeIdDto>
    )

    fun toMypageInfo() = Profile(
        memberNickname = data.memberNickname,
        mainPurpose = data.mainPurpose,
        breadTypeList = data.breadTypeList.mapNotNull { breadTypeIdDto ->
            BreadFilterType.values().find {
                it.id == breadTypeIdDto.breadTypeId
            }
        }
    )
}
