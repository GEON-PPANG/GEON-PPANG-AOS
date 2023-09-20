package com.sopt.geonppang.data.interceptor

import android.app.Application
import android.content.Intent
import com.sopt.geonppang.BuildConfig
import com.sopt.geonppang.data.datasource.local.GPDataSource
import com.sopt.geonppang.presentation.auth.SignActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val gpDataSource: GPDataSource,
    private val context: Application,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authRequest =
            originalRequest.newBuilder().addHeader("Authorization", gpDataSource.accessToken)
                .build()
        val response = chain.proceed(if (gpDataSource.isLogin) authRequest else originalRequest)

        when (response.code) {
            401 -> {
                response.close()
                val refreshTokenRequest = originalRequest.newBuilder().get()
                    .url("${BuildConfig.GP_BASE_URL}auth/refresh")
                    .addHeader(ACCESS_TOKEN, gpDataSource.accessToken)
                    .addHeader(REFRESH_TOKEN, gpDataSource.refreshToken)
                    .build()
                val refreshTokenResponse = chain.proceed(refreshTokenRequest)

                if (refreshTokenResponse.isSuccessful) {
                    val responseHeader = refreshTokenResponse.headers
                    val responseAccessToken = responseHeader[ACCESS_TOKEN]
                    val responseRefreshToken = responseHeader[REFRESH_TOKEN]

                    with(gpDataSource) {
                        accessToken = BEARER_PREFIX + responseAccessToken.toString()
                        refreshToken = BEARER_PREFIX + responseRefreshToken.toString()
                    }

                    refreshTokenResponse.close()
                    val newRequest = originalRequest.newBuilder()
                        .addHeader(ACCESS_TOKEN, gpDataSource.accessToken)
                        .addHeader(REFRESH_TOKEN, gpDataSource.refreshToken)
                        .build()
                    return chain.proceed(newRequest)
                } else {
                    with(context) {
                        CoroutineScope(Dispatchers.Main).launch {
                            startActivity(
                                Intent(this@with, SignActivity::class.java)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            )
                        }
                    }
                    gpDataSource.clear()
                }
            }
        }
        return response
    }

    companion object {
        const val ACCESS_TOKEN = "Authorization"
        const val REFRESH_TOKEN = "Authorization-refresh"
        const val BEARER_PREFIX = "Bearer "
    }
}
