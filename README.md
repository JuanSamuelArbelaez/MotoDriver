# 🏍️ MotoDriver - Aplicación Android para Moto-Taxistas

Aplicación Android nativa desarrollada con **Kotlin + Jetpack Compose** usando arquitectura **MVVM** para facilitar la gestión de carreras por parte de moto-taxistas.

## 🚀 Inicio Rápido

### Requisitos
- Android Studio Hedgehog (2023.1.1) o superior
- JDK 17
- Android SDK 34

### Compilar y Ejecutar

```bash
# Clonar el repositorio
git clone https://github.com/JuanSamuelArbelaez/MotoDriver.git
cd MotoDriver

# Abrir en Android Studio y sincronizar Gradle
# O compilar desde línea de comandos:
./gradlew assembleDebug
```

📖 **[Ver Guía de Inicio Completa](./QUICKSTART.md)**

## 📱 Características

✅ **Autenticación Segura**
- Login con validación de credenciales
- Manejo de errores y estados de carga

✅ **Gestión de Carreras**
- Lista de carreras disponibles ordenadas por proximidad
- Actualización en tiempo real
- Detalles completos de cada carrera

✅ **Notificaciones Inteligentes**
- Alertas para carreras cercanas (≤1km)
- Solo para conductores activos
- Aceptar/rechazar desde la notificación

✅ **Control de Estado**
- Cambio dinámico entre: Activo, Inactivo, En ruta, En carrera
- Header con información del conductor

✅ **Validación de Seguridad**
- Sistema OTP para iniciar carreras
- Verificación del código del cliente

## 🏗️ Arquitectura

- **Plataforma**: Android Nativo
- **Lenguaje**: Kotlin
- **UI Framework**: Jetpack Compose
- **Arquitectura**: MVVM (Model-View-ViewModel)
- **Navegación**: Jetpack Navigation Compose
- **Estado**: StateFlow + Compose State

📖 **[Ver Documentación Técnica Completa](./ARCHITECTURE.md)**

## 📂 Estructura del Proyecto

```
app/src/main/java/com/motodriver/app/
├── data/
│   ├── model/          # Modelos de datos (Driver, Ride, Client)
│   └── repository/     # Repositorio con datos mock
├── ui/
│   ├── components/     # Composables reutilizables
│   ├── navigation/     # Configuración de navegación
│   ├── screens/        # Pantallas principales
│   ├── theme/          # Tema y colores de la app
│   └── utils/          # Funciones de utilidad
├── viewmodel/          # ViewModels para cada pantalla
├── MainActivity.kt     # Activity principal
└── MotoDriverApplication.kt
```

## 🎯 Pantallas

### 1. Login
- Formulario con validación
- Feedback de errores
- Estados de carga

### 2. Carreras Disponibles
- Header con info del conductor
- Selector de estado
- Lista ordenada por distancia
- Overlay con carrera seleccionada
- Notificaciones popup

### 3. Carrera Actual
- Información del cliente
- Detalles origen/destino
- Validación OTP
- Iniciar carrera

## 🧪 Datos de Prueba

**Login (mock):**
- Email: cualquier email válido
- Password: mínimo 6 caracteres

**OTP:** `1234` (para la primera carrera)

## 📚 Documentación

- **[QUICKSTART.md](./QUICKSTART.md)** - Guía de inicio rápido
- **[ARCHITECTURE.md](./ARCHITECTURE.md)** - Arquitectura y decisiones técnicas
- **[FEATURES.md](./FEATURES.md)** - Lista de características
- **[DIAGRAMS.md](./DIAGRAMS.md)** - Diagramas de flujo y arquitectura

---

## 📋 Instrucciones para Agentes (Agent Instructions)

### 🎯 Objetivo principal
Desarrollar una **aplicación móvil para moto-taxistas**, que funcione como interfaz de aceptación y gestión de carreras solicitadas por clientes a través de bots de WhatsApp y Telegram, comunicándose con un backend central.

La app **NO maneja pagos ni taxímetro**, únicamente gestión de carreras.

---

### 🧠 Contexto del sistema completo
El sistema está compuesto por:

1. **App web administrativa**
   - Registro y gestión de moto-taxistas.

2. **Bot de WhatsApp y Telegram**
   - Registro de clientes.
   - Solicitud de carreras (origen, destino).
   - Notificaciones al cliente (moto-taxi en camino, etc).

3. **App móvil (este proyecto)**
   - Usada exclusivamente por moto-taxistas.
   - Permite ver, aceptar y gestionar carreras.

4. **Backend**
   - Centraliza autenticación, carreras, notificaciones y estados.
   - La app móvil consume este backend vía API REST y/o WebSockets.

---

### 🧱 Alcance del agente
El agente **solo debe desarrollar la app móvil**, incluyendo:

- UI / UX
- Arquitectura
- Estado de la aplicación
- Integración con backend (mockeada o real según avance)
- Manejo de notificaciones
- Manejo de ubicación y distancia

❌ Fuera de alcance:
- Desarrollo del backend
- Desarrollo del bot
- Lógica de pagos
- Taxímetro

---

### 📲 Lineamientos técnicos
El agente debe:

- Usar **Android Nativo con Kotlin y Jetpack Compose**.
- Mantener una **arquitectura MVVM limpia**.
- Separar claramente:
  - UI (Composables)
  - Lógica de negocio (ViewModels)
  - Servicios de datos (Repository)
- Usar **componentes reutilizables**.
- Documentar decisiones técnicas relevantes.
- Usar **mock data** cuando el backend aún no esté disponible.

---

### 🌍 Ubicación y distancias
- El moto-taxista tiene ubicación GPS en tiempo real.
- Las carreras se ordenan por **cercanía al punto de inicio**.
- El backend envía:
  - Coordenadas del origen
  - Distancia calculada o datos para calcularla
- El agente debe implementar la lógica necesaria para mostrar y actualizar esta información.

---

### 🔔 Notificaciones
- Las notificaciones push deben:
  - Activarse solo para moto-taxistas **Activos**
  - Estar limitadas a una distancia máxima (1 km inicialmente)
- El agente debe:
  - Preparar la estructura para push notifications
  - Implementar pop-ups in-app simulados si es necesario

---

### 🧪 Calidad
- El código debe ser legible, modular y mantenible.
- Evitar lógica acoplada a la UI.
- Preferir tipado fuerte y validaciones claras.
- Manejar errores y estados vacíos (sin carreras, error de red, etc).

---

## ✅ Nota final
Prioriza claridad, escalabilidad y buena experiencia de usuario para moto-taxistas que necesitan **aceptar carreras rápido y con mínima fricción**.
