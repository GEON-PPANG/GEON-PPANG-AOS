package com.sopt.geonppang.di

import android.content.Context
import com.sopt.geonppang.data.service.KakaoAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object KakaoModule {
    @Provides
    fun provideKakaoAuthService(
        @ActivityContext context: Context,
    ) = KakaoAuthService(context)
}
