import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { useDriver } from '../contexts/DriverContext';

// Import screens (to be created)
import LoginScreen from '../screens/LoginScreen';
import AvailableRidesScreen from '../screens/AvailableRidesScreen';
import CurrentRideScreen from '../screens/CurrentRideScreen';

export type RootStackParamList = {
  Login: undefined;
  AvailableRides: undefined;
  CurrentRide: { rideId: string };
};

const Stack = createStackNavigator<RootStackParamList>();

const AppNavigator: React.FC = () => {
  const { isAuthenticated } = useDriver();

  return (
    <NavigationContainer>
      <Stack.Navigator
        screenOptions={{
          headerShown: false,
        }}
      >
        {!isAuthenticated ? (
          <Stack.Screen name="Login" component={LoginScreen} />
        ) : (
          <>
            <Stack.Screen name="AvailableRides" component={AvailableRidesScreen} />
            <Stack.Screen name="CurrentRide" component={CurrentRideScreen} />
          </>
        )}
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default AppNavigator;
