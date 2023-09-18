package com.sopt.geonppang.data.model.request

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RequestLogin(
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?,
)
