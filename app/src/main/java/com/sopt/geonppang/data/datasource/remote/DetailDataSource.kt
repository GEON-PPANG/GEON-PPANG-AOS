package com.sopt.geonppang.data.datasource.remote

import com.sopt.geonppang.data.model.request.RequestBookMark
import com.sopt.geonppang.data.model.response.ResponseBookMark
import com.sopt.geonppang.data.model.response.ResponseDetailBakery
import com.sopt.geonppang.data.model.response.ResponseDetailReview
import com.sopt.geonppang.data.service.DetailService
import javax.inject.Inject

class DetailDataSource @Inject constructor(
    private val detailService: DetailService,
) {
    suspend fun fetchDetailBakery(bakeryId: Int): ResponseDetailBakery =
        detailService.fetchDetailBakery(bakeryId)

    suspend fun fetchDetailReview(bakeryId: Int): ResponseDetailReview =
        detailService.fetchDetailReview(bakeryId)

    suspend fun doBookMark(bakeryId: Int, isAddingBookMark: Boolean): ResponseBookMark =
        detailService.doBookMark(bakeryId, RequestBookMark(isAddingBookMark))
}
