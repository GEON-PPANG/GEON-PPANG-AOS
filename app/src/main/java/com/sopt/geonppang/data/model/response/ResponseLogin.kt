package com.sopt.geonppang.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseLogin(
    val code: String,
    val message: String,
    val data: String,
)
