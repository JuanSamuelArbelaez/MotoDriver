package com.motodriver.app.data.repository

import com.motodriver.app.data.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.Date

/**
 * Mock repository for development
 * This simulates backend API calls with mock data
 * 
 * Note: Uses Mutex for thread-safe access to mutable mock data.
 * In production, this would be replaced with actual API calls.
 */
class MotoDriverRepository {

    private val mutex = Mutex()
    
    private var mockDriver = Driver(
        id = "1",
        name = "Juan Pérez",
        phone = "+57 300 123 4567",
        vehiclePlate = "ABC-123",
        status = DriverStatus.ACTIVE,
        currentLocation = Location(
            latitude = 4.7110,
            longitude = -74.0721
        ),
        rating = 4.8
    )

    private val mockRides = mutableListOf(
        Ride(
            id = "ride-1",
            clientId = "client-1",
            originAddress = "Calle 72 #10-34, Bogotá",
            destinationAddress = "Carrera 7 #32-16, Bogotá",
            originLocation = Location(latitude = 4.7130, longitude = -74.0650),
            destinationLocation = Location(latitude = 4.6097, longitude = -74.0817),
            estimatedAmount = 8500,
            distanceFromDriver = 0.5,
            tripDistance = 5.2,
            status = RideStatus.AVAILABLE,
            createdAt = Date(),
            otp = "1234"
        ),
        Ride(
            id = "ride-2",
            clientId = "client-2",
            originAddress = "Carrera 15 #93-40, Bogotá",
            destinationAddress = "Avenida 68 #75-00, Bogotá",
            originLocation = Location(latitude = 4.6800, longitude = -74.0500),
            destinationLocation = Location(latitude = 4.7000, longitude = -74.1100),
            estimatedAmount = 12000,
            distanceFromDriver = 1.2,
            tripDistance = 8.5,
            status = RideStatus.AVAILABLE,
            createdAt = Date(),
            otp = "5678"
        ),
        Ride(
            id = "ride-3",
            clientId = "client-3",
            originAddress = "Calle 100 #19-61, Bogotá",
            destinationAddress = "Calle 26 #92-32, Bogotá",
            originLocation = Location(latitude = 4.6900, longitude = -74.0400),
            destinationLocation = Location(latitude = 4.6500, longitude = -74.1000),
            estimatedAmount = 15000,
            distanceFromDriver = 2.5,
            tripDistance = 12.0,
            status = RideStatus.AVAILABLE,
            createdAt = Date(),
            otp = "9012"
        )
    )

    private val mockClients = listOf(
        Client(id = "client-1", name = "María García", phone = "+57 301 234 5678"),
        Client(id = "client-2", name = "Carlos Rodríguez", phone = "+57 302 345 6789"),
        Client(id = "client-3", name = "Ana López", phone = "+57 303 456 7890")
    )

    /**
     * Simulate login
     */
    suspend fun login(email: String, password: String): Result<Driver> {
        delay(1000)
        return if (email.isNotBlank() && password.isNotBlank()) {
            Result.success(mockDriver)
        } else {
            Result.failure(Exception("Credenciales inválidas"))
        }
    }

    /**
     * Get available rides sorted by distance from driver
     */
    suspend fun getAvailableRides(): Result<List<Ride>> {
        delay(500)
        val sortedRides = mockRides
            .filter { it.status == RideStatus.AVAILABLE }
            .sortedBy { it.distanceFromDriver }
        return Result.success(sortedRides)
    }

    /**
     * Accept a ride
     */
    suspend fun acceptRide(rideId: String): Result<Ride> {
        delay(800)
        return mutex.withLock {
            val ride = mockRides.find { it.id == rideId }
            if (ride != null) {
                val index = mockRides.indexOf(ride)
                val updatedRide = ride.copy(status = RideStatus.ACCEPTED)
                mockRides[index] = updatedRide
                Result.success(updatedRide)
            } else {
                Result.failure(Exception("Carrera no encontrada"))
            }
        }
    }

    /**
     * Validate OTP
     */
    suspend fun validateOtp(rideId: String, otp: String): Result<Boolean> {
        delay(500)
        return mutex.withLock {
            val ride = mockRides.find { it.id == rideId }
            if (ride != null && ride.otp == otp) {
                Result.success(true)
            } else {
                Result.failure(Exception("OTP inválido"))
            }
        }
    }

    /**
     * Start ride
     */
    suspend fun startRide(rideId: String): Result<Ride> {
        delay(500)
        return mutex.withLock {
            val ride = mockRides.find { it.id == rideId }
            if (ride != null) {
                val index = mockRides.indexOf(ride)
                val updatedRide = ride.copy(status = RideStatus.IN_PROGRESS)
                mockRides[index] = updatedRide
                Result.success(updatedRide)
            } else {
                Result.failure(Exception("Carrera no encontrada"))
            }
        }
    }

    /**
     * Get client by ID
     */
    suspend fun getClient(clientId: String): Result<Client> {
        delay(300)
        val client = mockClients.find { it.id == clientId }
        return if (client != null) {
            Result.success(client)
        } else {
            Result.failure(Exception("Cliente no encontrado"))
        }
    }

    /**
     * Update driver status
     */
    suspend fun updateDriverStatus(status: DriverStatus): Result<Driver> {
        delay(500)
        return mutex.withLock {
            mockDriver = mockDriver.copy(status = status)
            Result.success(mockDriver)
        }
    }

    /**
     * Get ride by ID
     */
    suspend fun getRide(rideId: String): Result<Ride> {
        delay(300)
        return mutex.withLock {
            val ride = mockRides.find { it.id == rideId }
            if (ride != null) {
                Result.success(ride)
            } else {
                Result.failure(Exception("Carrera no encontrada"))
            }
        }
    }

    /**
     * Get current driver
     */
    suspend fun getCurrentDriver(): Driver = mutex.withLock { mockDriver }
}
