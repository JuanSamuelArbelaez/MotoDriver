package com.motodriver.app.ui.navigation

/**
 * Navigation routes for the app
 */
sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object AvailableRides : Screen("available_rides")
    data object CurrentRide : Screen("current_ride/{rideId}") {
        fun createRoute(rideId: String) = "current_ride/$rideId"
    }
}
