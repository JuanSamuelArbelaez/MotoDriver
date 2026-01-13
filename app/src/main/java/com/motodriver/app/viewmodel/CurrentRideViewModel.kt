package com.motodriver.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motodriver.app.data.model.Client
import com.motodriver.app.data.model.Ride
import com.motodriver.app.data.repository.MotoDriverRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CurrentRideUiState(
    val ride: Ride? = null,
    val client: Client? = null,
    val otp: String = "",
    val otpError: String = "",
    val isOtpValidated: Boolean = false,
    val isLoading: Boolean = false,
    val isValidating: Boolean = false,
    val isStarting: Boolean = false,
    val errorMessage: String = "",
    val successMessage: String = ""
)

/**
 * ViewModel for the Current Ride screen.
 * 
 * Note: Repository is instantiated directly for simplicity with mock data.
 * For production, consider using Hilt/Dagger for dependency injection.
 */
class CurrentRideViewModel(
    private val rideId: String,
    private val repository: MotoDriverRepository = MotoDriverRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(CurrentRideUiState())
    val uiState: StateFlow<CurrentRideUiState> = _uiState.asStateFlow()

    init {
        loadRideData()
    }

    private fun loadRideData() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            repository.getRide(rideId)
                .onSuccess { ride ->
                    _uiState.value = _uiState.value.copy(ride = ride)
                    loadClient(ride.clientId)
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Error al cargar la carrera"
                    )
                }
        }
    }

    private fun loadClient(clientId: String) {
        viewModelScope.launch {
            repository.getClient(clientId)
                .onSuccess { client ->
                    _uiState.value = _uiState.value.copy(
                        client = client,
                        isLoading = false
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Error al cargar datos del cliente"
                    )
                }
        }
    }

    fun updateOtp(otp: String) {
        _uiState.value = _uiState.value.copy(otp = otp, otpError = "")
    }

    fun validateOtp() {
        val currentOtp = _uiState.value.otp

        if (currentOtp.isBlank()) {
            _uiState.value = _uiState.value.copy(otpError = "El código OTP es requerido")
            return
        }

        if (currentOtp.length != 4) {
            _uiState.value = _uiState.value.copy(otpError = "El código debe tener 4 dígitos")
            return
        }

        _uiState.value = _uiState.value.copy(isValidating = true, otpError = "")

        viewModelScope.launch {
            repository.validateOtp(rideId, currentOtp)
                .onSuccess { isValid ->
                    if (isValid) {
                        _uiState.value = _uiState.value.copy(
                            isOtpValidated = true,
                            isValidating = false,
                            successMessage = "OTP validado correctamente"
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isValidating = false,
                        otpError = exception.message ?: "Código OTP inválido"
                    )
                }
        }
    }

    fun startRide(onSuccess: () -> Unit) {
        _uiState.value = _uiState.value.copy(isStarting = true)

        viewModelScope.launch {
            repository.startRide(rideId)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isStarting = false,
                        successMessage = "Carrera iniciada exitosamente"
                    )
                    onSuccess()
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isStarting = false,
                        errorMessage = exception.message ?: "Error al iniciar la carrera"
                    )
                }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = "")
    }

    fun clearSuccess() {
        _uiState.value = _uiState.value.copy(successMessage = "")
    }
}
