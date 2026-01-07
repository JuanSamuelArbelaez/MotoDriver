# 🏍️ MotoDriver - Guía de Inicio Rápido

## 📋 Requisitos Previos

- Node.js (v16 o superior)
- npm o yarn
- Expo Go app (para probar en dispositivo móvil)
- Android Studio o Xcode (para emuladores, opcional)

## 🚀 Instalación

### 1. Clonar el repositorio
```bash
git clone https://github.com/JuanSamuelArbelaez/MotoDriver.git
cd MotoDriver
```

### 2. Instalar dependencias
```bash
npm install
```

### 3. Iniciar el proyecto
```bash
npm start
```

## 📱 Ejecutar la Aplicación

### Opción 1: En tu dispositivo móvil (Recomendado para desarrollo)

1. Instala **Expo Go** desde:
   - [Play Store (Android)](https://play.google.com/store/apps/details?id=host.exp.exponent)
   - [App Store (iOS)](https://apps.apple.com/app/expo-go/id982107779)

2. Ejecuta:
   ```bash
   npm start
   ```

3. Escanea el código QR:
   - **Android**: Usa la app Expo Go
   - **iOS**: Usa la cámara del iPhone

### Opción 2: En emulador Android
```bash
npm run android
```
*Requiere Android Studio y un emulador configurado*

### Opción 3: En simulador iOS (Solo Mac)
```bash
npm run ios
```
*Requiere Xcode instalado*

### Opción 4: En navegador web
```bash
# Instalar dependencias web primero
npx expo install react-dom react-native-web

# Iniciar en web
npm run web
```

## 🧪 Probar la Aplicación

### Credenciales de Prueba

La aplicación usa datos mock, puedes iniciar sesión con:

- **Email**: Cualquier email válido (ej: `conductor@test.com`)
- **Password**: Cualquier texto de al menos 6 caracteres (ej: `123456`)

### Flujo de Prueba Completo

1. **Login**
   - Ingresa un email válido y contraseña (min 6 caracteres)
   - Click en "Iniciar sesión"

2. **Carreras Disponibles**
   - Verás 3 carreras mock ordenadas por distancia
   - Cambia tu estado usando los botones en el header
   - Selecciona diferentes carreras de la lista
   - El overlay inferior se actualiza con los detalles
   - Si tu estado es "Activo", verás una notificación popup después de 5 segundos

3. **Notificación Popup**
   - Solo aparece si:
     - Tu estado es "Activo"
     - Hay carreras a ≤1km de distancia
   - Puedes Aceptar o Rechazar

4. **Aceptar Carrera**
   - Click en "Aceptar carrera" en el overlay
   - Serás redirigido a la pantalla de Carrera Actual

5. **Carrera Actual**
   - Verás información del cliente
   - Detalles de origen y destino
   - Ingresa el OTP: `1234` (mock)
   - Click en "Validar código"
   - Una vez validado, click en "Iniciar carrera"

### Datos Mock Disponibles

#### Carreras
- **Carrera 1**: Calle 72 → Carrera 7 (0.5km, $8,500)
- **Carrera 2**: Carrera 15 → Avenida 68 (1.2km, $12,000)
- **Carrera 3**: Calle 100 → Calle 26 (2.5km, $15,000)

#### OTPs
- Todas las carreras tienen OTP: `1234`

## 🛠️ Comandos Útiles

### Desarrollo
```bash
# Iniciar en modo desarrollo
npm start

# Limpiar cache de Metro bundler
npm start -- --clear

# Ver logs del dispositivo
npm start -- --dev-client
```

### Verificación de Código
```bash
# Verificar TypeScript
npx tsc --noEmit

# Ver estructura del proyecto
tree src/ -L 2
```

## 📂 Estructura del Proyecto

```
MotoDriver/
├── App.tsx                 # Punto de entrada principal
├── src/
│   ├── components/         # Componentes reutilizables
│   ├── contexts/          # React Contexts (estado global)
│   ├── models/            # Tipos y modelos de datos
│   ├── navigation/        # Configuración de navegación
│   ├── screens/           # Pantallas principales
│   ├── services/          # Servicios (API mock)
│   └── utils/             # Utilidades
├── assets/                # Imágenes e iconos
├── ARCHITECTURE.md        # Documentación técnica detallada
└── package.json          # Dependencias
```

## 🎨 Características Implementadas

✅ **Autenticación**
- Login con validación
- Manejo de errores
- Estados de carga

✅ **Carreras Disponibles**
- Lista de carreras ordenadas por distancia
- Header con info del conductor
- Selector de estado (Activo/Inactivo/En ruta/En carrera)
- Overlay con detalles de carrera seleccionada
- Notificaciones para carreras cercanas

✅ **Carrera Actual**
- Info completa del cliente
- Validación de OTP
- Inicio de carrera
- Opción de cancelar

## 🔄 Próximas Funcionalidades

- [ ] Integración con backend real
- [ ] WebSockets para actualizaciones en tiempo real
- [ ] Mapas con rutas
- [ ] Notificaciones push reales
- [ ] Historial de carreras
- [ ] Perfil del conductor
- [ ] Chat con cliente

## 📚 Documentación Adicional

- [ARCHITECTURE.md](./ARCHITECTURE.md) - Arquitectura y decisiones técnicas
- [Expo Documentation](https://docs.expo.dev/)
- [React Navigation](https://reactnavigation.org/)

## ❓ Solución de Problemas

### Error: "Metro bundler no inicia"
```bash
# Limpiar cache
npm start -- --clear
```

### Error: "No se puede conectar al servidor"
- Verifica que tu computadora y dispositivo estén en la misma red WiFi
- Desactiva VPN o firewall temporalmente

### Error de dependencias
```bash
# Reinstalar node_modules
rm -rf node_modules
npm install
```

### Error en iOS Simulator
- Asegúrate de tener Xcode actualizado
- Ejecuta: `npx pod-install` (si hay carpeta ios/)

## 🤝 Soporte

Para preguntas o problemas:
1. Revisa la documentación en `ARCHITECTURE.md`
2. Verifica los logs en la terminal
3. Contacta al equipo de desarrollo

## 📄 Licencia

Este proyecto es parte del sistema MotoDriver para gestión de moto-taxis.

---

**¡Listo para desarrollar! 🚀**
