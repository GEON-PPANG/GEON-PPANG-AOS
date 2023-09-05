package com.sopt.geonppang.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseReport(
    val code: Int,
    val message: String,
    val data: Data
) {
    @Serializable
    data class Data(
        val reviewReportId: Long,
    )
}
