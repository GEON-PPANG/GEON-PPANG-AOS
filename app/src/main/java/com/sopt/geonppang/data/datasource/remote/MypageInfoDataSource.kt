package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.response.ResponseMypageInfo
import com.sopt.geonppang.data.service.MypageInfoService
import javax.inject.Inject

class MypageInfoDataSource @Inject constructor(
    private val mypageInfoService: MypageInfoService,
) {
    suspend fun fetchMypageInfo():
            ResponseMypageInfo = mypageInfoService.fetchMypageInfo()
}