package com.sopt.geonppang.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseLogout(
    val code: Int,
    val message: String,
    val data: String?
)
