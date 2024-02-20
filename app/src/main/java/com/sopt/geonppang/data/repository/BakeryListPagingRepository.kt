package com.sopt.geonppang.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.sopt.geonppang.data.service.BakeryService
import com.sopt.geonppang.domain.model.BakeryListFilterType
import javax.inject.Inject

class BakeryListPagingRepository @Inject constructor(
    private val bakeryService: BakeryService,
) {
    fun fetchBakeryList(
        bakeryListFilterType: BakeryListFilterType
    ) = run {
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                BakeryListPagingSource(
                    bakeryService = bakeryService,
                    sort = bakeryListFilterType.sortType.sortName,
                    personal = bakeryListFilterType.isPersonalFilterApplied == true,
                    isHard = bakeryListFilterType.isHard,
                    isBrunch = bakeryListFilterType.isBrunch,
                    isDessert = bakeryListFilterType.isDessert
                )
            }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}
