import { Location } from './Driver';

/**
 * Ride model representing a carrera
 */
export interface Ride {
  id: string;
  clientId: string;
  originAddress: string;
  destinationAddress: string;
  originLocation: Location;
  destinationLocation: Location;
  estimatedAmount: number;
  distanceFromDriver: number; // in kilometers
  tripDistance: number; // in kilometers
  status: RideStatus;
  createdAt: Date;
  otp?: string;
}

export enum RideStatus {
  AVAILABLE = 'available',
  ACCEPTED = 'accepted',
  IN_PROGRESS = 'in_progress',
  COMPLETED = 'completed',
  CANCELLED = 'cancelled',
}
