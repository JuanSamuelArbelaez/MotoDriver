# MotoDriver - Arquitectura y Documentación Técnica

## 📋 Stack Tecnológico

### Plataforma
- **Android Nativo con Kotlin**
  - Desarrollo específico para Android
  - Rendimiento óptimo y acceso completo a APIs del sistema
  - Kotlin para código conciso y seguro

### UI Framework
- **Jetpack Compose**
  - UI declarativa moderna
  - Composición de componentes reutilizables
  - Material Design 3 integrado
  - Previews en tiempo real

### Arquitectura
- **MVVM (Model-View-ViewModel)**
  - Separación clara de responsabilidades
  - Testabilidad mejorada
  - Soporte nativo con ViewModel de Jetpack

### Navegación
- **Navigation Compose**
  - Navegación declarativa
  - Type-safe arguments
  - Deep linking preparado

### Gestión de Estado
- **StateFlow + Compose State**
  - Flujos reactivos con Kotlin Coroutines
  - Integración nativa con Compose
  - Lifecycle-aware

## 🏗️ Arquitectura del Proyecto

### Estructura de Carpetas

```
app/src/main/java/com/motodriver/app/
├── data/
│   ├── model/              # Modelos de datos
│   │   ├── Driver.kt       # Modelo de conductor
│   │   ├── Ride.kt         # Modelo de carrera
│   │   └── Client.kt       # Modelo de cliente
│   └── repository/         # Capa de datos
│       └── MotoDriverRepository.kt
├── ui/
│   ├── components/         # Composables reutilizables
│   │   ├── MotoButton.kt
│   │   ├── MotoInput.kt
│   │   ├── DriverHeader.kt
│   │   ├── RideItem.kt
│   │   ├── RideOverlay.kt
│   │   └── NotificationPopup.kt
│   ├── navigation/         # Configuración de navegación
│   │   ├── Screen.kt
│   │   └── AppNavigation.kt
│   ├── screens/            # Pantallas principales
│   │   ├── LoginScreen.kt
│   │   ├── AvailableRidesScreen.kt
│   │   └── CurrentRideScreen.kt
│   ├── theme/              # Tema de la aplicación
│   │   ├── Color.kt
│   │   └── Theme.kt
│   └── utils/              # Utilidades
│       └── Formatters.kt
├── viewmodel/              # ViewModels
│   ├── LoginViewModel.kt
│   ├── AvailableRidesViewModel.kt
│   └── CurrentRideViewModel.kt
├── MainActivity.kt         # Activity principal
└── MotoDriverApplication.kt
```

### Patrones de Diseño

#### MVVM (Model-View-ViewModel)
- **Model**: Modelos de datos y Repository
- **View**: Composables y Screens
- **ViewModel**: Lógica de presentación y estado

#### Repository Pattern
- Abstracción de fuente de datos
- Mock data para desarrollo
- Fácil migración a API real

#### State Hoisting
- Estado elevado a ViewModels
- UI sin estado (stateless composables)
- Flujo unidireccional de datos

## 🎨 Pantallas Implementadas

### 1. LoginScreen
**Ruta**: `login`

**Características**:
- Formulario con validación
- Estados de carga y error
- Autenticación vía mock repository
- Diseño responsivo con Material 3

**Estados UI**:
```kotlin
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String = "",
    val passwordError: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
```

### 2. AvailableRidesScreen
**Ruta**: `available_rides`

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
- `NotificationPopup`: Dialog para notificaciones

### 3. CurrentRideScreen
**Ruta**: `current_ride/{rideId}`

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

### Repository Implementado

#### `MotoDriverRepository.kt`
Mock repository que simula llamadas al backend:

- `login(email, password)`: Autenticación
- `getAvailableRides()`: Lista de carreras disponibles
- `acceptRide(rideId)`: Aceptar una carrera
- `validateOtp(rideId, otp)`: Validar código OTP
- `startRide(rideId)`: Iniciar carrera
- `getClient(clientId)`: Obtener datos del cliente
- `updateDriverStatus(status)`: Actualizar estado del conductor

### Preparación para Backend Real

Para conectar con un backend real:

1. Agregar Retrofit o Ktor para HTTP requests
2. Implementar Repository con llamadas reales
3. Agregar manejo de tokens JWT
4. Implementar WebSockets para actualizaciones en tiempo real

Ejemplo con Retrofit:
```kotlin
interface MotoDriverApi {
    @POST("auth/login")
    suspend fun login(@Body credentials: LoginRequest): Response<Driver>
    
    @GET("rides/available")
    suspend fun getAvailableRides(): Response<List<Ride>>
    
    @POST("rides/{rideId}/accept")
    suspend fun acceptRide(@Path("rideId") rideId: String): Response<Ride>
}
```

## 🎯 Modelos de Datos

### Driver
```kotlin
data class Driver(
    val id: String,
    val name: String,
    val phone: String,
    val vehiclePlate: String,
    val status: DriverStatus,
    val currentLocation: Location? = null,
    val rating: Double? = null
)

enum class DriverStatus(val displayName: String) {
    ACTIVE("Activo"),
    INACTIVE("Inactivo"),
    EN_ROUTE("En ruta"),
    IN_RIDE("En carrera")
}
```

### Ride
```kotlin
data class Ride(
    val id: String,
    val clientId: String,
    val originAddress: String,
    val destinationAddress: String,
    val originLocation: Location,
    val destinationLocation: Location,
    val estimatedAmount: Int,
    val distanceFromDriver: Double,
    val tripDistance: Double,
    val status: RideStatus,
    val createdAt: Date,
    val otp: String? = null
)
```

### Client
```kotlin
data class Client(
    val id: String,
    val name: String,
    val phone: String
)
```

## 🔔 Notificaciones

### Implementación Actual
- Simulación de notificaciones in-app
- Dialog popup para carreras cercanas
- Solo para conductores con estado "Activo"
- Distancia máxima: 1km

### Para Producción
Implementar notificaciones push reales con:
- **Firebase Cloud Messaging (FCM)**

Configuración necesaria:
1. Agregar dependencia de Firebase
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

- **Tipografía**: Material Design 3 typography
- **Componentes**: Material 3 components

### Interacciones
- Feedback visual en todos los botones
- Loading states en operaciones asíncronas
- Mensajes de error claros con Toast
- Confirmaciones para acciones críticas

## 🚀 Próximos Pasos

### Funcionalidades Pendientes

1. **Mapas**
   - Integrar Google Maps SDK
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
   - Caché de datos con Room
   - Offline mode
   - Optimización de batería

## 🧪 Testing

### Para Desarrollo
```bash
# Compilar el proyecto
./gradlew assembleDebug

# Instalar en dispositivo/emulador
./gradlew installDebug

# Ejecutar tests unitarios
./gradlew test
```

### Credenciales de Prueba
- **Email**: cualquier email válido
- **Password**: cualquier texto (mínimo 6 caracteres)

### Datos Mock
El repository incluye 3 carreras de ejemplo con diferentes distancias y montos.

## 📦 Dependencias Principales

```kotlin
dependencies {
    // Core Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")

    // Compose
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
}
```

## 🔒 Seguridad

### Implementado
- Validación de formularios
- OTP de 4 dígitos para iniciar carreras
- Kotlin null safety para prevención de errores

### Por Implementar
- Tokens JWT con refresh
- Encriptación de datos sensibles con EncryptedSharedPreferences
- Biometría (huella/Face ID)
- Certificate pinning
- ProGuard/R8 para ofuscación

## 📄 Licencia

Este proyecto es parte del sistema MotoDriver para gestión de moto-taxis.

---

**Desarrollado con ❤️ para facilitar el trabajo de los moto-taxistas**
