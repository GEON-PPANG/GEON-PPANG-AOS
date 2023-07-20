package com.sopt.geonppang.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestReviewWriting(
    val isLike: Boolean?,
    val keywordList: List<KeywordName>?,
    val reviewText: String
) {
    @Serializable
    data class KeywordName(
        val keywordName: String
    )
}
