package com.sopt.geonppang.data.service

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.Constants.TAG
import com.kakao.sdk.user.UserApiClient
import com.sopt.geonppang.presentation.type.PlatformType
import dagger.hilt.android.qualifiers.ActivityContext
import timber.log.Timber
import javax.inject.Inject

class KakaoAuthService @Inject constructor(
    @ActivityContext private val context: Context,
) {
    private val kakaoLoginState =
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) KAKAO_APP
        else KAKAO_ACCOUNT

    fun startKakaoLogin(
        loginListener: (platformType: PlatformType, platformToken: String, email: String, password: String, nickname: String) -> Unit
    ) {
        val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                handleLoginError(error)
            } else if (token != null) {
                Timber.tag("kakaoToken").d(token.accessToken)
                handleLoginSuccess(token, loginListener)
            }
        }

        when (kakaoLoginState) {
            KAKAO_APP -> {
                UserApiClient.instance.loginWithKakaoTalk(
                    context, callback = kakaoLoginCallback
                )
            }

            KAKAO_ACCOUNT -> {
                UserApiClient.instance.loginWithKakaoAccount(
                    context, callback = kakaoLoginCallback
                )
            }
        }
    }

    private fun handleLoginSuccess(
        oAuthToken: OAuthToken,
        loginListener: (platformType: PlatformType, platformToken: String, email: String, password: String, nickname: String) -> Unit
    ) {
        loginListener(PlatformType.KAKAO, oAuthToken.accessToken, "", "", "")
    }

    private fun handleLoginError(throwable: Throwable) {
        when (kakaoLoginState) {
            KAKAO_APP -> Timber.d("카카오톡으로 로그인 실패 (${throwable.message})")
            KAKAO_ACCOUNT -> Timber.d("카카오 계정으로 로그인 실패 (${throwable.message})")
        }
    }

    fun logoutKakao(logoutListener: () -> Unit) {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Timber.tag(TAG).e(error, "카카오 로그아웃 실패. SDK에서 토큰 삭제됨")
            } else {
                logoutListener()
                Timber.tag(TAG).i("카카오 로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }
    }

    fun withdrawKakao() {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Timber.tag(TAG).e(error, "카카오 회원 탈퇴 실패")
            } else {
                Timber.tag(TAG).i("카카오 회원 탈퇴 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }

    companion object {
        const val KAKAO_APP = "kakao_app"
        const val KAKAO_ACCOUNT = "kakao_account"
    }
}
