package com.sopt.geonppang.util

import android.content.Context
import com.amplitude.android.Amplitude
import com.amplitude.android.Configuration
import com.sopt.geonppang.BuildConfig

object AmplitudeUtils {
    private lateinit var amplitude: Amplitude

    fun initAmplitude(applicationContext: Context) {
        amplitude = Amplitude(
            Configuration(
                apiKey = BuildConfig.AMPLITUDE_API_KEY,
                context = applicationContext
            )
        )
    }

    fun trackEvent(eventName: String) {
        amplitude.track(eventName)
    }

    fun <T> trackEventWithProperties(eventName: String, propertyName: String, propertyValue: T) {
        amplitude.track(eventName, mapOf(propertyName to propertyValue))
    }
}
