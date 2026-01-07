import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  Modal,
  TouchableOpacity,
  Animated,
} from 'react-native';
import { Ride } from '../models';
import { formatCurrency, formatDistance } from '../utils/formatters';
import Button from './Button';

interface NotificationPopupProps {
  visible: boolean;
  ride: Ride | null;
  onAccept: () => void;
  onReject: () => void;
}

const NotificationPopup: React.FC<NotificationPopupProps> = ({
  visible,
  ride,
  onAccept,
  onReject,
}) => {
  if (!ride) {
    return null;
  }

  return (
    <Modal
      visible={visible}
      transparent
      animationType="fade"
      onRequestClose={onReject}
    >
      <View style={styles.overlay}>
        <View style={styles.container}>
          <View style={styles.iconContainer}>
            <Text style={styles.icon}>🚨</Text>
          </View>

          <Text style={styles.title}>¡Nueva carrera cercana!</Text>
          <Text style={styles.subtitle}>
            A solo {formatDistance(ride.distanceFromDriver)} de tu ubicación
          </Text>

          <View style={styles.rideInfo}>
            <View style={styles.infoRow}>
              <Text style={styles.label}>Origen:</Text>
              <Text style={styles.value} numberOfLines={2}>
                {ride.originAddress}
              </Text>
            </View>

            <View style={styles.infoRow}>
              <Text style={styles.label}>Tarifa:</Text>
              <Text style={[styles.value, styles.amount]}>
                {formatCurrency(ride.estimatedAmount)}
              </Text>
            </View>
          </View>

          <View style={styles.buttonContainer}>
            <TouchableOpacity
              style={[styles.button, styles.rejectButton]}
              onPress={onReject}
              activeOpacity={0.7}
            >
              <Text style={styles.rejectButtonText}>Rechazar</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={[styles.button, styles.acceptButton]}
              onPress={onAccept}
              activeOpacity={0.7}
            >
              <Text style={styles.acceptButtonText}>Aceptar</Text>
            </TouchableOpacity>
          </View>
        </View>
      </View>
    </Modal>
  );
};

const styles = StyleSheet.create({
  overlay: {
    flex: 1,
    backgroundColor: 'rgba(0, 0, 0, 0.6)',
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
  },
  container: {
    backgroundColor: '#fff',
    borderRadius: 20,
    padding: 24,
    width: '100%',
    maxWidth: 400,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.3,
    shadowRadius: 8,
    elevation: 10,
  },
  iconContainer: {
    alignItems: 'center',
    marginBottom: 16,
  },
  icon: {
    fontSize: 48,
  },
  title: {
    fontSize: 22,
    fontWeight: 'bold',
    color: '#212121',
    textAlign: 'center',
    marginBottom: 8,
  },
  subtitle: {
    fontSize: 14,
    color: '#757575',
    textAlign: 'center',
    marginBottom: 20,
  },
  rideInfo: {
    backgroundColor: '#F5F5F5',
    borderRadius: 12,
    padding: 16,
    marginBottom: 20,
  },
  infoRow: {
    marginBottom: 12,
  },
  label: {
    fontSize: 12,
    color: '#757575',
    marginBottom: 4,
  },
  value: {
    fontSize: 14,
    color: '#212121',
    fontWeight: '500',
  },
  amount: {
    fontSize: 18,
    color: '#2E7D32',
    fontWeight: 'bold',
  },
  buttonContainer: {
    flexDirection: 'row',
    gap: 12,
  },
  button: {
    flex: 1,
    paddingVertical: 14,
    borderRadius: 8,
    alignItems: 'center',
    justifyContent: 'center',
  },
  rejectButton: {
    backgroundColor: '#fff',
    borderWidth: 1,
    borderColor: '#D32F2F',
  },
  rejectButtonText: {
    fontSize: 16,
    fontWeight: '600',
    color: '#D32F2F',
  },
  acceptButton: {
    backgroundColor: '#2E7D32',
  },
  acceptButtonText: {
    fontSize: 16,
    fontWeight: '600',
    color: '#fff',
  },
});

export default NotificationPopup;
