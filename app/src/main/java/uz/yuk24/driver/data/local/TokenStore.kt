package uz.yuk24.driver.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.yuk24.driver.domain.model.AuthSession
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenStore @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs: SharedPreferences = openEncryptedPrefs(context)

    private fun openEncryptedPrefs(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        val scheme = EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV
        val valueScheme = EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        return try {
            EncryptedSharedPreferences.create(context, PREFS_NAME, masterKey, scheme, valueScheme)
        } catch (_: Exception) {
            // Keystore key rotated or prefs corrupted — wipe and start fresh (user re-logs in).
            context.deleteSharedPreferences(PREFS_NAME)
            EncryptedSharedPreferences.create(context, PREFS_NAME, masterKey, scheme, valueScheme)
        }
    }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun getSession(): AuthSession? {
        val token = getToken() ?: return null
        val userId = prefs.getString(KEY_USER_ID, null) ?: return null
        val username = prefs.getString(KEY_USERNAME, null) ?: return null
        return AuthSession(
            token = token,
            userId = userId,
            username = username,
            name = prefs.getString(KEY_NAME, null),
            loginAt = prefs.getLong(KEY_LOGIN_AT, 0L)
        )
    }

    fun saveSession(token: String, userId: String, username: String, name: String?) {
        prefs.edit()
            .putString(KEY_TOKEN, token)
            .putString(KEY_USER_ID, userId)
            .putString(KEY_USERNAME, username)
            .putString(KEY_NAME, name)
            .putLong(KEY_LOGIN_AT, System.currentTimeMillis())
            .apply()
    }

    fun clear() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val PREFS_NAME = "yuk24_driver_secure_prefs"
        private const val KEY_TOKEN = "token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USERNAME = "username"
        private const val KEY_NAME = "name"
        private const val KEY_LOGIN_AT = "login_at"
    }
}
