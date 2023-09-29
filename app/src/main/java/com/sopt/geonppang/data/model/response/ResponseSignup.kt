package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.SignUpInfo
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

    fun toSignUpInfo() = SignUpInfo(
        memberId = data.memberId,
        role = data.role,
        type = data.type
    )
}
