import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  Alert,
  TouchableOpacity,
} from 'react-native';
import { useNavigation, useRoute, RouteProp } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../navigation/AppNavigator';
import { Ride, Client, RideStatus } from '../models';
import { apiService } from '../services/apiService';
import { formatCurrency, formatDistance } from '../utils/formatters';
import Button from '../components/Button';
import Input from '../components/Input';

type CurrentRideScreenNavigationProp = StackNavigationProp<
  RootStackParamList,
  'CurrentRide'
>;

type CurrentRideScreenRouteProp = RouteProp<RootStackParamList, 'CurrentRide'>;

const CurrentRideScreen: React.FC = () => {
  const navigation = useNavigation<CurrentRideScreenNavigationProp>();
  const route = useRoute<CurrentRideScreenRouteProp>();

  const [ride, setRide] = useState<Ride | null>(null);
  const [client, setClient] = useState<Client | null>(null);
  const [otp, setOtp] = useState('');
  const [otpError, setOtpError] = useState('');
  const [otpValidated, setOtpValidated] = useState(false);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadRideData();
  }, []);

  const loadRideData = async () => {
    try {
      // In a real app, we would fetch the ride by ID
      const mockRide: Ride = {
        id: route.params.rideId,
        clientId: 'client-1',
        originAddress: 'Calle 72 #10-34, Bogotá',
        destinationAddress: 'Carrera 7 #32-16, Bogotá',
        originLocation: { latitude: 4.7130, longitude: -74.0650 },
        destinationLocation: { latitude: 4.6097, longitude: -74.0817 },
        estimatedAmount: 8500,
        distanceFromDriver: 0.5,
        tripDistance: 5.2,
        status: RideStatus.ACCEPTED,
        createdAt: new Date(),
        otp: '1234',
      };

      const clientData = await apiService.getClient(mockRide.clientId);
      setRide(mockRide);
      setClient(clientData);
    } catch (error) {
      Alert.alert('Error', 'No se pudo cargar la información de la carrera');
    }
  };

  const handleValidateOtp = async () => {
    setOtpError('');

    if (!otp) {
      setOtpError('El código OTP es requerido');
      return;
    }

    if (otp.length !== 4) {
      setOtpError('El código debe tener 4 dígitos');
      return;
    }

    setLoading(true);
    try {
      const isValid = await apiService.validateOtp(route.params.rideId, otp);
      if (isValid) {
        setOtpValidated(true);
        Alert.alert('Éxito', 'OTP validado correctamente');
      }
    } catch (error) {
      setOtpError(
        error instanceof Error ? error.message : 'Código OTP inválido'
      );
    } finally {
      setLoading(false);
    }
  };

  const handleStartRide = async () => {
    setLoading(true);
    try {
      await apiService.startRide(route.params.rideId);
      Alert.alert(
        'Carrera iniciada',
        'La carrera ha comenzado exitosamente',
        [
          {
            text: 'OK',
            onPress: () => navigation.navigate('AvailableRides'),
          },
        ]
      );
    } catch (error) {
      Alert.alert('Error', 'No se pudo iniciar la carrera');
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = () => {
    Alert.alert(
      'Cancelar carrera',
      '¿Estás seguro de que quieres cancelar esta carrera?',
      [
        { text: 'No', style: 'cancel' },
        {
          text: 'Sí, cancelar',
          style: 'destructive',
          onPress: () => navigation.navigate('AvailableRides'),
        },
      ]
    );
  };

  if (!ride || !client) {
    return (
      <View style={styles.loadingContainer}>
        <Text>Cargando...</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity
          style={styles.backButton}
          onPress={() => navigation.goBack()}
        >
          <Text style={styles.backButtonText}>← Volver</Text>
        </TouchableOpacity>
        <Text style={styles.headerTitle}>Carrera Actual</Text>
      </View>

      <ScrollView style={styles.content} contentContainerStyle={styles.scrollContent}>
        {/* Client Information */}
        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Información del Cliente</Text>
          <View style={styles.card}>
            <View style={styles.clientHeader}>
              <View style={styles.clientAvatar}>
                <Text style={styles.clientAvatarText}>
                  {client.name.charAt(0)}
                </Text>
              </View>
              <View style={styles.clientInfo}>
                <Text style={styles.clientName}>{client.name}</Text>
                <Text style={styles.clientPhone}>{client.phone}</Text>
              </View>
            </View>
          </View>
        </View>

        {/* Ride Information */}
        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Detalles de la Carrera</Text>
          <View style={styles.card}>
            <View style={styles.addressRow}>
              <View style={[styles.marker, styles.originMarker]} />
              <View style={styles.addressInfo}>
                <Text style={styles.addressLabel}>Origen</Text>
                <Text style={styles.addressText}>{ride.originAddress}</Text>
              </View>
            </View>

            <View style={styles.addressDivider} />

            <View style={styles.addressRow}>
              <View style={[styles.marker, styles.destinationMarker]} />
              <View style={styles.addressInfo}>
                <Text style={styles.addressLabel}>Destino</Text>
                <Text style={styles.addressText}>{ride.destinationAddress}</Text>
              </View>
            </View>

            <View style={styles.detailsGrid}>
              <View style={styles.detailBox}>
                <Text style={styles.detailLabel}>Distancia</Text>
                <Text style={styles.detailValue}>
                  {formatDistance(ride.tripDistance)}
                </Text>
              </View>
              <View style={styles.detailBox}>
                <Text style={styles.detailLabel}>Tarifa</Text>
                <Text style={[styles.detailValue, styles.amount]}>
                  {formatCurrency(ride.estimatedAmount)}
                </Text>
              </View>
            </View>
          </View>
        </View>

        {/* OTP Validation */}
        {!otpValidated && (
          <View style={styles.section}>
            <Text style={styles.sectionTitle}>Validación de Seguridad</Text>
            <View style={styles.card}>
              <Text style={styles.otpInstruction}>
                Solicita al cliente el código OTP de 4 dígitos
              </Text>
              <Input
                placeholder="Ingresa el código"
                value={otp}
                onChangeText={setOtp}
                error={otpError}
                keyboardType="number-pad"
                maxLength={4}
                containerStyle={styles.otpInput}
              />
              <Button
                title="Validar código"
                onPress={handleValidateOtp}
                loading={loading}
              />
            </View>
          </View>
        )}

        {/* Start Ride Button */}
        {otpValidated && (
          <View style={styles.section}>
            <View style={styles.successBanner}>
              <Text style={styles.successIcon}>✓</Text>
              <Text style={styles.successText}>Código validado correctamente</Text>
            </View>
            <Button
              title="Iniciar carrera"
              onPress={handleStartRide}
              loading={loading}
              style={styles.startButton}
            />
          </View>
        )}

        <Button
          title="Cancelar carrera"
          onPress={handleCancel}
          variant="secondary"
          style={styles.cancelButton}
        />
      </ScrollView>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F5F5F5',
  },
  loadingContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  header: {
    backgroundColor: '#fff',
    paddingTop: 48,
    paddingBottom: 16,
    paddingHorizontal: 20,
    borderBottomWidth: 1,
    borderBottomColor: '#E0E0E0',
  },
  backButton: {
    marginBottom: 8,
  },
  backButtonText: {
    fontSize: 16,
    color: '#2E7D32',
    fontWeight: '500',
  },
  headerTitle: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#212121',
  },
  content: {
    flex: 1,
  },
  scrollContent: {
    padding: 20,
  },
  section: {
    marginBottom: 24,
  },
  sectionTitle: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#212121',
    marginBottom: 12,
  },
  card: {
    backgroundColor: '#fff',
    borderRadius: 12,
    padding: 16,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  clientHeader: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  clientAvatar: {
    width: 50,
    height: 50,
    borderRadius: 25,
    backgroundColor: '#2E7D32',
    alignItems: 'center',
    justifyContent: 'center',
    marginRight: 12,
  },
  clientAvatarText: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#fff',
  },
  clientInfo: {
    flex: 1,
  },
  clientName: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#212121',
    marginBottom: 4,
  },
  clientPhone: {
    fontSize: 14,
    color: '#757575',
  },
  addressRow: {
    flexDirection: 'row',
    alignItems: 'flex-start',
    marginBottom: 12,
  },
  marker: {
    width: 12,
    height: 12,
    borderRadius: 6,
    marginTop: 4,
    marginRight: 12,
  },
  originMarker: {
    backgroundColor: '#2E7D32',
  },
  destinationMarker: {
    backgroundColor: '#D32F2F',
  },
  addressInfo: {
    flex: 1,
  },
  addressLabel: {
    fontSize: 12,
    color: '#757575',
    marginBottom: 4,
  },
  addressText: {
    fontSize: 14,
    color: '#212121',
    fontWeight: '500',
  },
  addressDivider: {
    height: 1,
    backgroundColor: '#E0E0E0',
    marginVertical: 12,
  },
  detailsGrid: {
    flexDirection: 'row',
    marginTop: 16,
    gap: 12,
  },
  detailBox: {
    flex: 1,
    backgroundColor: '#F5F5F5',
    padding: 12,
    borderRadius: 8,
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
  amount: {
    color: '#2E7D32',
  },
  otpInstruction: {
    fontSize: 14,
    color: '#757575',
    marginBottom: 16,
    textAlign: 'center',
  },
  otpInput: {
    marginBottom: 16,
  },
  successBanner: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#E8F5E9',
    padding: 16,
    borderRadius: 12,
    marginBottom: 16,
  },
  successIcon: {
    fontSize: 24,
    marginRight: 8,
    color: '#2E7D32',
  },
  successText: {
    fontSize: 16,
    fontWeight: '600',
    color: '#2E7D32',
  },
  startButton: {
    marginBottom: 12,
  },
  cancelButton: {
    marginTop: 12,
  },
});

export default CurrentRideScreen;
