package com.sopt.geonppang.presentation.auth

import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.sopt.geonppang.util.KakaoLoginCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class KakaoAuthViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    /** 회원가입 여부 확인하는 변수 (추후 사용 예정) */
    private val _isSignedUp = MutableSharedFlow<Boolean>()
    val isSignedUp = _isSignedUp.asSharedFlow()

    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        KakaoLoginCallback(_uiState) { checkTokenAvailability() }.handleResult(token, error)
    }

    private fun checkTokenAvailability() {
        val isAvailable = _uiState.value.kakaoToken.isNotBlank()
        _uiState.value = _uiState.value.copy(isTokenAvailability = isAvailable)
    }

    data class LoginUiState(
        val kakaoToken: String = "",
        val isTokenAvailability: Boolean = false
    )

    companion object {
        private const val SOCIAL_TYPE = "KAKAO"
    }
}
