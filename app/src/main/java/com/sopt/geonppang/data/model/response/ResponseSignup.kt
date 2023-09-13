package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.SignInfo
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSignup(
    val code: String,
    val message: String,
    val data: ResponseSignupData,
) {
    @Serializable
    data class ResponseSignupData(
        val memberId: Int,
        val type: String,
        val email: String,
        val role: String,
    )

    fun toSignup() = SignInfo(
        role = data.role,
        type = data.type,
        email = data.email,
    )
}
