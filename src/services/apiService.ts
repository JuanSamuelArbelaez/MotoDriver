import { Driver, DriverStatus, Location, Ride, RideStatus, Client } from '../models';

/**
 * Mock API service for development
 * This simulates backend API calls
 */
class ApiService {
  private mockDriver: Driver = {
    id: '1',
    name: 'Juan Pérez',
    phone: '+57 300 123 4567',
    vehiclePlate: 'ABC-123',
    status: DriverStatus.ACTIVE,
    currentLocation: {
      latitude: 4.7110,
      longitude: -74.0721,
    },
    rating: 4.8,
  };

  private mockRides: Ride[] = [
    {
      id: 'ride-1',
      clientId: 'client-1',
      originAddress: 'Calle 72 #10-34, Bogotá',
      destinationAddress: 'Carrera 7 #32-16, Bogotá',
      originLocation: { latitude: 4.7130, longitude: -74.0650 },
      destinationLocation: { latitude: 4.6097, longitude: -74.0817 },
      estimatedAmount: 8500,
      distanceFromDriver: 0.5,
      tripDistance: 5.2,
      status: RideStatus.AVAILABLE,
      createdAt: new Date(),
      otp: '1234',
    },
    {
      id: 'ride-2',
      clientId: 'client-2',
      originAddress: 'Carrera 15 #93-40, Bogotá',
      destinationAddress: 'Avenida 68 #75-00, Bogotá',
      originLocation: { latitude: 4.6800, longitude: -74.0500 },
      destinationLocation: { latitude: 4.7000, longitude: -74.1100 },
      estimatedAmount: 12000,
      distanceFromDriver: 1.2,
      tripDistance: 8.5,
      status: RideStatus.AVAILABLE,
      createdAt: new Date(),
      otp: '5678',
    },
    {
      id: 'ride-3',
      clientId: 'client-3',
      originAddress: 'Calle 100 #19-61, Bogotá',
      destinationAddress: 'Calle 26 #92-32, Bogotá',
      originLocation: { latitude: 4.6900, longitude: -74.0400 },
      destinationLocation: { latitude: 4.6500, longitude: -74.1000 },
      estimatedAmount: 15000,
      distanceFromDriver: 2.5,
      tripDistance: 12.0,
      status: RideStatus.AVAILABLE,
      createdAt: new Date(),
      otp: '9012',
    },
  ];

  private mockClients: Client[] = [
    { id: 'client-1', name: 'María García', phone: '+57 301 234 5678' },
    { id: 'client-2', name: 'Carlos Rodríguez', phone: '+57 302 345 6789' },
    { id: 'client-3', name: 'Ana López', phone: '+57 303 456 7890' },
  ];

  /**
   * Simulate login
   */
  async login(email: string, password: string): Promise<Driver> {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        if (email && password) {
          resolve(this.mockDriver);
        } else {
          reject(new Error('Credenciales inválidas'));
        }
      }, 1000);
    });
  }

  /**
   * Get available rides
   */
  async getAvailableRides(): Promise<Ride[]> {
    return new Promise((resolve) => {
      setTimeout(() => {
        // Sort by distance from driver
        const sortedRides = [...this.mockRides].sort(
          (a, b) => a.distanceFromDriver - b.distanceFromDriver
        );
        resolve(sortedRides.filter(r => r.status === RideStatus.AVAILABLE));
      }, 500);
    });
  }

  /**
   * Accept a ride
   */
  async acceptRide(rideId: string): Promise<Ride> {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        const ride = this.mockRides.find(r => r.id === rideId);
        if (ride) {
          ride.status = RideStatus.ACCEPTED;
          resolve(ride);
        } else {
          reject(new Error('Carrera no encontrada'));
        }
      }, 800);
    });
  }

  /**
   * Validate OTP
   */
  async validateOtp(rideId: string, otp: string): Promise<boolean> {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        const ride = this.mockRides.find(r => r.id === rideId);
        if (ride && ride.otp === otp) {
          resolve(true);
        } else {
          reject(new Error('OTP inválido'));
        }
      }, 500);
    });
  }

  /**
   * Start ride
   */
  async startRide(rideId: string): Promise<Ride> {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        const ride = this.mockRides.find(r => r.id === rideId);
        if (ride) {
          ride.status = RideStatus.IN_PROGRESS;
          resolve(ride);
        } else {
          reject(new Error('Carrera no encontrada'));
        }
      }, 500);
    });
  }

  /**
   * Get client by ID
   */
  async getClient(clientId: string): Promise<Client> {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        const client = this.mockClients.find(c => c.id === clientId);
        if (client) {
          resolve(client);
        } else {
          reject(new Error('Cliente no encontrado'));
        }
      }, 300);
    });
  }

  /**
   * Update driver status
   */
  async updateDriverStatus(status: DriverStatus): Promise<Driver> {
    return new Promise((resolve) => {
      setTimeout(() => {
        this.mockDriver.status = status;
        resolve(this.mockDriver);
      }, 500);
    });
  }

  /**
   * Get current driver
   */
  getCurrentDriver(): Driver {
    return this.mockDriver;
  }
}

export const apiService = new ApiService();
