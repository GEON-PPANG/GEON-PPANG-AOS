package com.sopt.geonppang.domain.repository

import com.sopt.geonppang.domain.model.Bakery

interface MypageBookmarkRepository {
    suspend fun fetchMyBookmark() : Result<List<Bakery>>
}