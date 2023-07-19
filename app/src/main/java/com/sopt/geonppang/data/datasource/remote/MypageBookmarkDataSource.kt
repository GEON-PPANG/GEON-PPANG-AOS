package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.response.ResponseMypageBookmark
import com.sopt.geonppang.data.service.MypageBookmarkService
import javax.inject.Inject

class MypageBookmarkDataSource @Inject constructor(
    private val mypageBookmarkService: MypageBookmarkService,
) {
    suspend fun fetchMyBookmarkList() : ResponseMypageBookmark =
        mypageBookmarkService.fetchMypageBookmark()
}