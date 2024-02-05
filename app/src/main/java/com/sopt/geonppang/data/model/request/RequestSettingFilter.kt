package com.sopt.geonppang.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestSettingFilter(
    val mainPurpose: String,
    val breadTypeList: List<Int>,
    val nutrientTypeList: List<Int>
)
