package com.motodriver.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.motodriver.app.data.model.Ride
import com.motodriver.app.ui.theme.Blue50
import com.motodriver.app.ui.theme.Blue700
import com.motodriver.app.ui.theme.Green500
import com.motodriver.app.ui.theme.GreenLight
import com.motodriver.app.ui.theme.Grey800
import com.motodriver.app.ui.theme.White
import com.motodriver.app.ui.utils.formatCurrency
import com.motodriver.app.ui.utils.formatDistance

@Composable
fun RideItem(
    ride: Ride,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .then(
                if (isSelected) {
                    Modifier.border(2.dp, Green500, RoundedCornerShape(12.dp))
                } else {
                    Modifier
                }
            )
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) GreenLight else White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header: Distance and Amount
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Distance Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Blue50)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "📍 ${formatDistance(ride.distanceFromDriver)}",
                        style = MaterialTheme.typography.labelMedium,
                        color = Blue700,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Spacer
                Box(modifier = Modifier.weight(1f))

                // Amount
                Text(
                    text = formatCurrency(ride.estimatedAmount),
                    style = MaterialTheme.typography.titleMedium,
                    color = Green500,
                    fontWeight = FontWeight.Bold
                )
            }

            // Origin Address
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Green500)
                )
                Text(
                    text = ride.originAddress,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Grey800,
                    modifier = Modifier.padding(start = 8.dp),
                    maxLines = 1
                )
            }
        }
    }
}
