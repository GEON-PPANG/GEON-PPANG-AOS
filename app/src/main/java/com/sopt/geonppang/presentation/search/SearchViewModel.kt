package com.sopt.geonppang.presentation.search

import androidx.lifecycle.ViewModel
import com.sopt.geonppang.R
import com.sopt.geonppang.domain.model.Bakery
import com.sopt.geonppang.domain.model.BreadType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel : ViewModel() {
    val mockBakeryList = listOf(
        Bakery(
            bakeryId = 1,
            bakeryName = "건대 비건빵아아아아아아아아아",
            firstNearStation = "건대역",
            secondNearStation = null,
            isBooked = true,
            bookmarkCount = 5,
            bakeryPicture = R.drawable.bbang1,
            isHACCP = true,
            isNonGMO = true,
            isVegan = true,
            breadType = BreadType(
                breadTypeId = 1,
                isGlutenFree = true,
                isVegan = true,
                isNutFree = true,
                isSugarFree = true,
            )
        ),
        Bakery(
            bakeryId = 2,
            bakeryName = "건대 비건빵아아아아아아",
            firstNearStation = "덕소역",
            secondNearStation = "구리역",
            isBooked = false,
            bookmarkCount = 7,
            bakeryPicture = R.drawable.bbang1,
            isHACCP = false,
            isNonGMO = true,
            isVegan = true,
            breadType = BreadType(
                breadTypeId = 2,
                isGlutenFree = false,
                isVegan = true,
                isNutFree = true,
                isSugarFree = true,
            )
        ),
        Bakery(
            bakeryId = 3,
            bakeryName = "건대 비건빵아아아아아아",
            firstNearStation = "덕소역",
            secondNearStation = "구리역",
            isBooked = false,
            bookmarkCount = 0,
            bakeryPicture = R.drawable.bbang1,
            isHACCP = false,
            isNonGMO = true,
            isVegan = false,
            breadType = BreadType(
                breadTypeId = 3,
                isGlutenFree = true,
                isVegan = true,
                isNutFree = true,
                isSugarFree = false,
            )
        ),
        Bakery(
            bakeryId = 4,
            bakeryName = "건대 비건빵아아아아아아",
            firstNearStation = "건대역",
            secondNearStation = null,
            isBooked = true,
            bookmarkCount = 5,
            bakeryPicture = R.drawable.bbang1,
            isHACCP = true,
            isNonGMO = true,
            isVegan = false,
            breadType = BreadType(
                breadTypeId = 4,
                isGlutenFree = false,
                isVegan = true,
                isNutFree = false,
                isSugarFree = true,
            )
        ),
        Bakery(
            bakeryId = 5,
            bakeryName = "건대 비건빵아아아아아아",
            firstNearStation = "덕소역",
            secondNearStation = "구리역",
            isBooked = false,
            bookmarkCount = 0,
            bakeryPicture = R.drawable.bbang1,
            isHACCP = false,
            isNonGMO = true,
            isVegan = true,
            breadType = BreadType(
                breadTypeId = 5,
                isGlutenFree = true,
                isVegan = false,
                isNutFree = false,
                isSugarFree = false,
            )
        ),
        Bakery(
            bakeryId = 6,
            bakeryName = "건대 비건빵아아아아아아",
            firstNearStation = "덕소역",
            secondNearStation = "구리역",
            isBooked = false,
            bookmarkCount = 0,
            bakeryPicture = R.drawable.bbang1,
            isHACCP = false,
            isNonGMO = true,
            isVegan = false,
            breadType = BreadType(
                breadTypeId = 6,
                isGlutenFree = true,
                isVegan = true,
                isNutFree = true,
                isSugarFree = false,
            )
        ),
    )

    val inputSearch = MutableStateFlow("")

    private var _searchCount = MutableStateFlow<Int?>(6)
    val searchCount get() = _searchCount.asStateFlow()
}
