package com.motodriver.app.data.model

/**
 * Location model representing GPS coordinates
 */
data class Location(
    val latitude: Double,
    val longitude: Double
)

/**
 * Driver status enum representing the current state of a moto-taxista
 */
enum class DriverStatus(val displayName: String) {
    ACTIVE("Activo"),
    INACTIVE("Inactivo"),
    EN_ROUTE("En ruta"),
    IN_RIDE("En carrera")
}

/**
 * Driver model representing a moto-taxista
 */
data class Driver(
    val id: String,
    val name: String,
    val phone: String,
    val vehiclePlate: String,
    val status: DriverStatus,
    val currentLocation: Location? = null,
    val rating: Double? = null
)
