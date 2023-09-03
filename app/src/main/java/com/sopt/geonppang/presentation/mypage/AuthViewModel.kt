package com.sopt.geonppang.presentation.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _showWithdrawSuccessEvent = MutableLiveData<Unit>()
    val showWithdrawSuccessEvent: LiveData<Unit> = _showWithdrawSuccessEvent

    fun withdrawSuccess() {
        _showWithdrawSuccessEvent.value = Unit
    }

    fun withdraw() {
        viewModelScope.launch {
            authRepository.withdraw().onSuccess {
                withdrawSuccess()
            }.onFailure { throwable ->
                Timber.e(throwable.message)
            }
        }
    }
}
