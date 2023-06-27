package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.request.RequestDummy
import com.sopt.geonppang.data.model.response.ResponseDummy
import com.sopt.geonppang.data.service.DummyService
import javax.inject.Inject

class DummyDataSource @Inject constructor(
    private val dummyService: DummyService,
) {
    suspend fun uploadDummy(requestDummy: RequestDummy): ResponseDummy =
        dummyService.uploadDummy(requestDummy)
}
