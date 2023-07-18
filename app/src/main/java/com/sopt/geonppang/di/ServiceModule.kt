package com.sopt.geonppang.di

import com.sopt.geonppang.data.service.DetailService
import com.sopt.geonppang.data.service.DummyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun provideDummyService(retrofit: Retrofit): DummyService =
        retrofit.create(DummyService::class.java)

    @Singleton
    @Provides
    fun detailService(retrofit: Retrofit): DetailService =
        retrofit.create(DetailService::class.java)
}
