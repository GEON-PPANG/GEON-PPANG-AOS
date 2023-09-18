package com.sopt.geonppang.presentation.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.data.datasource.local.GPDataSource
import com.sopt.geonppang.data.model.request.RequestNicknameSetting
import com.sopt.geonppang.data.model.request.RequestSignup
import com.sopt.geonppang.data.model.request.RequestValidationEmail
import com.sopt.geonppang.data.model.request.RequestValidationNickname
import com.sopt.geonppang.domain.repository.AuthRepository
import com.sopt.geonppang.domain.repository.ValidationRepository
import com.sopt.geonppang.presentation.type.AuthRoleType
import com.sopt.geonppang.presentation.type.PlatformType
import com.sopt.geonppang.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val gpDataSource: GPDataSource,
    private val authRepository: AuthRepository,
    private val validationRepository: ValidationRepository,
) : ViewModel() {
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val password_check = MutableLiveData("")
    val nickname = MutableLiveData("")
    private val _authRoleType = MutableStateFlow<AuthRoleType?>(null)
    val authRoleType get() = _authRoleType.asStateFlow()

    private val _isEmailUsable: MutableLiveData<Boolean> = MutableLiveData()
    val isEmailUsable: LiveData<Boolean> = _isEmailUsable

    private val _isNicknameUsable: MutableLiveData<Boolean?> = MutableLiveData()
    val isNicknameUsable: LiveData<Boolean?> = _isNicknameUsable

    private var _nicknameLen: MutableLiveData<Int> = MutableLiveData()
    val nicknameLen: LiveData<Int> = _nicknameLen

    private val _signUpState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val signUpState get() = _signUpState.asStateFlow()

    val isValidEmail: LiveData<Boolean> = email.map { email ->
        email.matches(Regex(EMAIL_PATTERN))
        /*조건에 만족하는 이메일인지 확인*/
    }

    val isValidNickname: LiveData<Boolean> = nickname.map { nickname ->
        nickname.matches(Regex(NICKNAME_PATTERN))
        /*조건에 맞는 닉네임인지 확인*/
    }

    val isValidPassword: LiveData<Boolean> = password.map { password ->
        password.matches(Regex(PASSWORD_PATTERN))
        /*조건에 맞는 비밀번호인지 확인*/
    }

    fun initNickname() {
        _isNicknameUsable.value = null
    }

    fun doubleCheckEmail() {
        viewModelScope.launch {
            Log.e("이메일 중복확인하기 http", "이메일 중복확인 서버 통신 확인하기")
            validationRepository.validateEmail(RequestValidationEmail(email.value))
                .onSuccess {
                    _isEmailUsable.value = true
                    Log.e("이메일 중복확인하기!!! http", "${_isEmailUsable.value}")
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                }
        }
    }

    fun doubleCheckNickname() {
        viewModelScope.launch {
            validationRepository.validateNickname(RequestValidationNickname(nickname.value))
                .onSuccess {
                    if (it.code == 200) {
                        // 중복 아닐 때
                        _isNicknameUsable.value = it.data?.available
                        // nickname.value?.length.also { _nicknameLen }
                        Log.e("isNicknameDuplicated", "{${it.message} ${_isNicknameUsable.value}}")
                    }
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                    // 중복
                    _isNicknameUsable.value = false
                    Log.e("isNicknameNotDuplicate", "{${_isNicknameUsable.value}}")
                }
        }
    }

    /*다음 버튼 활성화 -> 중복확인이 true 이면 활성화 되어야 함*/
    val completeEmail = MediatorLiveData<Boolean>().apply {
        addSource(email) { value = checkEmailCondition() }
    }

    /*비밀번호 다음 버튼 활성화*/
    val completePassword = MediatorLiveData<Boolean>().apply {
        addSource(password) { value = isPasswordDoubleCheck() }
        addSource(password_check) { value = isPasswordDoubleCheck() }
    }

    /*다음 버튼 활성화 -> 중복 확인이 되면 활성화 되어야 함*/
    val completeNickname = MediatorLiveData<Boolean>().apply {
        addSource(nickname) { value = checkNicknameCondition() }
    }

    private fun isPasswordDoubleCheck(): Boolean {
        return password.value.toString() == password_check.value.toString() && !password.value.isNullOrBlank() && !password_check.value.isNullOrBlank()
    }

    private fun checkEmailCondition(): Boolean {
        return isValidEmail.value == true
    }

    private fun checkNicknameCondition(): Boolean {
        return isValidNickname.value == true
    }

    fun signUp(
        platformType: PlatformType,
        platformToken: String,
        email: String,
        password: String,
        nickName: String
    ) {
        gpDataSource.platformType = platformType.name
        viewModelScope.launch {
            authRepository.signup(
                platformToken = platformToken,
                RequestSignup(
                    platformType.name,
                    email,
                    password,
                    nickName
                )
            )
                .onSuccess { signUpResponse ->
                    val responseBody = signUpResponse.body()?.toSignUpInfo()
                    val responseHeader = signUpResponse.headers()
                    val accessToken = responseHeader[AUTHORIZATION].toString()
                    val refreshToken = responseHeader[AUTHORIZATION_REFRESH].toString()
                    _authRoleType.value =
                        if (responseBody?.role == AuthRoleType.GUEST.name) AuthRoleType.GUEST else AuthRoleType.USER
                    gpDataSource.accessToken = BEARER_PREFIX + accessToken
                    if (_authRoleType.value == AuthRoleType.USER) {
                        gpDataSource.refreshToken = BEARER_PREFIX + refreshToken
                    }
                    _signUpState.value = UiState.Success(true)
                    Timber.tag("access token").d(gpDataSource.accessToken)
                    Timber.tag("refresh token").d(gpDataSource.refreshToken)
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                }
        }
    }

    fun settingNickName() {
        viewModelScope.launch {
            nickname.value?.let { nickname ->
                authRepository.settingNickname(RequestNicknameSetting(nickname))
                    .onSuccess {
                        _signUpState.value = UiState.Success(true)
                    }
                    .onFailure { throwable ->
                        Timber.e(throwable.message)
                    }
            }
        }
    }

    companion object {
        const val EMAIL_PATTERN = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$"
        const val NICKNAME_PATTERN = "^[\\sㄱ-ㅎ가-힣0-9a-zA-Z]{1,8}\$"
        const val PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*[0-9]).{7,25}.\$"
        const val AUTHORIZATION = "Authorization"
        const val AUTHORIZATION_REFRESH = "Authorization-refresh"
        const val BEARER_PREFIX = "Bearer "
    }
}
