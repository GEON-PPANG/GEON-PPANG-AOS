package com.sopt.geonppang.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.data.datasource.local.GPDataSource
import com.sopt.geonppang.domain.model.BestBakery
import com.sopt.geonppang.domain.model.BestReview
import com.sopt.geonppang.domain.repository.GetUserFilterRepository
import com.sopt.geonppang.domain.repository.HomeRepository
import com.sopt.geonppang.presentation.type.UserRoleType
import com.sopt.geonppang.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val gpDataSource: GPDataSource,
    private val homeRepository: HomeRepository,
    private val getUserFilterRepository: GetUserFilterRepository
) : ViewModel() {
    private var _bestBakeryListState = MutableStateFlow<UiState<List<BestBakery>>>(UiState.Loading)
    val bestBakeryListState get() = _bestBakeryListState.asStateFlow()

    private var _bestReviewListState = MutableStateFlow<UiState<List<BestReview>>>(UiState.Loading)
    val bestReviewListState get() = _bestReviewListState.asStateFlow()

    private val _nickName = MutableStateFlow("")
    val nickName get() = _nickName.asStateFlow()

    private val _userRoleType = MutableStateFlow(gpDataSource.userRoleType)
    val userRoleType get() = _userRoleType.asStateFlow()


    // TODO: 무슨 회원인지 로컬 에서 관리
    init {
        // 비회원이 아닌 경우에만 호출 되도록
        if (_userRoleType.value != UserRoleType.NONE_MEMBER.name) {
            getUserFilter()
        }
        fetchBestList()
    }

    // 하나의 스코프를 만들어서 각 함수들이 동기적으로 처리되도록 수정
    fun fetchBestList() {
        viewModelScope.launch {
            homeRepository.fetchBestBakery()
                .onSuccess { bestBakeryList ->
                    _bestBakeryListState.value = UiState.Success(bestBakeryList)
                }
                .onFailure { throwable ->
                    _bestBakeryListState.value = UiState.Error(throwable.message)
                }

            homeRepository.fetchBestReview()
                .onSuccess { bestReviewList ->
                    _bestReviewListState.value = UiState.Success(bestReviewList)
                }
                .onFailure { throwable ->
                    _bestReviewListState.value = UiState.Error(throwable.message)
                }
        }
    }

    // 유저가 필터를 선택 했는지 안 했는지를 구분 하기 위한 api 호출
    private fun getUserFilter() {
        viewModelScope.launch {
            getUserFilterRepository.getUserFilter()
                .onSuccess { userFilterInfo ->
                    setNickName(userFilterInfo.nickName)

                    // 홈에서 필터 조회를 해서 유저 상태 설정
                    val userRoleType =
                        if (userFilterInfo.mainPurpose == NONE) {
                            UserRoleType.FILTER_UNSELECTED_MEMBER.name
                        } else {
                            UserRoleType.FILTER_SELECTED_MEMBER.name
                        }

                    gpDataSource.userRoleType = userRoleType
                    _userRoleType.value = userRoleType
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                }
        }
    }

    fun setNickName(nickName: String) {
        _nickName.value = nickName
    }

    companion object {
        const val NONE = "NONE"
    }
}
