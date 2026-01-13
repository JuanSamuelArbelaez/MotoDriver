package com.motodriver.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.motodriver.app.data.model.Ride
import com.motodriver.app.ui.theme.Green500
import com.motodriver.app.ui.theme.Grey50
import com.motodriver.app.ui.theme.Grey600
import com.motodriver.app.ui.theme.Grey900
import com.motodriver.app.ui.theme.Red500
import com.motodriver.app.ui.theme.White
import com.motodriver.app.ui.utils.formatCurrency
import com.motodriver.app.ui.utils.formatDistance

@Composable
fun NotificationPopup(
    visible: Boolean,
    ride: Ride?,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    if (!visible || ride == null) return

    Dialog(
        onDismissRequest = onReject,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = White,
            shadowElevation = 10.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icon
                Text(
                    text = "🚨",
                    fontSize = 48.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Title
                Text(
                    text = "¡Nueva carrera cercana!",
                    style = MaterialTheme.typography.titleLarge,
                    color = Grey900,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                // Subtitle
                Text(
                    text = "A solo ${formatDistance(ride.distanceFromDriver)} de tu ubicación",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Grey600,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp, bottom = 20.dp)
                )

                // Ride Info Card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Grey50, RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    // Origin
                    Text(
                        text = "Origen:",
                        style = MaterialTheme.typography.labelSmall,
                        color = Grey600
                    )
                    Text(
                        text = ride.originAddress,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Grey900,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Amount
                    Text(
                        text = "Tarifa:",
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

                // Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onReject,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Red500
                        )
                    ) {
                        Text(
                            text = "Rechazar",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Button(
                        onClick = onAccept,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green500,
                            contentColor = White
                        )
                    ) {
                        Text(
                            text = "Aceptar",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}
