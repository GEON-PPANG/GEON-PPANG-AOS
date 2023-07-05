package com.sopt.geonppang

import android.app.Application
import com.sopt.geonppang.util.GPDebugTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(GPDebugTree())
    }
}
