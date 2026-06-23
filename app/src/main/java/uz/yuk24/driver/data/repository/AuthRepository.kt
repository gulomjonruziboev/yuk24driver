package uz.yuk24.driver.data.repository

import uz.yuk24.driver.data.local.TokenStore
import uz.yuk24.driver.data.remote.ApiResult
import uz.yuk24.driver.data.remote.api.DriverApiService
import uz.yuk24.driver.data.remote.dto.LoginRequest
import uz.yuk24.driver.data.remote.safeApiCall
import uz.yuk24.driver.domain.model.AuthSession
import javax.inject.Inject
import javax.inject.Singleton

sealed class LoginResult {
    data class Success(val session: AuthSession) : LoginResult()
    data object InvalidCredentials : LoginResult()
    data object InactiveAccount : LoginResult()
    data object RateLimited : LoginResult()
    data object NetworkError : LoginResult()
    data class Error(val message: String) : LoginResult()
}

@Singleton
class AuthRepository @Inject constructor(
    private val api: DriverApiService,
    private val tokenStore: TokenStore
) {
    fun getStoredSession(): AuthSession? = tokenStore.getSession()

    fun getToken(): String? = tokenStore.getToken()

    suspend fun login(username: String, password: String): LoginResult {
        return when (val result = safeApiCall { api.login(LoginRequest(username, password)) }) {
            is ApiResult.Success -> {
                val response = result.data
                val userId = response.user.id
                tokenStore.saveSession(
                    token = response.token,
                    userId = userId,
                    username = response.user.username,
                    name = response.user.name
                )
                LoginResult.Success(
                    AuthSession(
                        token = response.token,
                        userId = userId,
                        username = response.user.username,
                        name = response.user.name,
                        loginAt = System.currentTimeMillis()
                    )
                )
            }
            is ApiResult.Error -> when (result.code) {
                401 -> LoginResult.InvalidCredentials
                403 -> LoginResult.InactiveAccount
                429 -> LoginResult.RateLimited
                else -> LoginResult.Error(result.message)
            }
            is ApiResult.NetworkError -> LoginResult.NetworkError
        }
    }

    suspend fun validateSession(): Boolean {
        val token = tokenStore.getToken() ?: return false
        return when (val result = safeApiCall { api.getMe() }) {
            is ApiResult.Success -> true
            is ApiResult.Error -> {
                if (result.code == 401) tokenStore.clear()
                false
            }
            is ApiResult.NetworkError -> token.isNotBlank()
        }
    }
}
