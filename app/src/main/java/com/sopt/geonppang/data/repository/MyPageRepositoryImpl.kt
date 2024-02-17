package com.sopt.geonppang.data.repository

import com.sopt.geonppang.data.datasource.remote.MyPageDataSource
import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.domain.model.BakeryInformation
import com.sopt.geonppang.domain.model.MyReview
import com.sopt.geonppang.domain.model.Profile
import com.sopt.geonppang.domain.repository.MyPageRepository
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val mypageDataSource: MyPageDataSource,
) : MyPageRepository {
    override suspend fun fetchProfileInfo(): Result<Profile> = runCatching {
        mypageDataSource.fetchMyPageInfo().toMypageInfo()
    }

    override suspend fun fetchMyBookmark(): Result<List<BakeryInformation>> = runCatching {
        mypageDataSource.fetchMyBookmarkList().toMypageBookmark()
    }

    override suspend fun fetchMyReview(): Result<List<MyReview>> = runCatching {
        mypageDataSource.fetchMyReviewList().toMyReview()
    }
}
