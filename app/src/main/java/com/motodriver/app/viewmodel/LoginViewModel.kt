package com.motodriver.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motodriver.app.data.model.Driver
import com.motodriver.app.data.model.DriverStatus
import com.motodriver.app.data.repository.MotoDriverRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String = "",
    val passwordError: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)

/**
 * ViewModel for the Login screen.
 * 
 * Note: Repository is instantiated directly for simplicity with mock data.
 * For production, consider using Hilt/Dagger for dependency injection
 * to improve testability and allow for different repository implementations.
 */
class LoginViewModel(
    private val repository: MotoDriverRepository = MotoDriverRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _driver = MutableStateFlow<Driver?>(null)
    val driver: StateFlow<Driver?> = _driver.asStateFlow()

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email, emailError = "")
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password, passwordError = "")
    }

    fun login() {
        val currentState = _uiState.value
        var hasError = false
        var newState = currentState.copy(emailError = "", passwordError = "", errorMessage = "")

        // Validate email
        if (currentState.email.isBlank()) {
            newState = newState.copy(emailError = "El correo es requerido")
            hasError = true
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches()) {
            newState = newState.copy(emailError = "Correo inválido")
            hasError = true
        }

        // Validate password
        if (currentState.password.isBlank()) {
            newState = newState.copy(passwordError = "La contraseña es requerida")
            hasError = true
        } else if (currentState.password.length < 6) {
            newState = newState.copy(passwordError = "La contraseña debe tener al menos 6 caracteres")
            hasError = true
        }

        if (hasError) {
            _uiState.value = newState
            return
        }

        _uiState.value = currentState.copy(isLoading = true, errorMessage = "")

        viewModelScope.launch {
            repository.login(currentState.email, currentState.password)
                .onSuccess { driver ->
                    _driver.value = driver
                    _isAuthenticated.value = true
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Error de autenticación"
                    )
                }
        }
    }

    fun logout() {
        _driver.value = null
        _isAuthenticated.value = false
        _uiState.value = LoginUiState()
    }

    fun updateDriverStatus(status: DriverStatus) {
        viewModelScope.launch {
            repository.updateDriverStatus(status)
                .onSuccess { updatedDriver ->
                    _driver.value = updatedDriver
                }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = "")
    }
}
