package com.sopt.geonppang.presentation.myPage

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
    private val _isWithdrawCompleted = MutableLiveData<Boolean>()
    val isWithdrawCompleted: LiveData<Boolean> = _isWithdrawCompleted
    private val _isLogoutCompleted = MutableLiveData<Boolean>()
    val isLogoutCompleted: LiveData<Boolean> = _isLogoutCompleted

    private fun setIsWithdrawCompleted(value: Boolean) {
        _isWithdrawCompleted.value = value
    }

    private fun setIsLogoutCompleted(value: Boolean) {
        _isLogoutCompleted.value = value
    }

    fun withdraw() {
        viewModelScope.launch {
            authRepository.withdraw().onSuccess {
                setIsWithdrawCompleted(true)
            }.onFailure { throwable ->
                Timber.e(throwable.message)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout().onSuccess {
                setIsLogoutCompleted(true)
            }.onFailure { throwable ->
                Timber.e(throwable.message)
            }
        }
    }
}
