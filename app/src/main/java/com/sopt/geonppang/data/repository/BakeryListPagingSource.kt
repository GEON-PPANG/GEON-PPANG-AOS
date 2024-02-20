package com.sopt.geonppang.data.repository

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sopt.geonppang.data.service.BakeryService
import com.sopt.geonppang.domain.model.BakeryInformation
import retrofit2.HttpException
import java.io.IOException

class BakeryListPagingSource(
    private val bakeryService: BakeryService,
    private val sort: String,
    private val personal: Boolean,
    private val isHard: Boolean,
    private val isBrunch: Boolean,
    private val isDessert: Boolean,
) : PagingSource<Int, BakeryInformation>() {
    override fun getRefreshKey(state: PagingState<Int, BakeryInformation>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BakeryInformation> {
        return try {
            val position = params.key ?: 1
            val response = bakeryService.fetchBakeryList(
                sort = sort,
                personal = personal,
                isHard = isHard,
                isBrunch = isBrunch,
                isDessert = isDessert,
                pageNumber = position
            )

            val bakeryList = response.toBakery()

            LoadResult.Page(
                data = bakeryList,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (bakeryList.isNotEmpty()) position + 1 else null
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
