package com.motodriver.app.data.model

import java.util.Date

/**
 * Ride status enum representing the current state of a carrera
 */
enum class RideStatus {
    AVAILABLE,
    ACCEPTED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}

/**
 * Ride model representing a carrera
 */
data class Ride(
    val id: String,
    val clientId: String,
    val originAddress: String,
    val destinationAddress: String,
    val originLocation: Location,
    val destinationLocation: Location,
    val estimatedAmount: Int,
    val distanceFromDriver: Double, // in kilometers
    val tripDistance: Double, // in kilometers
    val status: RideStatus,
    val createdAt: Date,
    val otp: String? = null
)
