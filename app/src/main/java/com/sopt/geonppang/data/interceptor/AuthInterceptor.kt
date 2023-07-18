package com.sopt.geonppang.data.interceptor

import com.sopt.geonppang.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authRequest =
            originalRequest.newBuilder().addHeader("Authorization   ", BuildConfig.ACCESS_TOKEN).build()
        val response = chain.proceed(authRequest)

        when (response.code) {
            401 -> {
                // TODO 토큰 재발급 api 연동
            }
        }
        return response
    }
}
