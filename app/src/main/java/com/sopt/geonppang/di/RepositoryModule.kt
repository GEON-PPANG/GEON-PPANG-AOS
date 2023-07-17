package com.sopt.geonppang.di

import com.sopt.geonppang.data.repository.DummyRepositoryImpl
import com.sopt.geonppang.data.repository.HomeRepositoryImpl
import com.sopt.geonppang.domain.repository.DummyRepository
import com.sopt.geonppang.domain.repository.HomeRepository
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
}
