package com.sopt.geonppang.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.data.datasource.local.GPDataSource
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
    private val gpDataSource: GPDataSource
) : ViewModel() {
    val loginEmail = MutableStateFlow("")
    val loginPassword = MutableStateFlow("")
    private val _loginState = MutableStateFlow<Boolean?>(null)
    val loginState get() = _loginState.asStateFlow()

    fun initLogin() {
        _loginState.value = null
    }

    fun login() {
        viewModelScope.launch {
            authRepository.login(RequestLogin(loginEmail.value, loginPassword.value))
                .onSuccess { loginResponse ->
                    val responseHeader = loginResponse.headers()
                    val accessToken = responseHeader[AUTHORIZATION].toString()
                    if (loginResponse.code() == 200) {
                        gpDataSource.accessToken = BEARER_PREFIX + accessToken
                        gpDataSource.isLogin = true
                        _loginState.value = true
                    }
                    if (loginResponse.code() == 400) {
                        _loginState.value = false
                    }
                    if (loginResponse.code() == 500) {
                        Timber.tag("서버 오류")
                    }
                }.onFailure { throwable ->
                    Timber.tag("로그인 실패 on Failure").e(throwable.message)
                }
        }
    }

    companion object {
        const val AUTHORIZATION = "Authorization"
        const val BEARER_PREFIX = "Bearer "
    }
}
