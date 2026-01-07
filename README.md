# 🏍️ MotoDriver - Aplicación Móvil para Moto-Taxistas

Aplicación móvil desarrollada con **React Native + TypeScript + Expo** para facilitar la gestión de carreras por parte de moto-taxistas.

## 🚀 Inicio Rápido

```bash
# Instalar dependencias
npm install

# Iniciar la aplicación
npm start
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

- **Framework**: Expo + React Native
- **Lenguaje**: TypeScript
- **Navegación**: React Navigation
- **Estado**: React Context API
- **Patrón**: Clean Architecture

📖 **[Ver Documentación Técnica Completa](./ARCHITECTURE.md)**

## 📂 Estructura del Proyecto

```
src/
├── components/     # Componentes reutilizables (Button, Input, etc.)
├── contexts/       # Estado global con React Context
├── models/         # Tipos TypeScript (Driver, Ride, Client)
├── navigation/     # Configuración de rutas
├── screens/        # Pantallas principales (Login, Rides, Current)
├── services/       # Mock API (listo para backend real)
└── utils/          # Helpers y formatters
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

**OTP:** `1234` (para todas las carreras)

## 📚 Documentación

- **[QUICKSTART.md](./QUICKSTART.md)** - Guía de inicio rápido
- **[ARCHITECTURE.md](./ARCHITECTURE.md)** - Arquitectura y decisiones técnicas

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

- Proponer y justificar el **framework móvil** (Flutter o React Native preferido).
- Mantener una **arquitectura limpia** (por ejemplo: Clean Architecture, MVVM).
- Separar claramente:
  - UI
  - Lógica de negocio
  - Servicios de red
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

## 2️⃣ Prompt Inicial para el Agente

### 🟢 Prompt de inicio

> Actúa como un **desarrollador móvil senior**.  
> Vamos a desarrollar una **aplicación móvil para moto-taxistas**.
>
> ### Requerimientos funcionales:
>
> #### Pantallas
>
> **1. Login**
> - Inicio de sesión con credenciales del moto-taxista.
> - Manejo de errores y estados de carga.
>
> **2. Carreras Disponibles**
> - Header con:
>   - Datos del conductor
>   - Estado actual: Activo / Inactivo / En ruta / En carrera
>
> - Lista central de carreras disponibles:
>   - Se actualiza en tiempo real cuando:
>     - Se agenda una carrera
>     - Se cancela
>     - Se acepta por otro conductor
>   - Ordenada por cercanía al punto de inicio.
>
> - Cada item de carrera debe mostrar:
>   - Distancia desde el conductor
>   - Dirección de origen
>   - Monto estimado
>
> **3. Overlay Footer (Carrera seleccionada)**
> - Visible por defecto con la carrera más cercana.
> - Muestra:
>   - Dirección origen
>   - Dirección destino
>   - Distancia del trayecto
>   - Botón para aceptar carrera
> - Al seleccionar otra carrera en la lista:
>   - El overlay se actualiza con esa información.
>
> **4. Notificación Pop-up**
> - Cuando un cliente solicita una carrera:
>   - Moto-taxistas activos a ≤ 1 km reciben un pop-up
>   - El pop-up incluye:
>     - Mensaje de carrera cercana
>     - Botón Aceptar
>     - Botón Rechazar
> - Moto-taxistas fuera del rango:
>   - Solo ven la carrera en la lista (sin pop-up).
>
> **5. Carrera Actual**
> - Se muestra al aceptar una carrera.
> - Incluye:
>   - Información completa de la carrera
>   - Datos del cliente (nombre, teléfono)
>
> - Paso de seguridad:
>   - Ingreso de OTP proporcionado al cliente por el bot
>   - Validación del OTP vía backend
>
> - Al validar OTP:
>   - Se habilita el inicio de la carrera
>
> ⚠️ La app NO incluye taxímetro ni cobros.
>
> ---
>
> ### Tareas iniciales:
> 1. Proponer el stack tecnológico.
> 2. Definir la arquitectura del proyecto.
> 3. Diseñar la navegación entre pantallas.
> 4. Crear los primeros mocks y modelos de datos.
> 5. Implementar la UI base de las tres pantallas principales.

---

## ✅ Nota final
Prioriza claridad, escalabilidad y buena experiencia de usuario para moto-taxistas que necesitan **aceptar carreras rápido y con mínima fricción**.
