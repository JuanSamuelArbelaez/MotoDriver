import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  RefreshControl,
  Alert,
} from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../navigation/AppNavigator';
import { useDriver } from '../contexts/DriverContext';
import { Ride, DriverStatus } from '../models';
import { apiService } from '../services/apiService';
import DriverHeader from '../components/DriverHeader';
import RideItem from '../components/RideItem';
import RideOverlay from '../components/RideOverlay';
import NotificationPopup from '../components/NotificationPopup';

type AvailableRidesScreenNavigationProp = StackNavigationProp<
  RootStackParamList,
  'AvailableRides'
>;

const AvailableRidesScreen: React.FC = () => {
  const navigation = useNavigation<AvailableRidesScreenNavigationProp>();
  const { driver, updateDriverStatus } = useDriver();

  const [rides, setRides] = useState<Ride[]>([]);
  const [selectedRide, setSelectedRide] = useState<Ride | null>(null);
  const [refreshing, setRefreshing] = useState(false);
  const [loading, setLoading] = useState(false);
  const [showNotification, setShowNotification] = useState(false);
  const [notificationRide, setNotificationRide] = useState<Ride | null>(null);

  useEffect(() => {
    loadRides();
    // Simulate real-time notification for active drivers
    if (driver?.status === DriverStatus.ACTIVE) {
      const timer = setTimeout(() => {
        simulateNotification();
      }, 5000);
      return () => clearTimeout(timer);
    }
  }, [driver?.status]);

  useEffect(() => {
    // Auto-select the closest ride
    if (rides.length > 0 && !selectedRide) {
      setSelectedRide(rides[0]);
    }
  }, [rides]);

  const loadRides = async () => {
    try {
      const availableRides = await apiService.getAvailableRides();
      setRides(availableRides);
    } catch (error) {
      Alert.alert('Error', 'No se pudieron cargar las carreras');
    }
  };

  const handleRefresh = async () => {
    setRefreshing(true);
    await loadRides();
    setRefreshing(false);
  };

  const simulateNotification = () => {
    // Simulate a new ride within 1km for active drivers
    if (rides.length > 0 && driver?.status === DriverStatus.ACTIVE) {
      const nearbyRide = rides.find(r => r.distanceFromDriver <= 1);
      if (nearbyRide) {
        setNotificationRide(nearbyRide);
        setShowNotification(true);
      }
    }
  };

  const handleAcceptRide = async (ride: Ride) => {
    setLoading(true);
    try {
      await apiService.acceptRide(ride.id);
      Alert.alert(
        'Carrera aceptada',
        'Has aceptado la carrera exitosamente',
        [
          {
            text: 'Continuar',
            onPress: () => navigation.navigate('CurrentRide', { rideId: ride.id }),
          },
        ]
      );
    } catch (error) {
      Alert.alert(
        'Error',
        error instanceof Error ? error.message : 'No se pudo aceptar la carrera'
      );
    } finally {
      setLoading(false);
    }
  };

  const handleNotificationAccept = () => {
    setShowNotification(false);
    if (notificationRide) {
      handleAcceptRide(notificationRide);
    }
  };

  const handleNotificationReject = () => {
    setShowNotification(false);
    setNotificationRide(null);
  };

  const handleStatusChange = async (status: DriverStatus) => {
    try {
      await updateDriverStatus(status);
    } catch (error) {
      Alert.alert('Error', 'No se pudo actualizar el estado');
    }
  };

  const renderRideItem = ({ item }: { item: Ride }) => (
    <RideItem
      ride={item}
      isSelected={selectedRide?.id === item.id}
      onPress={() => setSelectedRide(item)}
    />
  );

  const renderEmptyList = () => (
    <View style={styles.emptyContainer}>
      <Text style={styles.emptyIcon}>🔍</Text>
      <Text style={styles.emptyTitle}>No hay carreras disponibles</Text>
      <Text style={styles.emptySubtitle}>
        Las nuevas carreras aparecerán aquí automáticamente
      </Text>
    </View>
  );

  if (!driver) {
    return null;
  }

  return (
    <View style={styles.container}>
      <DriverHeader driver={driver} onStatusChange={handleStatusChange} />

      <FlatList
        data={rides}
        renderItem={renderRideItem}
        keyExtractor={(item) => item.id}
        contentContainerStyle={[
          styles.listContent,
          rides.length === 0 && styles.listContentEmpty,
        ]}
        refreshControl={
          <RefreshControl refreshing={refreshing} onRefresh={handleRefresh} />
        }
        ListEmptyComponent={renderEmptyList}
      />

      <RideOverlay
        ride={selectedRide}
        onAccept={() => selectedRide && handleAcceptRide(selectedRide)}
        loading={loading}
      />

      <NotificationPopup
        visible={showNotification}
        ride={notificationRide}
        onAccept={handleNotificationAccept}
        onReject={handleNotificationReject}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F5F5F5',
  },
  listContent: {
    padding: 16,
    paddingBottom: 320, // Space for overlay
  },
  listContentEmpty: {
    flexGrow: 1,
  },
  emptyContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    paddingHorizontal: 40,
  },
  emptyIcon: {
    fontSize: 64,
    marginBottom: 16,
  },
  emptyTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#212121',
    marginBottom: 8,
    textAlign: 'center',
  },
  emptySubtitle: {
    fontSize: 14,
    color: '#757575',
    textAlign: 'center',
  },
});

export default AvailableRidesScreen;
