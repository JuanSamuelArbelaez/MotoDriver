# MotoDriver - Arquitectura y Documentación Técnica

## 📋 Stack Tecnológico

### Framework Principal
- **Expo + React Native + TypeScript**
  - Desarrollo cross-platform (iOS y Android)
  - TypeScript para tipado fuerte y mejor mantenibilidad
  - Expo facilita el desarrollo y despliegue

### Navegación
- **React Navigation v6**
  - Stack Navigator para flujo de pantallas
  - Gestión de estado de navegación
  - Deep linking preparado

### Gestión de Estado
- **React Context API**
  - Context para estado global del conductor
  - Hooks personalizados (`useDriver`)
  - Simple y efectivo para el alcance actual

### Almacenamiento
- **AsyncStorage**
  - Persistencia local de datos
  - Tokens de autenticación
  - Preferencias del usuario

### Ubicación
- **expo-location**
  - Acceso a GPS del dispositivo
  - Cálculo de distancias
  - Tracking en tiempo real

## 🏗️ Arquitectura del Proyecto

### Estructura de Carpetas

```
src/
├── components/          # Componentes reutilizables
│   ├── Button.tsx
│   ├── Input.tsx
│   ├── DriverHeader.tsx
│   ├── RideItem.tsx
│   ├── RideOverlay.tsx
│   └── NotificationPopup.tsx
├── contexts/           # React Contexts
│   └── DriverContext.tsx
├── models/             # Modelos de datos y tipos
│   ├── Driver.ts
│   ├── Ride.ts
│   ├── Client.ts
│   └── index.ts
├── navigation/         # Configuración de navegación
│   └── AppNavigator.tsx
├── screens/           # Pantallas principales
│   ├── LoginScreen.tsx
│   ├── AvailableRidesScreen.tsx
│   └── CurrentRideScreen.tsx
├── services/          # Servicios de API
│   └── apiService.ts
└── utils/             # Utilidades y helpers
    └── formatters.ts
```

### Patrones de Diseño

#### Clean Architecture
- **Separación de capas**: UI → Lógica → Servicios
- **Inyección de dependencias**: Contexts y Services
- **Componentes reutilizables**: Máxima modularidad

#### Component-Based
- Componentes funcionales con Hooks
- Props tipadas con TypeScript
- Composición sobre herencia

## 🎨 Pantallas Implementadas

### 1. LoginScreen
**Ruta**: `/Login`

**Características**:
- Formulario con validación
- Estados de carga y error
- Autenticación vía mock API
- Diseño responsivo

**Estados**:
- Loading (durante autenticación)
- Error (credenciales inválidas)
- Success (redirige a AvailableRides)

### 2. AvailableRidesScreen
**Ruta**: `/AvailableRides`

**Características**:
- Header con información del conductor
- Selector de estado (Activo/Inactivo/En ruta/En carrera)
- Lista de carreras ordenadas por proximidad
- Pull-to-refresh
- Overlay footer con carrera seleccionada
- Popup de notificación para carreras cercanas (≤1km)

**Componentes**:
- `DriverHeader`: Info del conductor y selector de estado
- `RideItem`: Item de carrera en la lista
- `RideOverlay`: Footer con detalles de carrera seleccionada
- `NotificationPopup`: Modal para notificaciones

**Funcionalidades**:
- Auto-selección de la carrera más cercana
- Actualización en tiempo real (simulada)
- Notificaciones solo para conductores activos
- Aceptar/Rechazar carreras

### 3. CurrentRideScreen
**Ruta**: `/CurrentRide/:rideId`

**Características**:
- Información completa del cliente
- Detalles de origen y destino
- Distancia y tarifa
- Validación de OTP (4 dígitos)
- Inicio de carrera tras validación
- Botón de cancelación

**Flujo**:
1. Mostrar info de cliente y carrera
2. Solicitar y validar OTP
3. Habilitar inicio de carrera
4. Navegar de vuelta tras completar

## 🔄 Flujo de Navegación

```
Login
  ↓
  ↓ (autenticación exitosa)
  ↓
AvailableRides ←→ CurrentRide
  ↑                    ↓
  └────────────────────┘
   (completar/cancelar)
```

## 📡 Integración con Backend (Mock)

### Servicios Implementados

#### `apiService.ts`
Mock service que simula llamadas al backend:

- `login(email, password)`: Autenticación
- `getAvailableRides()`: Lista de carreras disponibles
- `acceptRide(rideId)`: Aceptar una carrera
- `validateOtp(rideId, otp)`: Validar código OTP
- `startRide(rideId)`: Iniciar carrera
- `getClient(clientId)`: Obtener datos del cliente
- `updateDriverStatus(status)`: Actualizar estado del conductor

### Preparación para Backend Real

Para conectar con un backend real:

1. Reemplazar `apiService.ts` con llamadas HTTP reales
2. Usar `fetch` o `axios` para requests
3. Implementar WebSockets para actualizaciones en tiempo real
4. Agregar manejo de tokens JWT
5. Implementar refresh de tokens

Ejemplo:
```typescript
async login(email: string, password: string): Promise<Driver> {
  const response = await fetch('https://api.motodriver.com/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password }),
  });
  
  if (!response.ok) throw new Error('Auth failed');
  return await response.json();
}
```

## 🎯 Modelos de Datos

### Driver
```typescript
interface Driver {
  id: string;
  name: string;
  phone: string;
  vehiclePlate: string;
  status: DriverStatus;
  currentLocation?: Location;
  rating?: number;
}

enum DriverStatus {
  ACTIVE = 'Activo',
  INACTIVE = 'Inactivo',
  EN_ROUTE = 'En ruta',
  IN_RIDE = 'En carrera',
}
```

### Ride
```typescript
interface Ride {
  id: string;
  clientId: string;
  originAddress: string;
  destinationAddress: string;
  originLocation: Location;
  destinationLocation: Location;
  estimatedAmount: number;
  distanceFromDriver: number;
  tripDistance: number;
  status: RideStatus;
  createdAt: Date;
  otp?: string;
}
```

### Client
```typescript
interface Client {
  id: string;
  name: string;
  phone: string;
}
```

## 🔔 Notificaciones

### Implementación Actual
- Simulación de notificaciones in-app
- Modal popup para carreras cercanas
- Solo para conductores con estado "Activo"
- Distancia máxima: 1km

### Para Producción
Implementar notificaciones push reales con:
- **Expo Notifications**
- **Firebase Cloud Messaging (FCM)**

Configuración necesaria:
1. Instalar `expo-notifications`
2. Configurar FCM en Firebase Console
3. Implementar token registration
4. Backend envía notificaciones push

## 📱 Características de UX

### Diseño
- **Paleta de colores**:
  - Primario: Verde `#2E7D32` (moto-taxi tradicional)
  - Secundario: Gris `#757575`
  - Error: Rojo `#D32F2F`
  - Fondo: `#F5F5F5`

- **Tipografía**: System fonts nativas
- **Iconografía**: Emojis para prototipo (reemplazar con iconos reales)

### Interacciones
- Feedback visual en todos los botones
- Loading states en operaciones asíncronas
- Mensajes de error claros
- Confirmaciones para acciones críticas

## 🚀 Próximos Pasos

### Funcionalidades Pendientes

1. **Mapas**
   - Integrar Google Maps / Mapbox
   - Mostrar ruta origen-destino
   - Tracking en tiempo real

2. **Notificaciones Push Reales**
   - Configurar FCM
   - Permisos de notificaciones
   - Sonido y vibración

3. **Chat/Llamadas**
   - Comunicación con cliente
   - Botón de llamada directa

4. **Historial**
   - Ver carreras completadas
   - Estadísticas del conductor

5. **Perfil**
   - Editar datos personales
   - Cambiar contraseña
   - Documentos del vehículo

6. **Optimizaciones**
   - Caché de datos
   - Offline mode
   - Optimización de batería

## 🧪 Testing

### Para Desarrollo
```bash
# Iniciar en modo desarrollo
npm start

# Probar en Android
npm run android

# Probar en iOS (requiere Mac)
npm run ios

# Probar en web
npm run web
```

### Credenciales de Prueba
- **Email**: cualquier email válido
- **Password**: cualquier texto (mínimo 6 caracteres)

### Datos Mock
El servicio incluye 3 carreras de ejemplo con diferentes distancias y montos.

## 📦 Dependencias Principales

```json
{
  "expo": "~54.0.31",
  "react": "19.1.0",
  "react-native": "0.81.5",
  "@react-navigation/native": "^7.0.17",
  "@react-navigation/stack": "^7.2.0",
  "@react-native-async-storage/async-storage": "^2.1.0",
  "expo-location": "^18.0.6",
  "typescript": "~5.9.2"
}
```

## 🔒 Seguridad

### Implementado
- Validación de formularios
- OTP de 4 dígitos para iniciar carreras
- TypeScript para prevención de errores

### Por Implementar
- Tokens JWT con refresh
- Encriptación de datos sensibles
- Biometría (huella/Face ID)
- Rate limiting en requests
- Sanitización de inputs

## 📄 Licencia

Este proyecto es parte del sistema MotoDriver para gestión de moto-taxis.

---

**Desarrollado con ❤️ para facilitar el trabajo de los moto-taxistas**
