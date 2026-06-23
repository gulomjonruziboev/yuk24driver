package uz.yuk24.driver.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.yuk24.driver.data.repository.AuthRepository
import uz.yuk24.driver.data.repository.LoginResult
import javax.inject.Inject

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess.asStateFlow()

    fun onUsernameChange(value: String) {
        _uiState.update { it.copy(username = value, errorMessage = null) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value, errorMessage = null) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    fun login() {
        val state = _uiState.value
        if (state.username.isBlank() || state.password.isBlank()) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = authRepository.login(state.username.trim(), state.password)) {
                is LoginResult.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _loginSuccess.value = true
                }
                LoginResult.InvalidCredentials -> _uiState.update {
                    it.copy(isLoading = false, errorMessage = "invalid")
                }
                LoginResult.InactiveAccount -> _uiState.update {
                    it.copy(isLoading = false, errorMessage = "inactive")
                }
                LoginResult.RateLimited -> _uiState.update {
                    it.copy(isLoading = false, errorMessage = "rate_limited")
                }
                LoginResult.NetworkError -> _uiState.update {
                    it.copy(isLoading = false, errorMessage = "network")
                }
                is LoginResult.Error -> _uiState.update {
                    it.copy(isLoading = false, errorMessage = result.message)
                }
            }
        }
    }
}
