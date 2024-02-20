package com.sopt.geonppang.data.datasource.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.sopt.geonppang.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GPDataSource @Inject constructor(@ApplicationContext context: Context) {
    private val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val dataStore: SharedPreferences =
        if (BuildConfig.DEBUG) context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        else EncryptedSharedPreferences.create(
            context,
            FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    // TODO 자료형 PlatformType으로 수정
    var platformType: String
        set(value) = dataStore.edit { putString(PLATFORM_TYPE, value) }
        get() = dataStore.getString(
            PLATFORM_TYPE,
            ""
        ) ?: ""

    var accessToken: String
        set(value) = dataStore.edit { putString(ACCESS_TOKEN, value) }
        get() = dataStore.getString(
            ACCESS_TOKEN,
            ""
        ) ?: ""

    var refreshToken: String
        set(value) = dataStore.edit { putString(REFRESH_TOKEN, value) }
        get() = dataStore.getString(
            REFRESH_TOKEN,
            ""
        ) ?: ""

    var isLogin: Boolean
        set(value) = dataStore.edit { putBoolean(IS_LOGIN, value) }
        get() = dataStore.getBoolean(IS_LOGIN, false)

    // TODO: all 앱 내에서의 userRoleType 어떻게 관리할 것인지 논의
    var userRoleType: String
        set(value) = dataStore.edit { putString(USER_ROLE_TYPE, value) }
        get() = dataStore.getString(
            USER_ROLE_TYPE,
            ""
        ) ?: ""

    fun clear() {
        dataStore.edit {
            clear()
        }
    }

    companion object {
        const val FILE_NAME = "Gpdatastore"
        const val PLATFORM_TYPE = "platforType"
        const val ACCESS_TOKEN = "AccessToken"
        const val REFRESH_TOKEN = "RefreshToken"
        const val IS_LOGIN = "IsLogin"
        const val USER_ROLE_TYPE = "UserRoleType"
    }
}
