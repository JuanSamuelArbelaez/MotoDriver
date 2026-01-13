package com.motodriver.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.motodriver.app.data.repository.MotoDriverRepository
import com.motodriver.app.ui.screens.AvailableRidesScreen
import com.motodriver.app.ui.screens.CurrentRideScreen
import com.motodriver.app.ui.screens.LoginScreen
import com.motodriver.app.viewmodel.AvailableRidesViewModel
import com.motodriver.app.viewmodel.CurrentRideViewModel
import com.motodriver.app.viewmodel.LoginViewModel

@Composable
fun AppNavigation(
    loginViewModel: LoginViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val isAuthenticated by loginViewModel.isAuthenticated.collectAsState()
    val driver by loginViewModel.driver.collectAsState()

    val startDestination = if (isAuthenticated) Screen.AvailableRides.route else Screen.Login.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.AvailableRides.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.AvailableRides.route) {
            val availableRidesViewModel: AvailableRidesViewModel = viewModel()
            driver?.let { currentDriver ->
                AvailableRidesScreen(
                    driver = currentDriver,
                    viewModel = availableRidesViewModel,
                    onStatusChange = { status ->
                        loginViewModel.updateDriverStatus(status)
                    },
                    onNavigateToCurrentRide = { rideId ->
                        navController.navigate(Screen.CurrentRide.createRoute(rideId))
                    }
                )
            }
        }

        composable(
            route = Screen.CurrentRide.route,
            arguments = listOf(
                navArgument("rideId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val rideId = backStackEntry.arguments?.getString("rideId") ?: return@composable
            val currentRideViewModel = remember(rideId) {
                CurrentRideViewModel(rideId = rideId)
            }

            CurrentRideScreen(
                viewModel = currentRideViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
