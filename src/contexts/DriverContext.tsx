import React, { createContext, useState, useContext, ReactNode } from 'react';
import { Driver, DriverStatus } from '../models';
import { apiService } from '../services/apiService';

interface DriverContextType {
  driver: Driver | null;
  isAuthenticated: boolean;
  login: (email: string, password: string) => Promise<void>;
  logout: () => void;
  updateDriverStatus: (status: DriverStatus) => Promise<void>;
}

const DriverContext = createContext<DriverContextType | undefined>(undefined);

export const DriverProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [driver, setDriver] = useState<Driver | null>(null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const login = async (email: string, password: string) => {
    try {
      const loggedInDriver = await apiService.login(email, password);
      setDriver(loggedInDriver);
      setIsAuthenticated(true);
    } catch (error) {
      throw error;
    }
  };

  const logout = () => {
    setDriver(null);
    setIsAuthenticated(false);
  };

  const updateDriverStatus = async (status: DriverStatus) => {
    try {
      const updatedDriver = await apiService.updateDriverStatus(status);
      setDriver(updatedDriver);
    } catch (error) {
      throw error;
    }
  };

  return (
    <DriverContext.Provider
      value={{
        driver,
        isAuthenticated,
        login,
        logout,
        updateDriverStatus,
      }}
    >
      {children}
    </DriverContext.Provider>
  );
};

export const useDriver = () => {
  const context = useContext(DriverContext);
  if (context === undefined) {
    throw new Error('useDriver must be used within a DriverProvider');
  }
  return context;
};
