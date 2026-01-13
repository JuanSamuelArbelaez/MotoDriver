package com.motodriver.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.motodriver.app.data.model.Driver
import com.motodriver.app.data.model.DriverStatus
import com.motodriver.app.ui.theme.Blue500
import com.motodriver.app.ui.theme.Green500
import com.motodriver.app.ui.theme.Grey100
import com.motodriver.app.ui.theme.Grey50
import com.motodriver.app.ui.theme.Grey500
import com.motodriver.app.ui.theme.Grey600
import com.motodriver.app.ui.theme.Grey900
import com.motodriver.app.ui.theme.Orange500
import com.motodriver.app.ui.theme.White

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DriverHeader(
    driver: Driver,
    onStatusChange: (DriverStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = White,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Driver Info Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Green500),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = driver.name.firstOrNull()?.uppercase() ?: "?",
                        style = MaterialTheme.typography.headlineSmall,
                        color = White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {
                    Text(
                        text = driver.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = Grey900,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = driver.vehiclePlate,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Grey600
                    )
                }
            }

            // Status Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = "Estado:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Grey600,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DriverStatus.entries.forEach { status ->
                        StatusChip(
                            status = status,
                            isSelected = driver.status == status,
                            onClick = { onStatusChange(status) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusChip(
    status: DriverStatus,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val statusColor = when (status) {
        DriverStatus.ACTIVE -> Color(0xFF4CAF50)
        DriverStatus.INACTIVE -> Grey500
        DriverStatus.EN_ROUTE -> Orange500
        DriverStatus.IN_RIDE -> Blue500
    }

    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = {
            Text(
                text = status.displayName,
                style = MaterialTheme.typography.labelMedium
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Grey50,
            labelColor = Grey600,
            selectedContainerColor = statusColor,
            selectedLabelColor = White
        ),
        border = FilterChipDefaults.filterChipBorder(
            borderColor = Grey100,
            selectedBorderColor = statusColor,
            enabled = true,
            selected = isSelected
        ),
        shape = RoundedCornerShape(16.dp)
    )
}
