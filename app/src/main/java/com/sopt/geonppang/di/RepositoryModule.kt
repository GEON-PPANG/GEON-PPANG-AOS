package com.sopt.geonppang.di

import com.sopt.geonppang.data.repository.BakeryRepositoryImpl
import com.sopt.geonppang.data.repository.DummyRepositoryImpl
import com.sopt.geonppang.data.repository.HomeRepositoryImpl
import com.sopt.geonppang.data.repository.SearchRepositoryImpl
import com.sopt.geonppang.domain.repository.BakeryRepository
import com.sopt.geonppang.domain.repository.DummyRepository
import com.sopt.geonppang.domain.repository.HomeRepository
import com.sopt.geonppang.domain.repository.SearchRepository
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
    fun bindDummyRepository(
        dummyRepositoryImpl: DummyRepositoryImpl,
    ): DummyRepository

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
}
