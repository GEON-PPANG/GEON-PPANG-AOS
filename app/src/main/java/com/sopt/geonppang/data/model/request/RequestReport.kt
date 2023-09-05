package com.sopt.geonppang.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestReport(
    val content: String,
    val reportCategory: String,
)
