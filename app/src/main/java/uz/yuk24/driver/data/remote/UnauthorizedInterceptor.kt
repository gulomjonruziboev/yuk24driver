package uz.yuk24.driver.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import uz.yuk24.driver.data.local.SessionManager

class UnauthorizedInterceptor(
    private val sessionManager: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.code == 401 && chain.request().header("Authorization") != null) {
            sessionManager.onUnauthorized()
        }
        return response
    }
}
