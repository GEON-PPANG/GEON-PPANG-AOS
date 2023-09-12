package com.sopt.geonppang.presentation.myPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _isWithdrawCompleted = MutableStateFlow<Boolean?>(null)
    val isWithdrawCompleted get() = _isWithdrawCompleted.asStateFlow()
    private val _isLogoutCompleted = MutableStateFlow<Boolean?>(null)
    val isLogoutCompleted = _isLogoutCompleted.asStateFlow()

    fun withdraw() {
        viewModelScope.launch {
            authRepository.withdraw().onSuccess {
                _isWithdrawCompleted.value = true
            }.onFailure { throwable ->
                Timber.e(throwable.message)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout().onSuccess {
                _isLogoutCompleted.value = true
            }.onFailure { throwable ->
                Timber.e(throwable.message)
            }
        }
    }
}
