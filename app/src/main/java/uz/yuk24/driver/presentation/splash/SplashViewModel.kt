package uz.yuk24.driver.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.yuk24.driver.data.local.ActiveOrderStore
import uz.yuk24.driver.data.repository.AuthRepository
import uz.yuk24.driver.data.repository.HealthRepository
import javax.inject.Inject

sealed class SplashDestination {
    data object Loading : SplashDestination()
    data object Login : SplashDestination()
    data object Dashboard : SplashDestination()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val healthRepository: HealthRepository,
    private val activeOrderStore: ActiveOrderStore
) : ViewModel() {

    private val _destination = MutableStateFlow<SplashDestination>(SplashDestination.Loading)
    val destination: StateFlow<SplashDestination> = _destination.asStateFlow()

    private val _isOffline = MutableStateFlow(false)
    val isOffline: StateFlow<Boolean> = _isOffline.asStateFlow()

    init {
        viewModelScope.launch {
            when (healthRepository.checkHealth()) {
                is uz.yuk24.driver.data.remote.ApiResult.Success -> _isOffline.value = false
                else -> _isOffline.value = true
            }

            val session = authRepository.getStoredSession()
            if (session == null) {
                _destination.value = SplashDestination.Login
                return@launch
            }

            val valid = authRepository.validateSession()
            if (valid) {
                activeOrderStore.load()
                _destination.value = SplashDestination.Dashboard
            } else {
                _destination.value = SplashDestination.Login
            }
        }
    }
}
