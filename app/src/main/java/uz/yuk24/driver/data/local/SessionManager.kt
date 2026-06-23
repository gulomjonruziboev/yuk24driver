package uz.yuk24.driver.data.local

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val tokenStore: TokenStore
) {
    private val _logoutEvents = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val logoutEvents: SharedFlow<Unit> = _logoutEvents.asSharedFlow()

    fun onUnauthorized() {
        tokenStore.clear()
        _logoutEvents.tryEmit(Unit)
    }

    fun logout() {
        tokenStore.clear()
        _logoutEvents.tryEmit(Unit)
    }
}
