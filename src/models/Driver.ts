/**
 * Driver model representing a moto-taxista
 */
export interface Driver {
  id: string;
  name: string;
  phone: string;
  vehiclePlate: string;
  status: DriverStatus;
  currentLocation?: Location;
  rating?: number;
}

export enum DriverStatus {
  ACTIVE = 'Activo',
  INACTIVE = 'Inactivo',
  EN_ROUTE = 'En ruta',
  IN_RIDE = 'En carrera',
}

export interface Location {
  latitude: number;
  longitude: number;
}
