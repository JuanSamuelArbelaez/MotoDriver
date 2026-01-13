package com.motodriver.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.motodriver.app.data.model.Ride
import com.motodriver.app.ui.theme.Green500
import com.motodriver.app.ui.theme.Grey100
import com.motodriver.app.ui.theme.Grey50
import com.motodriver.app.ui.theme.Grey600
import com.motodriver.app.ui.theme.Grey900
import com.motodriver.app.ui.theme.Red500
import com.motodriver.app.ui.theme.White
import com.motodriver.app.ui.utils.formatCurrency
import com.motodriver.app.ui.utils.formatDistance

@Composable
fun RideOverlay(
    ride: Ride?,
    onAccept: () -> Unit,
    loading: Boolean = false,
    modifier: Modifier = Modifier
) {
    if (ride == null) return

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        color = White,
        shadowElevation = 10.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            // Handle Bar
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Grey100)
                    .align(Alignment.CenterHorizontally)
            )

            // Title
            Text(
                text = "Carrera seleccionada",
                style = MaterialTheme.typography.titleMedium,
                color = Grey900,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )

            // Origin
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
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
                modifier = Modifier.padding(start = 32.dp, vertical = 8.dp),
                color = Grey100
            )

            // Destination
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
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

            // Details Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Grey50)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
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
                Column {
                    Text(
                        text = "Tarifa estimada",
                        style = MaterialTheme.typography.labelSmall,
                        color = Grey600
                    )
                    Text(
                        text = formatCurrency(ride.estimatedAmount),
                        style = MaterialTheme.typography.titleMedium,
                        color = Grey900,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Accept Button
            MotoButton(
                text = "Aceptar carrera",
                onClick = onAccept,
                loading = loading,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )
        }
    }
}
