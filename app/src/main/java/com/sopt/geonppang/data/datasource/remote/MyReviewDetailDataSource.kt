package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.response.ResponseMyReviewDetail
import com.sopt.geonppang.data.service.MyReviewDetailService
import javax.inject.Inject

class MyReviewDetailDataSource @Inject constructor(
    private val myReviewDetailService: MyReviewDetailService
) {
    suspend fun fetchMyReviewDetail(reviewId: Int): ResponseMyReviewDetail =
        myReviewDetailService.fetchMyReviewDetail(reviewId)
}
