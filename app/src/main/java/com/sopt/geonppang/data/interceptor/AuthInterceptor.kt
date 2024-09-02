package com.sopt.geonppang.data.interceptor

import android.app.Application
import android.content.Intent
import com.sopt.geonppang.BuildConfig
import com.sopt.geonppang.data.datasource.local.GPDataSource
import com.sopt.geonppang.presentation.auth.SignActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val gpDataSource: GPDataSource,
    private val context: Application,
) : Interceptor {

    private val mutex = Mutex()

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestAccessToken = gpDataSource.accessToken // 요청한 엑세스 토큰

        var response = attemptRequest(chain, originalRequest, requestAccessToken)

        when (response.code) {
            401 -> {
                response.close()
                response = handleTokenExpiration(chain, originalRequest, requestAccessToken)
            }
        }

        return response
    }

    /**
     * 최초 요청을 시도하는 함수 (인터셉트를 위한)
     */
    private fun attemptRequest(chain: Interceptor.Chain, originalRequest: Request, accessToken: String): Response {
        val authRequest = originalRequest.newBuilder().addHeader(ACCESS_TOKEN, accessToken).build()
        return chain.proceed(
            if (gpDataSource.accessToken.isNotBlank()) {
                authRequest
            } else {
                originalRequest
            }
        )
    }

    /**
     * 토큰 만료 처리
     * @param requestAccessToken 최초 요청한 엑세스 토큰, 유효하지 않은 토큰
     * @member nowAccessToken 현재 시점에서의 토큰
     *
     */
    private fun handleTokenExpiration(chain: Interceptor.Chain, originalRequest: Request, requestAccessToken: String): Response =
        runBlocking {
            mutex.withLock {
                val nowAccessToken = gpDataSource.accessToken
                val nowRefreshToken = gpDataSource.refreshToken

                when (isTokenValid(requestAccessToken = requestAccessToken, nowAccessToken = nowAccessToken)) {
                    true -> {
                        reattemptRequest(chain, originalRequest, nowAccessToken)
                    }

                    false -> {
                        handleTokenRefresh(chain, originalRequest, nowAccessToken, nowRefreshToken)
                    }
                }
            }
        }

    /**
     * 재발급을 요청하는 엑세스 토큰이 유효한 토큰인지 확인
     * 유효하다면 재발급하지 않는다.
     * 요청한 토큰과 현재 재발급 시점에서의 토큰이 다르다면, 이전에 이미 토큰이 재발급 된 것이라고 판단
     */
    private fun isTokenValid(requestAccessToken: String, nowAccessToken: String): Boolean =
        requestAccessToken != nowAccessToken && nowAccessToken.isNotBlank()

    /**
     * 토큰 갱신 및 처리
     */
    private fun handleTokenRefresh(
        chain: Interceptor.Chain,
        originalRequest: Request,
        accessToken: String,
        refreshToken: String
    ): Response {
        val refreshTokenResponse = refreshToken(chain, originalRequest, accessToken, refreshToken)

        when (refreshTokenResponse.isSuccessful) {
            true -> {
                return handleSuccessfulTokenRefresh(chain, originalRequest, refreshTokenResponse)
            }

            false -> {
                return handleFailedTokenRefresh(refreshTokenResponse)
            }
        }
    }

    /**
     * 토큰 재발급 성공 처리
     */
    private fun handleSuccessfulTokenRefresh(
        chain: Interceptor.Chain,
        originalRequest: Request,
        refreshTokenResponse: Response,
    ): Response {
        val responseHeader = refreshTokenResponse.headers
        val responseAccessToken = responseHeader[ACCESS_TOKEN]
        val responseRefreshToken = responseHeader[REFRESH_TOKEN]

        with(gpDataSource) {
            accessToken = BEARER_PREFIX + responseAccessToken.toString()
            refreshToken = BEARER_PREFIX + responseRefreshToken.toString()
        }

        refreshTokenResponse.close()

        // 새 토큰으로 원래 요청을 다시 시도
        return reattemptRequest(chain, originalRequest, GPDataSource.ACCESS_TOKEN)
    }

    /**
     * 토큰 재발급 실패 처리
     */
    private fun handleFailedTokenRefresh(refreshTokenResponse: Response): Response {
        refreshTokenResponse.close()

        // 로그아웃 처리 및 로그인 화면으로 이동
        CoroutineScope(Dispatchers.Main).launch {
            context.startActivity(
                Intent(context, SignActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
        gpDataSource.clear()

        return refreshTokenResponse
    }

    /**
     * 토큰 재발급 api 호출
     */
    private fun refreshToken(
        chain: Interceptor.Chain,
        originalRequest: Request,
        accessToken: String,
        refreshToken: String,
    ): Response {
        val refreshTokenRequest = originalRequest.newBuilder().get()
            .url("${BuildConfig.GP_BASE_URL}auth/refresh")
            .addHeader(ACCESS_TOKEN, accessToken)
            .addHeader(REFRESH_TOKEN, refreshToken)
            .build()
        val refreshTokenResponse = chain.proceed(refreshTokenRequest)
        return refreshTokenResponse
    }

    /**
     * 재요청
     */
    private fun reattemptRequest(
        chain: Interceptor.Chain,
        originalRequest: Request,
        accessToken: String
    ): Response {
        val newRequest = originalRequest.newBuilder().addHeader(ACCESS_TOKEN, accessToken).build()
        return chain.proceed(newRequest)
    }

    companion object {
        const val ACCESS_TOKEN = "Authorization"
        const val REFRESH_TOKEN = "Authorization-refresh"
        const val BEARER_PREFIX = "Bearer "
    }
}