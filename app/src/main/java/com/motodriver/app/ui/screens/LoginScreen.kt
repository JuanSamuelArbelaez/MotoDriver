package com.motodriver.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.motodriver.app.ui.components.MotoButton
import com.motodriver.app.ui.components.MotoInput
import com.motodriver.app.ui.theme.Background
import com.motodriver.app.ui.theme.Green500
import com.motodriver.app.ui.theme.Grey600
import com.motodriver.app.ui.theme.Grey900
import com.motodriver.app.ui.theme.White
import com.motodriver.app.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val isAuthenticated by viewModel.isAuthenticated.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    // Navigate on successful login
    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated) {
            onLoginSuccess()
        }
    }

    // Show error message
    LaunchedEffect(uiState.errorMessage) {
        if (uiState.errorMessage.isNotEmpty()) {
            Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(paddingValues)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo Section
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Green500),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🏍️",
                    fontSize = 50.sp
                )
            }

            Text(
                text = "MotoDriver",
                style = MaterialTheme.typography.headlineLarge,
                color = Grey900,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = "Bienvenido de vuelta",
                style = MaterialTheme.typography.bodyLarge,
                color = Grey600,
                modifier = Modifier.padding(top = 8.dp, bottom = 48.dp)
            )

            // Form Section
            MotoInput(
                value = uiState.email,
                onValueChange = viewModel::updateEmail,
                label = "Correo electrónico",
                placeholder = "ejemplo@correo.com",
                error = uiState.emailError,
                keyboardType = KeyboardType.Email,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            MotoInput(
                value = uiState.password,
                onValueChange = viewModel::updatePassword,
                label = "Contraseña",
                placeholder = "••••••••",
                error = uiState.passwordError,
                isPassword = true,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            MotoButton(
                text = "Iniciar sesión",
                onClick = viewModel::login,
                loading = uiState.isLoading
            )

            Text(
                text = "¿Olvidaste tu contraseña? Contacta al administrador",
                style = MaterialTheme.typography.bodySmall,
                color = Grey600,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 24.dp)
            )
        }
    }
}
