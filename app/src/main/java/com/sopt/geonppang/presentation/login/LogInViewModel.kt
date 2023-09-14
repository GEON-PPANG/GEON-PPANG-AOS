package com.sopt.geonppang.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.data.datasource.local.GPDataStore
import com.sopt.geonppang.data.model.request.RequestLogin
import com.sopt.geonppang.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    val gpDataStore: GPDataStore
) : ViewModel() {
    val loginEmail = MutableLiveData("")
    val loginPassword = MutableLiveData("")
    private val _loginState = MutableStateFlow<Boolean?>(null)
    val loginState get() = _loginState.asStateFlow()

    fun login() {
        viewModelScope.launch {
            authRepository.login(RequestLogin(loginEmail.value, loginPassword.value))
                .onSuccess {
                    gpDataStore.isLogin = true
                    _loginState.value = true
                    // TODO 응답 코드 200 반환 받기 구현 예정
                }.onFailure { throwable ->
                    _loginState.value = false
                    Timber.tag("로그인 실패").e(throwable.message)
                }
        }
    }
}
