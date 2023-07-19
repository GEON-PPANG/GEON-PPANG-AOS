package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.response.ResponseMypageReview
import com.sopt.geonppang.data.service.MypageReviewService
import javax.inject.Inject

class MypageReviewDataSource @Inject constructor(
    private val myReviewListService: MypageReviewService,
) {
    suspend fun fetchMyReviewList(): ResponseMypageReview =
        myReviewListService.fethcMypageReveiw()
}
