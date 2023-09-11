package com.sopt.geonppang.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestSignup(
    val platformType: String,
    val email: String,
    val password: String,
    val nickname: String,
)
