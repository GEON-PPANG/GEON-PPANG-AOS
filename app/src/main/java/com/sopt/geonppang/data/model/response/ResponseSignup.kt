package com.sopt.geonppang.data.model.response

data class ResponseSignup(
    val code: String,
    val message: String,
    val data: ResponseSignupData,
) {
    data class ResponseSignupData(
        val memberId: Int,
        val type: String,
        val email: String,
        val role:String,
    )
}
