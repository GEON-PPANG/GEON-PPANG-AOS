package com.sopt.geonppang.presentation.myPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.domain.repository.AuthRepository
import com.sopt.geonppang.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _withdrawState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val withdrawState get() = _withdrawState.asStateFlow()
    private val _logoutState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val logoutState = _logoutState.asStateFlow()

    fun withdraw() {
        viewModelScope.launch {
            authRepository.withdraw().onSuccess {
                _withdrawState.value = UiState.Success(true)
            }.onFailure { throwable ->
                _withdrawState.value = UiState.Error(throwable.message)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout().onSuccess {
                _logoutState.value = UiState.Success(true)
            }.onFailure { throwable ->
                _logoutState.value = UiState.Error(throwable.message)
            }
        }
    }
}
