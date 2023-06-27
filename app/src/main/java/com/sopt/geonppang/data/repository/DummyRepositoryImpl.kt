package com.sopt.geonppang.data.repository

import com.sopt.geonppang.data.datasource.remote.DummyDataSource
import com.sopt.geonppang.data.model.request.RequestDummy
import com.sopt.geonppang.domain.model.DummyData
import com.sopt.geonppang.domain.repository.DummyRepository
import javax.inject.Inject

class DummyRepositoryImpl @Inject constructor(
    private val dummyDataSource: DummyDataSource,
) : DummyRepository {
    override suspend fun uploadDummy(name: String, email: String): Result<DummyData> =
        runCatching {
            dummyDataSource.uploadDummy(RequestDummy(name, email)).data.toDummyData()
        }
}
