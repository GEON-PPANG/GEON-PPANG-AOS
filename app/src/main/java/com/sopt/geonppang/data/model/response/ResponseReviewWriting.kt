package com.sopt.geonppang.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseReviewWriting(
    val code: Int,
    val data: Data,
    val message: String,
) {
    @Serializable
    data class Data(
        val reviewId: Int,
    )
}
