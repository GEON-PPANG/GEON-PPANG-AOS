package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.response.ResponseMypageBookmark
import com.sopt.geonppang.data.model.response.ResponseMypageInfo
import com.sopt.geonppang.data.model.response.ResponseMypageReview
import com.sopt.geonppang.data.service.MypageService
import javax.inject.Inject

class MypageDataSource @Inject constructor(
    private val mypageService: MypageService,
) {
    suspend fun fetchMypageInfo(): ResponseMypageInfo =
        mypageService.fetchMypageInfo()

    suspend fun fetchMyBookmarkList(): ResponseMypageBookmark =
        mypageService.fetchMypageBookmark()

    suspend fun fetchMyReviewList(): ResponseMypageReview =
        mypageService.fethcMypageReveiw()
}
