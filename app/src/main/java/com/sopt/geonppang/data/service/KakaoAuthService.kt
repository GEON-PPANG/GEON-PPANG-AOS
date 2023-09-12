package com.sopt.geonppang.data.service

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.Constants.TAG
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ActivityContext
import timber.log.Timber
import javax.inject.Inject


class KakaoAuthService @Inject constructor(@ActivityContext private val context: Context) {
    fun startKakaoLogin(kakaoLoginCallBack: (OAuthToken?, Throwable?) -> Unit) {
        val kakaoLoginState =
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) KAKAO_APP_LOGIN
            else KAKAO_ACCOUNT_LOGIN

        when (kakaoLoginState) {
            KAKAO_APP_LOGIN -> {
                UserApiClient.instance.loginWithKakaoTalk(
                    context,
                    callback = kakaoLoginCallBack
                )
            }

            KAKAO_ACCOUNT_LOGIN -> {
                UserApiClient.instance.loginWithKakaoAccount(
                    context,
                    callback = kakaoLoginCallBack
                )
            }
        }
    }

    fun logoutKakao() {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            } else {
                Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }
    }

    fun disconnectKakao() {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e(TAG, "연결 끊기 실패", error)
            } else {
                Log.i(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }

    companion object {
        const val KAKAO_APP_LOGIN = 0
        const val KAKAO_ACCOUNT_LOGIN = 1
    }
}
