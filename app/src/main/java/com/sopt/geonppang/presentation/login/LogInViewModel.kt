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
                    // Log.e("로그인 상태 확인","${loginResponse}")
                    val responseHeader = loginResponse.headers()
                    val accessToken = responseHeader[AUTHORIZATION].toString()
                    if (loginResponse.code() == 200) {
                        // Log.e("로그인 성공", "${loginResponse.code()}")
                        gpDataSource.accessToken = BEARER_PREFIX + accessToken
                        gpDataSource.isLogin = true
                        _loginState.value = true
                    }
                    if (loginResponse.code() == 400) {
                        // Log.e("로그인 실패", "${loginResponse.code()}")
                        _loginState.value = false
                    }
                    if (loginResponse.code() == 500) {
                        // Log.e("서버 오류", "${loginResponse.code()}")
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
