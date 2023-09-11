package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.response.ResponseMyPageBookmark
import com.sopt.geonppang.data.model.response.ResponseMyPageInfo
import com.sopt.geonppang.data.model.response.ResponseMyPageReview
import com.sopt.geonppang.data.service.MyPageService
import javax.inject.Inject

class MyPageDataSource @Inject constructor(
    private val myPageService: MyPageService,
) {
    suspend fun fetchMyPageInfo(): ResponseMyPageInfo =
        myPageService.fetchMyPageInfo()

    suspend fun fetchMyBookmarkList(): ResponseMyPageBookmark =
        myPageService.fetchMyPageBookmark()

    suspend fun fetchMyReviewList(): ResponseMyPageReview =
        myPageService.fethcMyPageReveiw()
}
