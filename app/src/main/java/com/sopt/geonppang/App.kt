package com.sopt.geonppang

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.sopt.geonppang.BuildConfig.KAKAO_APP_KEY
import com.sopt.geonppang.util.AmplitudeUtils
import com.sopt.geonppang.util.AmplitudeUtils.initAmplitude
import com.sopt.geonppang.util.GPDebugTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, KAKAO_APP_KEY)

        if (BuildConfig.DEBUG) Timber.plant(GPDebugTree())

        initAmplitude(applicationContext)
        AmplitudeUtils.trackEvent(OPEN_APP)
    }

    companion object {
        const val OPEN_APP = "open_app"
    }
}
