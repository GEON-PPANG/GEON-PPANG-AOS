package com.sopt.geonppang.data.interceptor

import com.sopt.geonppang.data.datasource.local.GPDataStore
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val gpDataStore: GPDataStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authRequest =
            originalRequest.newBuilder().addHeader("Authorization", gpDataStore.accessToken)
                .build()
        val response = chain.proceed(authRequest)

        when (response.code) {
            401 -> {
                // TODO 토큰 재발급 api 연동
            }
        }
        return response
    }
}
