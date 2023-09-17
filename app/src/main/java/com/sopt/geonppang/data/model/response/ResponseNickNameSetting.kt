package com.sopt.geonppang.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseNickNameSetting(
    val code: Int,
    val data: Data?,
    val message: String
) {
    @Serializable
    data class Data(
        val memberId: Int,
        val nickname: String,
        val role: String
    )
}
