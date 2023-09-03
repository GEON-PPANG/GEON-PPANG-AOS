package com.sopt.geonppang.di

import com.sopt.geonppang.data.service.*
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

    @Singleton
    @Provides
    fun provideWriteReviewService(retrofit: Retrofit): ReviewWritingService =
        retrofit.create(ReviewWritingService::class.java)

    @Singleton
    @Provides
    fun provideFilterService(retrofit: Retrofit): FilterService =
        retrofit.create(FilterService::class.java)

    @Singleton
    @Provides
    fun provideMyReviewDetailService(retrofit: Retrofit): MyReviewDetailService =
        retrofit.create(MyReviewDetailService::class.java)

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)
}
