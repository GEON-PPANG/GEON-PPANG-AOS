package com.sopt.geonppang.data.repository

import com.sopt.geonppang.data.datasource.remote.MypageDataSource
import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.domain.model.MyReview
import com.sopt.geonppang.domain.model.Profile
import com.sopt.geonppang.domain.repository.MypageRepository
import javax.inject.Inject

class MypageRepositoryImpl @Inject constructor(
    private val mypageDataSource: MypageDataSource,
) : MypageRepository {
    override suspend fun fetchMypageInfo(): Result<Profile> = runCatching {
        mypageDataSource.fetchMypageInfo().toMypageInfo()
    }

    override suspend fun fetchMyBookmark(): Result<List<Bakery>> = runCatching {
        mypageDataSource.fetchMyBookmarkList().toMypageBookmark()
    }

    override suspend fun fetchMyReview(): Result<List<MyReview>> = runCatching {
        mypageDataSource.fetchMyReviewList().toMyReview()
    }
}
