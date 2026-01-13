import React from 'react';
import { StatusBar } from 'expo-status-bar';
import { GestureHandlerRootView } from 'react-native-gesture-handler';
import { SafeAreaProvider } from 'react-native-safe-area-context';
import { DriverProvider } from './src/contexts/DriverContext';
import AppNavigator from './src/navigation/AppNavigator';

export default function App() {
  return (
    <GestureHandlerRootView style={{ flex: 1 }}>
      <SafeAreaProvider>
        <DriverProvider>
          <AppNavigator />
          <StatusBar style="auto" />
        </DriverProvider>
      </SafeAreaProvider>
    </GestureHandlerRootView>
  );
}
