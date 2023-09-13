package com.sopt.geonppang.domain.model

data class SignInfo(
    val role: String,
    val type: String,
    val email: String,
    val accessToken: String = "",
    val refreshToken: String = "",
)
