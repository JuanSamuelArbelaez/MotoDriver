import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import { Ride } from '../models';
import { formatCurrency, formatDistance } from '../utils/formatters';

interface RideItemProps {
  ride: Ride;
  isSelected: boolean;
  onPress: () => void;
}

const RideItem: React.FC<RideItemProps> = ({ ride, isSelected, onPress }) => {
  return (
    <TouchableOpacity
      style={[styles.container, isSelected && styles.containerSelected]}
      onPress={onPress}
      activeOpacity={0.7}
    >
      <View style={styles.header}>
        <View style={styles.distanceBadge}>
          <Text style={styles.distanceText}>
            📍 {formatDistance(ride.distanceFromDriver)}
          </Text>
        </View>
        <Text style={styles.amount}>{formatCurrency(ride.estimatedAmount)}</Text>
      </View>

      <View style={styles.addressContainer}>
        <View style={styles.addressRow}>
          <View style={styles.dot} />
          <Text style={styles.addressText} numberOfLines={1}>
            {ride.originAddress}
          </Text>
        </View>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: '#fff',
    borderRadius: 12,
    padding: 16,
    marginBottom: 12,
    borderWidth: 2,
    borderColor: 'transparent',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  containerSelected: {
    borderColor: '#2E7D32',
    backgroundColor: '#F1F8E9',
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 12,
  },
  distanceBadge: {
    backgroundColor: '#E3F2FD',
    paddingHorizontal: 12,
    paddingVertical: 6,
    borderRadius: 16,
  },
  distanceText: {
    fontSize: 12,
    fontWeight: '600',
    color: '#1976D2',
  },
  amount: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#2E7D32',
  },
  addressContainer: {
    marginTop: 4,
  },
  addressRow: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  dot: {
    width: 8,
    height: 8,
    borderRadius: 4,
    backgroundColor: '#2E7D32',
    marginRight: 8,
  },
  addressText: {
    flex: 1,
    fontSize: 14,
    color: '#424242',
  },
});

export default RideItem;
