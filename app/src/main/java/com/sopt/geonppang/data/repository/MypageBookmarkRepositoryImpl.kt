package com.sopt.geonppang.data.repository

import com.sopt.geonppang.data.datasource.remote.MypageBookmarkDataSource
import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.domain.repository.MypageBookmarkRepository
import javax.inject.Inject

class MypageBookmarkRepositoryImpl @Inject constructor(
    private val mypageBookmarkDataSource: MypageBookmarkDataSource,
) : MypageBookmarkRepository {
    override suspend fun fetchMyBookmark(): Result<List<Bakery>> = runCatching{
        mypageBookmarkDataSource.fetchMyBookmarkList().toMypageBookmark()
    }
}