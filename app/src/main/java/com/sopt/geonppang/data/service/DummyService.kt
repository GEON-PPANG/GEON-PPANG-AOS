package com.sopt.geonppang.data.service

import com.sopt.geonppang.data.model.request.RequestDummy
import com.sopt.geonppang.data.model.response.ResponseDummy
import retrofit2.http.Body
import retrofit2.http.POST

interface DummyService {
    @POST("api/dummy")
    suspend fun uploadDummy(@Body request: RequestDummy): ResponseDummy
}
