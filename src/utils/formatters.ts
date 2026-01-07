/**
 * Format currency in Colombian Pesos
 */
export const formatCurrency = (amount: number): string => {
  return `$${amount.toLocaleString('es-CO')}`;
};

/**
 * Format distance
 */
export const formatDistance = (distance: number): string => {
  if (distance < 1) {
    return `${Math.round(distance * 1000)} m`;
  }
  return `${distance.toFixed(1)} km`;
};
