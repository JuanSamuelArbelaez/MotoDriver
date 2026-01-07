import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import { Driver, DriverStatus } from '../models';

interface DriverHeaderProps {
  driver: Driver;
  onStatusChange: (status: DriverStatus) => void;
}

const DriverHeader: React.FC<DriverHeaderProps> = ({ driver, onStatusChange }) => {
  const getStatusColor = (status: DriverStatus): string => {
    switch (status) {
      case DriverStatus.ACTIVE:
        return '#4CAF50';
      case DriverStatus.INACTIVE:
        return '#9E9E9E';
      case DriverStatus.EN_ROUTE:
        return '#FF9800';
      case DriverStatus.IN_RIDE:
        return '#2196F3';
      default:
        return '#9E9E9E';
    }
  };

  const statusOptions = [
    DriverStatus.ACTIVE,
    DriverStatus.INACTIVE,
    DriverStatus.EN_ROUTE,
    DriverStatus.IN_RIDE,
  ];

  return (
    <View style={styles.container}>
      <View style={styles.driverInfo}>
        <View style={styles.avatar}>
          <Text style={styles.avatarText}>
            {driver.name.charAt(0).toUpperCase()}
          </Text>
        </View>
        <View style={styles.infoText}>
          <Text style={styles.name}>{driver.name}</Text>
          <Text style={styles.plate}>{driver.vehiclePlate}</Text>
        </View>
      </View>

      <View style={styles.statusContainer}>
        <Text style={styles.statusLabel}>Estado:</Text>
        <View style={styles.statusButtons}>
          {statusOptions.map((status) => (
            <TouchableOpacity
              key={status}
              style={[
                styles.statusButton,
                driver.status === status && {
                  backgroundColor: getStatusColor(status),
                },
              ]}
              onPress={() => onStatusChange(status)}
              activeOpacity={0.7}
            >
              <Text
                style={[
                  styles.statusButtonText,
                  driver.status === status && styles.statusButtonTextActive,
                ]}
              >
                {status}
              </Text>
            </TouchableOpacity>
          ))}
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: '#fff',
    paddingVertical: 16,
    paddingHorizontal: 20,
    borderBottomWidth: 1,
    borderBottomColor: '#E0E0E0',
  },
  driverInfo: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 16,
  },
  avatar: {
    width: 50,
    height: 50,
    borderRadius: 25,
    backgroundColor: '#2E7D32',
    alignItems: 'center',
    justifyContent: 'center',
    marginRight: 12,
  },
  avatarText: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#fff',
  },
  infoText: {
    flex: 1,
  },
  name: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#212121',
    marginBottom: 2,
  },
  plate: {
    fontSize: 14,
    color: '#757575',
  },
  statusContainer: {
    marginTop: 8,
  },
  statusLabel: {
    fontSize: 14,
    fontWeight: '500',
    color: '#757575',
    marginBottom: 8,
  },
  statusButtons: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: 8,
  },
  statusButton: {
    paddingVertical: 6,
    paddingHorizontal: 12,
    borderRadius: 16,
    backgroundColor: '#F5F5F5',
    borderWidth: 1,
    borderColor: '#E0E0E0',
  },
  statusButtonText: {
    fontSize: 12,
    fontWeight: '500',
    color: '#757575',
  },
  statusButtonTextActive: {
    color: '#fff',
  },
});

export default DriverHeader;
