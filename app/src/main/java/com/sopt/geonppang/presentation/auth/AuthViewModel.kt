package com.sopt.geonppang.presentation.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.data.datasource.local.GPDataStore
import com.sopt.geonppang.data.model.request.RequestSignup
import com.sopt.geonppang.domain.repository.AuthRepository
import com.sopt.geonppang.presentation.type.AuthRoleType
import com.sopt.geonppang.presentation.type.PlatformType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val gpDataStore: GPDataStore,
    private val authRepository: AuthRepository
) : ViewModel() {
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val password_check = MutableLiveData("")
    val nickname = MutableLiveData("")
    private val _authRoleType = MutableStateFlow<AuthRoleType?>(null)
    val authRoleType get() = _authRoleType.asStateFlow()

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

    // TODO 이메일 중복확인 구현 예정
    /*val doubleCheckEmail: LiveData<Boolean> = email.map {

    }*/
    val doubleCheckNickname = MediatorLiveData<Boolean>().apply {
        // Todo 닉네임 중복 확인 구현 예정
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

    // 현재 소셜 회원가입만 고려해 email, password, nickname을 임의로 ""로 해놓았습니다.
    fun singUp(platformType: PlatformType, platformToken: String) {
        gpDataStore.platformType = PlatformType.KAKAO.name
        viewModelScope.launch {
            authRepository.signup(
                platformToken = platformToken,
                RequestSignup(
                    platformType.name,
                    "",
                    "",
                    ""
                )
            )
                .onSuccess { signUpResponse ->
                    val responseBody = signUpResponse.body()?.toSignUpInfo()
                    val responseHeader = signUpResponse.headers()
                    _authRoleType.value =
                        if (responseBody?.role == AuthRoleType.GUEST.name) AuthRoleType.GUEST else AuthRoleType.USER
                    gpDataStore.accessToken = responseHeader[AUTHORIZATION].toString()
                    Log.d("header", responseHeader[AUTHORIZATION].toString())
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                }
        }
    }

    companion object {
        const val EMAIL_PATTERN = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$"
        const val NICKNAME_PATTERN = "^[\\sㄱ-ㅎ가-힣0-9a-zA-Z]{1,8}\$"
        const val PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*[0-9]).{7,25}.\$"
        const val AUTHORIZATION = "Authorization"
    }
}
