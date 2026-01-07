import React from 'react';
import { StatusBar } from 'expo-status-bar';
import { SafeAreaProvider } from 'react-native-safe-area-context';
import { DriverProvider } from './src/contexts/DriverContext';
import AppNavigator from './src/navigation/AppNavigator';

export default function App() {
  return (
    <SafeAreaProvider>
      <DriverProvider>
        <AppNavigator />
        <StatusBar style="auto" />
      </DriverProvider>
    </SafeAreaProvider>
  );
}
