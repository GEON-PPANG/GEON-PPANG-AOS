package com.sopt.geonppang.data.repository

import com.sopt.geonppang.data.datasource.remote.MypageReviewDataSource
import com.sopt.geonppang.domain.model.MyReview
import com.sopt.geonppang.domain.repository.MypageReviewRepository
import javax.inject.Inject

class MypageReviewRepositoryImpl @Inject constructor(
    private val myPageReviewDataSource: MypageReviewDataSource,
) : MypageReviewRepository {
    override suspend fun fetchMyReview(): Result<List<MyReview>> = runCatching {
        myPageReviewDataSource.fetchMyReviewList().toMyReview()
    }
}
