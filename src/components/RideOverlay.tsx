import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { Ride } from '../models';
import { formatCurrency, formatDistance } from '../utils/formatters';
import Button from './Button';

interface RideOverlayProps {
  ride: Ride | null;
  onAccept: () => void;
  loading?: boolean;
}

const RideOverlay: React.FC<RideOverlayProps> = ({ ride, onAccept, loading }) => {
  if (!ride) {
    return null;
  }

  return (
    <View style={styles.container}>
      <View style={styles.handle} />
      
      <Text style={styles.title}>Carrera seleccionada</Text>

      <View style={styles.infoSection}>
        <View style={styles.infoRow}>
          <View style={styles.iconContainer}>
            <View style={[styles.icon, styles.originIcon]} />
          </View>
          <View style={styles.addressInfo}>
            <Text style={styles.label}>Origen</Text>
            <Text style={styles.address}>{ride.originAddress}</Text>
          </View>
        </View>

        <View style={styles.divider} />

        <View style={styles.infoRow}>
          <View style={styles.iconContainer}>
            <View style={[styles.icon, styles.destinationIcon]} />
          </View>
          <View style={styles.addressInfo}>
            <Text style={styles.label}>Destino</Text>
            <Text style={styles.address}>{ride.destinationAddress}</Text>
          </View>
        </View>
      </View>

      <View style={styles.detailsRow}>
        <View style={styles.detailItem}>
          <Text style={styles.detailLabel}>Distancia</Text>
          <Text style={styles.detailValue}>
            {formatDistance(ride.tripDistance)}
          </Text>
        </View>
        <View style={styles.detailItem}>
          <Text style={styles.detailLabel}>Tarifa estimada</Text>
          <Text style={styles.detailValue}>
            {formatCurrency(ride.estimatedAmount)}
          </Text>
        </View>
      </View>

      <Button
        title="Aceptar carrera"
        onPress={onAccept}
        loading={loading}
        style={styles.acceptButton}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    position: 'absolute',
    bottom: 0,
    left: 0,
    right: 0,
    backgroundColor: '#fff',
    borderTopLeftRadius: 24,
    borderTopRightRadius: 24,
    paddingHorizontal: 20,
    paddingTop: 12,
    paddingBottom: 32,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: -4 },
    shadowOpacity: 0.1,
    shadowRadius: 8,
    elevation: 10,
  },
  handle: {
    width: 40,
    height: 4,
    backgroundColor: '#E0E0E0',
    borderRadius: 2,
    alignSelf: 'center',
    marginBottom: 16,
  },
  title: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#212121',
    marginBottom: 16,
  },
  infoSection: {
    marginBottom: 16,
  },
  infoRow: {
    flexDirection: 'row',
    alignItems: 'flex-start',
    paddingVertical: 8,
  },
  iconContainer: {
    width: 32,
    alignItems: 'center',
    paddingTop: 4,
  },
  icon: {
    width: 12,
    height: 12,
    borderRadius: 6,
  },
  originIcon: {
    backgroundColor: '#2E7D32',
  },
  destinationIcon: {
    backgroundColor: '#D32F2F',
  },
  addressInfo: {
    flex: 1,
    marginLeft: 8,
  },
  label: {
    fontSize: 12,
    color: '#757575',
    marginBottom: 2,
  },
  address: {
    fontSize: 14,
    color: '#212121',
    fontWeight: '500',
  },
  divider: {
    height: 1,
    backgroundColor: '#E0E0E0',
    marginVertical: 8,
    marginLeft: 32,
  },
  detailsRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 20,
    backgroundColor: '#F5F5F5',
    padding: 16,
    borderRadius: 12,
  },
  detailItem: {
    flex: 1,
  },
  detailLabel: {
    fontSize: 12,
    color: '#757575',
    marginBottom: 4,
  },
  detailValue: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#212121',
  },
  acceptButton: {
    marginTop: 8,
  },
});

export default RideOverlay;
