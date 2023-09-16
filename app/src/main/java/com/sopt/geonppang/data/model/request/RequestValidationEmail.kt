package com.sopt.geonppang.data.model.request

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RequestValidationEmail(
    @SerializedName("email")
    val email: String?,
)
