# 📱 Agente Copilot – Desarrollo App Móvil Moto-Taxi

Este documento define las **instrucciones de comportamiento del agente** y el **prompt inicial de trabajo** para desarrollar la aplicación móvil de moto-taxistas.

---

## 1️⃣ Instrucciones para el Agente (Agent Instructions)

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
