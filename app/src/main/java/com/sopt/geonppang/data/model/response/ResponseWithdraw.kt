package com.sopt.geonppang.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseWithdraw(
    val code: Int,
    val message: String,
    val data: Data
) {
    @Serializable
    data class Data(
        val memberId: Long,
    )
}
