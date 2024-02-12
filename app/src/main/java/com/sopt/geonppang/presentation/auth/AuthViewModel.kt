package com.sopt.geonppang.presentation.auth

import androidx.lifecycle.ViewModel
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val gpDataSource: GPDataSource,
    private val authRepository: AuthRepository,
    private val validationRepository: ValidationRepository,
) : ViewModel() {
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val passwordCheck = MutableStateFlow("")

    val nickname = MutableStateFlow("")
    private val _memberId = MutableStateFlow<Int?>(null)
    val memberId get() = _memberId.asStateFlow()

    private val _authRoleType = MutableStateFlow<AuthRoleType?>(null)
    val authRoleType get() = _authRoleType.asStateFlow()

    private val _isEmailUsable = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val isEmailUsable get() = _isEmailUsable.asStateFlow()

    private val _isNicknameUsable = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val isNicknameUsable get() = _isNicknameUsable.asStateFlow()

    private val _signUpState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val signUpState get() = _signUpState.asStateFlow()

    val isValidEmail: StateFlow<Boolean> = email.map { email ->
        email.matches(Regex(EMAIL_PATTERN))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val isValidNickname: StateFlow<Boolean> = nickname.map { nickname ->
        nickname.matches(Regex(NICKNAME_PATTERN))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val isValidPassword: StateFlow<Boolean> = password.map { password ->
        password.matches(Regex(PASSWORD_PATTERN))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val completePassword: StateFlow<Boolean> =
        combine(password, passwordCheck) { password, passwordCheck ->
            password == passwordCheck
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val signupReady: StateFlow<Boolean> =
        combine(
            isValidEmail,
            isEmailUsable.map { it is UiState.Success && it.data },
            isValidPassword,
            completePassword
        ) { isValidEmail, isEmailUsable, isValidPassword, completePassword ->
            isValidEmail && isEmailUsable && isValidPassword && completePassword
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val nicknameReady: StateFlow<Boolean> =
        combine(
            isValidNickname,
            isNicknameUsable.map { it is UiState.Success && it.data }
        ) { isValidNickname, isNicknameUsable ->
            isValidNickname && isNicknameUsable
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val platformType = gpDataSource.platformType

    // TODO: 초기화 부분 고민
    fun initNickname() {
        _isNicknameUsable.value = UiState.Empty
    }

    fun initEmail() {
        _isEmailUsable.value = UiState.Loading
    }

    fun doubleCheckEmail() {
        viewModelScope.launch {
            validationRepository.validateEmail(RequestValidationEmail(email.value))
                .onSuccess {
                    _isEmailUsable.value = UiState.Success(true)
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                    _isEmailUsable.value = UiState.Error("false")
                }
        }
    }

    fun doubleCheckNickname() {
        viewModelScope.launch {
            validationRepository.validateNickname(RequestValidationNickname(nickname.value))
                .onSuccess {
                    if (it.code == 200) {
                        _isNicknameUsable.value = UiState.Success(true)
                    }
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                    _isNicknameUsable.value = UiState.Error("false")
                }
        }
    }

    fun setAutoLogin() {
        gpDataSource.isLogin = true
    }

    // 카카오 로그인 이거나, 자체 회원 가입인 경우는 -> role이 USER
    // 카카오 회원 가입인 경우만 -> role 이 GUEST -> 이 경우메만 닉네임 설정 뷰로 이동
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
                        if (responseBody?.role == AuthRoleType.ROLE_GUEST.name) {
                            AuthRoleType.ROLE_GUEST
                        } else {
                            AuthRoleType.ROLE_MEMBER
                        }

                    gpDataSource.accessToken = BEARER_PREFIX + accessToken

                    // 카카오 로그인, 자체 회원 가입인 경우메만 리프래시 토큰을 저장하고 회원가입 상태를 success로 지정
                    if(_authRoleType.value == AuthRoleType.ROLE_MEMBER) {
                        gpDataSource.refreshToken = BEARER_PREFIX + refreshToken

                        _signUpState.value = UiState.Success(true)
                    }

                    // amplitude를 쏘기 위한 것
                    if (platformType == PlatformType.NONE) {
                        _memberId.value = responseBody?.memberId
                    }
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                }
        }
    }

    // 소셜 회원가입 후에만 사용하는 api
    fun settingNickName() {
        viewModelScope.launch {
            nickname.value.let { nickname ->
                authRepository.settingNickname(RequestNicknameSetting(nickname))
                    .onSuccess { response ->
                        val responseHeader = response.headers()
                        val responseBody = response.body()

                        // 소셜 회원가입 시 여기서 다시 엑세스, 리프래시 토큰을 발급 받음
                        val accessToken = responseHeader[AUTHORIZATION].toString()
                        val refreshToken = responseHeader[AUTHORIZATION_REFRESH].toString()

                        gpDataSource.accessToken = BEARER_PREFIX + accessToken
                        gpDataSource.refreshToken = BEARER_PREFIX + refreshToken

                        _signUpState.value = UiState.Success(true)
                        _memberId.value = responseBody?.data?.memberId
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
