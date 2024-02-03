package com.sopt.geonppang.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestSettingFilter(
    val mainPurpose: String,
    val breadType: List<Int>,
    val nutrientType: List<Int>
)
