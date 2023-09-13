package com.sopt.geonppang.di

import com.sopt.geonppang.data.datasource.local.GPDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object KakaoModule {
    private val json = Json { ignoreUnknownKeys = true }
    private const val CONTENT_TYPE = "Content-Type"
    private const val APPLICATION_JSON = "application/json"
    private const val PLATFORM_TOKEN = "Platform-Token"

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class GPType

    @GPType
    @Provides
    fun providesGPInterceptor(gpDataSource: GPDataStore): Interceptor =
        Interceptor { chain ->
            with(chain) {
                proceed(
                    request()
                        .newBuilder()
                        .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .addHeader(PLATFORM_TOKEN, gpDataSource.accessToken)
                        .build()
                )
            }
        }
}