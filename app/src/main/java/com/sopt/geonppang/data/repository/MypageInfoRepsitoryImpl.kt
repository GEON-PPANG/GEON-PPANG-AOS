package com.sopt.geonppang.data.repository

import com.sopt.geonppang.data.datasource.remote.MypageInfoDataSource
import com.sopt.geonppang.data.model.response.ResponseMypageInfo
import com.sopt.geonppang.domain.model.Profile
import com.sopt.geonppang.domain.repository.MypageInfoRepository
import javax.inject.Inject

class MypageInfoRepsitoryImpl @Inject constructor(
    private val mypageInfoDataSource: MypageInfoDataSource,
) : MypageInfoRepository {
    override suspend fun fetchMypageInfo(): Result<Profile> = runCatching {
        mypageInfoDataSource.fetchMypageInfo().toMypageInfo()
    }
}