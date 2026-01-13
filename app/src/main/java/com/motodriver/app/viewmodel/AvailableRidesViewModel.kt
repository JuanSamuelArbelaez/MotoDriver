package com.motodriver.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motodriver.app.data.model.Ride
import com.motodriver.app.data.model.RideStatus
import com.motodriver.app.data.repository.MotoDriverRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AvailableRidesUiState(
    val rides: List<Ride> = emptyList(),
    val selectedRide: Ride? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isAccepting: Boolean = false,
    val showNotification: Boolean = false,
    val notificationRide: Ride? = null,
    val errorMessage: String = ""
)

/**
 * ViewModel for the Available Rides screen.
 * 
 * Note: Repository is instantiated directly for simplicity with mock data.
 * For production, consider using Hilt/Dagger for dependency injection.
 */
class AvailableRidesViewModel(
    private val repository: MotoDriverRepository = MotoDriverRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(AvailableRidesUiState())
    val uiState: StateFlow<AvailableRidesUiState> = _uiState.asStateFlow()

    init {
        loadRides()
    }

    fun loadRides() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            repository.getAvailableRides()
                .onSuccess { rides ->
                    val currentState = _uiState.value
                    _uiState.value = currentState.copy(
                        rides = rides,
                        selectedRide = currentState.selectedRide ?: rides.firstOrNull(),
                        isLoading = false,
                        isRefreshing = false
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isRefreshing = false,
                        errorMessage = exception.message ?: "Error al cargar carreras"
                    )
                }
        }
    }

    fun refresh() {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        loadRides()
    }

    fun selectRide(ride: Ride) {
        _uiState.value = _uiState.value.copy(selectedRide = ride)
    }

    fun acceptRide(ride: Ride, onSuccess: (String) -> Unit) {
        _uiState.value = _uiState.value.copy(isAccepting = true)

        viewModelScope.launch {
            repository.acceptRide(ride.id)
                .onSuccess { acceptedRide ->
                    _uiState.value = _uiState.value.copy(isAccepting = false)
                    onSuccess(acceptedRide.id)
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isAccepting = false,
                        errorMessage = exception.message ?: "Error al aceptar carrera"
                    )
                }
        }
    }

    fun showNotificationForNearbyRide(isDriverActive: Boolean) {
        if (!isDriverActive) return

        val nearbyRide = _uiState.value.rides.find { it.distanceFromDriver <= 1.0 }
        if (nearbyRide != null) {
            _uiState.value = _uiState.value.copy(
                showNotification = true,
                notificationRide = nearbyRide
            )
        }
    }

    fun dismissNotification() {
        _uiState.value = _uiState.value.copy(
            showNotification = false,
            notificationRide = null
        )
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = "")
    }
}
