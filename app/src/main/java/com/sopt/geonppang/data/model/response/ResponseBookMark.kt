package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.BookMark
import kotlinx.serialization.Serializable

@Serializable
data class ResponseBookMark(
    val code: Int,
    val data: Data,
    val message: String
) {
    @Serializable
    data class Data(
        val bookMarkCount: Int,
        val isBookMarked: Boolean
    )

    fun toBookMark() = BookMark(
        bookMarkCount = data.bookMarkCount,
        isBookMarked = data.isBookMarked
    )
}
