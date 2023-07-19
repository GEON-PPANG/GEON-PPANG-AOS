package com.sopt.geonppang.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestBookMark(
    val isAddingBookMark: Boolean
)
