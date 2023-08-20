package com.sopt.geonppang.di

import com.sopt.geonppang.data.repository.*
import com.sopt.geonppang.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl,
    ): HomeRepository

    @Binds
    @Singleton
    fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl,
    ): SearchRepository

    @Binds
    @Singleton
    fun bindBakeryRepository(
        bakeryRepositoryImpl: BakeryRepositoryImpl,
    ): BakeryRepository

    @Binds
    @Singleton
    fun bindMypageRepository(
        mypageRepositoryImpl: MypageRepositoryImpl,
    ): MypageRepository

    @Binds
    @Singleton
    fun bindDetailRepository(
        detailRepositoryImpl: DetailRepositoryImpl,
    ): DetailRepository

    @Binds
    @Singleton
    fun bindReviewWritingRepository(
        reviewWritingRepositoryImpl: ReviewWritingWritingRepositoryImpl,
    ): ReviewWritingRepository

    @Binds
    @Singleton
    fun bindFilterRepository(
        filterRepositoryImpl: FilterRepositoryImpl,
    ): FilterRepository

    @Binds
    @Singleton
    fun bindMyReviewDetailRepository(
        myReviewDetailRepositoryImpl: MyReviewDetailRepositoryImpl
    ): MyReviewDetailRepository

}
