package com.motodriver.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.motodriver.app.ui.theme.Green500
import com.motodriver.app.ui.theme.Grey400
import com.motodriver.app.ui.theme.Red500
import com.motodriver.app.ui.theme.White

enum class ButtonVariant {
    PRIMARY, SECONDARY, DANGER
}

@Composable
fun MotoButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.PRIMARY,
    enabled: Boolean = true,
    loading: Boolean = false
) {
    val backgroundColor = when {
        !enabled || loading -> Grey400
        variant == ButtonVariant.DANGER -> Red500
        variant == ButtonVariant.PRIMARY -> Green500
        else -> Color.Transparent
    }

    val contentColor = when (variant) {
        ButtonVariant.SECONDARY -> Green500
        else -> White
    }

    if (variant == ButtonVariant.SECONDARY) {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = enabled && !loading,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Green500,
                disabledContentColor = Grey400
            )
        ) {
            if (loading) {
                CircularProgressIndicator(
                    color = Green500,
                    modifier = Modifier.height(24.dp)
                )
            } else {
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    } else {
        Button(
            onClick = onClick,
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = enabled && !loading,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = contentColor,
                disabledContainerColor = Grey400,
                disabledContentColor = White
            )
        ) {
            if (loading) {
                CircularProgressIndicator(
                    color = White,
                    modifier = Modifier.height(24.dp)
                )
            } else {
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
