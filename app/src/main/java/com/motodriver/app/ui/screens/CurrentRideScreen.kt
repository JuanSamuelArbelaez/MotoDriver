package com.motodriver.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.motodriver.app.ui.components.ButtonVariant
import com.motodriver.app.ui.components.MotoButton
import com.motodriver.app.ui.components.MotoInput
import com.motodriver.app.ui.theme.Background
import com.motodriver.app.ui.theme.Green500
import com.motodriver.app.ui.theme.Green50
import com.motodriver.app.ui.theme.Grey100
import com.motodriver.app.ui.theme.Grey50
import com.motodriver.app.ui.theme.Grey600
import com.motodriver.app.ui.theme.Grey900
import com.motodriver.app.ui.theme.Red500
import com.motodriver.app.ui.theme.White
import com.motodriver.app.ui.utils.formatCurrency
import com.motodriver.app.ui.utils.formatDistance
import com.motodriver.app.viewmodel.CurrentRideViewModel

@Composable
fun CurrentRideScreen(
    viewModel: CurrentRideViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Show error messages
    LaunchedEffect(uiState.errorMessage) {
        if (uiState.errorMessage.isNotEmpty()) {
            Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    // Show success messages
    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage.isNotEmpty()) {
            Toast.makeText(context, uiState.successMessage, Toast.LENGTH_SHORT).show()
            viewModel.clearSuccess()
        }
    }

    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Green500)
        }
        return
    }

    val ride = uiState.ride
    val client = uiState.client

    if (ride == null || client == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Error al cargar la información")
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = White,
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "← Volver",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Green500,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .clickable(onClick = onNavigateBack)
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "Carrera Actual",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Grey900,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // Client Information Section
            Text(
                text = "Información del Cliente",
                style = MaterialTheme.typography.titleSmall,
                color = Grey900,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Green500),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = client.name.firstOrNull()?.uppercase() ?: "?",
                            style = MaterialTheme.typography.headlineSmall,
                            color = White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column(
                        modifier = Modifier.padding(start = 12.dp)
                    ) {
                        Text(
                            text = client.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = Grey900,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = client.phone,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Grey600
                        )
                    }
                }
            }

            // Ride Details Section
            Text(
                text = "Detalles de la Carrera",
                style = MaterialTheme.typography.titleSmall,
                color = Grey900,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Origin
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(Green500)
                        )
                        Column(
                            modifier = Modifier.padding(start = 12.dp)
                        ) {
                            Text(
                                text = "Origen",
                                style = MaterialTheme.typography.labelSmall,
                                color = Grey600
                            )
                            Text(
                                text = ride.originAddress,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Grey900,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Divider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = Grey100
                    )

                    // Destination
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(Red500)
                        )
                        Column(
                            modifier = Modifier.padding(start = 12.dp)
                        ) {
                            Text(
                                text = "Destino",
                                style = MaterialTheme.typography.labelSmall,
                                color = Grey600
                            )
                            Text(
                                text = ride.destinationAddress,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Grey900,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Details Grid
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Grey50)
                                .padding(12.dp)
                        ) {
                            Column {
                                Text(
                                    text = "Distancia",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Grey600
                                )
                                Text(
                                    text = formatDistance(ride.tripDistance),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Grey900,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Grey50)
                                .padding(12.dp)
                        ) {
                            Column {
                                Text(
                                    text = "Tarifa",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Grey600
                                )
                                Text(
                                    text = formatCurrency(ride.estimatedAmount),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Green500,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // OTP Validation Section
            if (!uiState.isOtpValidated) {
                Text(
                    text = "Validación de Seguridad",
                    style = MaterialTheme.typography.titleSmall,
                    color = Grey900,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Solicita al cliente el código OTP de 4 dígitos",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Grey600,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        MotoInput(
                            value = uiState.otp,
                            onValueChange = viewModel::updateOtp,
                            placeholder = "Ingresa el código",
                            error = uiState.otpError,
                            keyboardType = KeyboardType.Number,
                            maxLength = 4,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        MotoButton(
                            text = "Validar código",
                            onClick = viewModel::validateOtp,
                            loading = uiState.isValidating
                        )
                    }
                }
            }

            // OTP Validated - Start Ride Section
            if (uiState.isOtpValidated) {
                // Success Banner
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Green50)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "✓",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Green500,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "Código validado correctamente",
                        style = MaterialTheme.typography.titleSmall,
                        color = Green500,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                MotoButton(
                    text = "Iniciar carrera",
                    onClick = {
                        viewModel.startRide {
                            onNavigateBack()
                        }
                    },
                    loading = uiState.isStarting,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            // Cancel Button
            MotoButton(
                text = "Cancelar carrera",
                onClick = onNavigateBack,
                variant = ButtonVariant.SECONDARY,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}
