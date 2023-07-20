package com.sopt.geonppang.di

import com.sopt.geonppang.data.service.BakeryService
import com.sopt.geonppang.data.service.DetailService
import com.sopt.geonppang.data.service.DummyService
import com.sopt.geonppang.data.service.HomeService
import com.sopt.geonppang.data.service.MypageService
import com.sopt.geonppang.data.service.SearchService
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
    fun provideHomeService(retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)

    @Singleton
    @Provides
    fun provideSearchService(retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)

    @Singleton
    @Provides
    fun provideBakeryService(retrofit: Retrofit): BakeryService =
        retrofit.create(BakeryService::class.java)

    @Singleton
    @Provides
    fun provideMypageService(retrofit: Retrofit): MypageService =
        retrofit.create(MypageService::class.java)

    @Singleton
    @Provides
    fun provideDetailService(retrofit: Retrofit): DetailService =
        retrofit.create(DetailService::class.java)
}
