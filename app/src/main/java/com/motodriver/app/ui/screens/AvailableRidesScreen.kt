package com.motodriver.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.motodriver.app.data.model.Driver
import com.motodriver.app.data.model.DriverStatus
import com.motodriver.app.ui.components.DriverHeader
import com.motodriver.app.ui.components.NotificationPopup
import com.motodriver.app.ui.components.RideItem
import com.motodriver.app.ui.components.RideOverlay
import com.motodriver.app.ui.theme.Background
import com.motodriver.app.ui.theme.Grey600
import com.motodriver.app.ui.theme.Grey900
import com.motodriver.app.viewmodel.AvailableRidesViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvailableRidesScreen(
    driver: Driver,
    viewModel: AvailableRidesViewModel,
    onStatusChange: (DriverStatus) -> Unit,
    onNavigateToCurrentRide: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Show notification popup after delay for active drivers
    LaunchedEffect(driver.status) {
        if (driver.status == DriverStatus.ACTIVE) {
            delay(5000)
            viewModel.showNotificationForNearbyRide(true)
        }
    }

    // Show error messages
    LaunchedEffect(uiState.errorMessage) {
        if (uiState.errorMessage.isNotEmpty()) {
            Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            // Driver Header
            DriverHeader(
                driver = driver,
                onStatusChange = onStatusChange
            )

            // Rides List with Pull-to-Refresh
            PullToRefreshBox(
                isRefreshing = uiState.isRefreshing,
                onRefresh = viewModel::refresh,
                modifier = Modifier.weight(1f)
            ) {
                if (uiState.rides.isEmpty() && !uiState.isLoading) {
                    // Empty State
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                    ) {
                        Text(
                            text = "🔍",
                            fontSize = 64.sp
                        )
                        Text(
                            text = "No hay carreras disponibles",
                            style = MaterialTheme.typography.titleMedium,
                            color = Grey900,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Text(
                            text = "Las nuevas carreras aparecerán aquí automáticamente",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Grey600,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp,
                            bottom = 320.dp // Space for overlay
                        )
                    ) {
                        items(uiState.rides) { ride ->
                            RideItem(
                                ride = ride,
                                isSelected = uiState.selectedRide?.id == ride.id,
                                onClick = { viewModel.selectRide(ride) }
                            )
                        }
                    }
                }
            }
        }

        // Ride Overlay at the bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            RideOverlay(
                ride = uiState.selectedRide,
                onAccept = {
                    uiState.selectedRide?.let { ride ->
                        viewModel.acceptRide(ride) { rideId ->
                            Toast.makeText(context, "Carrera aceptada exitosamente", Toast.LENGTH_SHORT).show()
                            onNavigateToCurrentRide(rideId)
                        }
                    }
                },
                loading = uiState.isAccepting
            )
        }

        // Notification Popup
        NotificationPopup(
            visible = uiState.showNotification,
            ride = uiState.notificationRide,
            onAccept = {
                viewModel.dismissNotification()
                uiState.notificationRide?.let { ride ->
                    viewModel.acceptRide(ride) { rideId ->
                        Toast.makeText(context, "Carrera aceptada exitosamente", Toast.LENGTH_SHORT).show()
                        onNavigateToCurrentRide(rideId)
                    }
                }
            },
            onReject = viewModel::dismissNotification
        )
    }
}
