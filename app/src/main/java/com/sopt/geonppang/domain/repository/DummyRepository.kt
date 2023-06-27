package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.domain.model.DummyData

interface DummyRepository {
    suspend fun uploadDummy(name: String, email: String): Result<DummyData>
}
