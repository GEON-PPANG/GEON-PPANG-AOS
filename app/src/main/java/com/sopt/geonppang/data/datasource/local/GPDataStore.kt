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
class GPDataStore @Inject constructor(@ApplicationContext context: Context) {
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

    fun clear() {
        dataStore.edit {
            clear()
        }
    }

    var userNickname: String
        set(value) = dataStore.edit { putString(NICKNAME, value) }
        get() = dataStore.getString(NICKNAME, "") ?: ""

    companion object {
        const val FILE_NAME = "Gpdatastore"
        const val ACCESS_TOKEN = "AccessToken"
        const val REFRESH_TOKEN = "RefreshToken"
        const val IS_LOGIN = "IsLogin"
        const val NICKNAME = "nickname"
    }
}
