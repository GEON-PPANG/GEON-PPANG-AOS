package com.sopt.geonppang.data.model.response

import com.sopt.geonppang.domain.model.BestBakery
import kotlinx.serialization.Serializable

@Serializable
data class ResponseBestBakery(
    val code: Int,
    val data: List<Data>,
    val message: String,
) {
    @Serializable
    data class Data(
        val bakeryId: Int,
        val bakeryName: String,
        val bakeryPicture: String,
        val bookMarkCount: Int,
        val firstNearStation: String,
        val isBookMarked: Boolean,
        val isHACCP: Boolean,
        val isNonGMO: Boolean,
        val isVegan: Boolean,
        val reviewCount: Int,
        val secondNearStation: String,
    )

    fun toBestBakery() = data.map { bestBakery ->
        BestBakery(
            bakeryId = bestBakery.bakeryId,
            bakeryName = bestBakery.bakeryName,
            bakeryImage = bestBakery.bakeryPicture,
            bookmarkCount = bestBakery.bookMarkCount,
            firstNearStation = bestBakery.firstNearStation,
            secondNearStation = bestBakery.secondNearStation,
            isHACCP = bestBakery.isHACCP,
            isVegan = bestBakery.isVegan,
            isNonGMO = bestBakery.isNonGMO,
            reviewCount = bestBakery.reviewCount,
        )
    }
}
