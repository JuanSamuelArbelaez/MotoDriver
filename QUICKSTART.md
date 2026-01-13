# 🏍️ MotoDriver - Guía de Inicio Rápido

## 📋 Requisitos Previos

- Android Studio Hedgehog (2023.1.1) o superior
- JDK 17
- Android SDK 34
- Dispositivo Android o emulador (API 26+)

## 🚀 Instalación

### 1. Clonar el repositorio
```bash
git clone https://github.com/JuanSamuelArbelaez/MotoDriver.git
cd MotoDriver
```

### 2. Abrir en Android Studio
1. Abre Android Studio
2. Selecciona "Open an existing project"
3. Navega a la carpeta del proyecto y selecciónala
4. Espera a que Gradle sincronice las dependencias

### 3. Compilar el proyecto
```bash
# Desde la terminal
./gradlew assembleDebug

# O usar el menú Build > Make Project en Android Studio
```

## 📱 Ejecutar la Aplicación

### Opción 1: En emulador Android (Recomendado para desarrollo)

1. En Android Studio, abre AVD Manager (Tools > Device Manager)
2. Crea un nuevo dispositivo virtual o selecciona uno existente
3. Ejecuta la aplicación con el botón "Run" o `Shift + F10`

### Opción 2: En dispositivo físico

1. Habilita "Opciones de desarrollador" en tu dispositivo Android
2. Activa "Depuración USB"
3. Conecta el dispositivo por USB
4. Ejecuta la aplicación desde Android Studio

### Opción 3: Desde línea de comandos
```bash
# Instalar en dispositivo/emulador conectado
./gradlew installDebug

# Iniciar la app
adb shell am start -n com.motodriver.app/.MainActivity
```

## 🧪 Probar la Aplicación

### Credenciales de Prueba

La aplicación usa datos mock, puedes iniciar sesión con:

- **Email**: Cualquier email válido (ej: `conductor@test.com`)
- **Password**: Cualquier texto de al menos 6 caracteres (ej: `123456`)

### Flujo de Prueba Completo

1. **Login**
   - Ingresa un email válido y contraseña (min 6 caracteres)
   - Toca "Iniciar sesión"

2. **Carreras Disponibles**
   - Verás 3 carreras mock ordenadas por distancia
   - Cambia tu estado usando los chips en el header
   - Selecciona diferentes carreras de la lista
   - El overlay inferior se actualiza con los detalles
   - Si tu estado es "Activo", verás una notificación popup después de 5 segundos

3. **Notificación Popup**
   - Solo aparece si:
     - Tu estado es "Activo"
     - Hay carreras a ≤1km de distancia
   - Puedes Aceptar o Rechazar

4. **Aceptar Carrera**
   - Toca "Aceptar carrera" en el overlay
   - Serás redirigido a la pantalla de Carrera Actual

5. **Carrera Actual**
   - Verás información del cliente
   - Detalles de origen y destino
   - Ingresa el OTP: `1234` (mock)
   - Toca "Validar código"
   - Una vez validado, toca "Iniciar carrera"

### Datos Mock Disponibles

#### Carreras
- **Carrera 1**: Calle 72 → Carrera 7 (0.5km, $8,500) - OTP: 1234
- **Carrera 2**: Carrera 15 → Avenida 68 (1.2km, $12,000) - OTP: 5678
- **Carrera 3**: Calle 100 → Calle 26 (2.5km, $15,000) - OTP: 9012

## 🛠️ Comandos Útiles

### Desarrollo
```bash
# Compilar debug
./gradlew assembleDebug

# Compilar release
./gradlew assembleRelease

# Limpiar proyecto
./gradlew clean

# Limpiar y recompilar
./gradlew clean assembleDebug
```

### Testing
```bash
# Ejecutar tests unitarios
./gradlew test

# Ejecutar tests instrumentados
./gradlew connectedAndroidTest
```

### Verificación
```bash
# Verificar lint
./gradlew lint

# Ver dependencias
./gradlew app:dependencies
```

## 📂 Estructura del Proyecto

```
MotoDriver/
├── app/
│   ├── src/main/
│   │   ├── java/com/motodriver/app/
│   │   │   ├── data/           # Modelos y repositorio
│   │   │   ├── ui/             # Composables y pantallas
│   │   │   ├── viewmodel/      # ViewModels
│   │   │   ├── MainActivity.kt
│   │   │   └── MotoDriverApplication.kt
│   │   ├── res/                # Recursos (strings, colores, etc.)
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts        # Dependencias del módulo
├── gradle/
├── build.gradle.kts            # Configuración raíz
├── settings.gradle.kts
├── ARCHITECTURE.md             # Documentación técnica
└── README.md                   # Documentación principal
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
- [ ] Mapas con rutas (Google Maps)
- [ ] Notificaciones push reales (FCM)
- [ ] Historial de carreras
- [ ] Perfil del conductor
- [ ] Chat con cliente

## 📚 Documentación Adicional

- [ARCHITECTURE.md](./ARCHITECTURE.md) - Arquitectura y decisiones técnicas
- [FEATURES.md](./FEATURES.md) - Lista completa de características
- [DIAGRAMS.md](./DIAGRAMS.md) - Diagramas de flujo
- [Android Developer Docs](https://developer.android.com/docs)
- [Jetpack Compose Docs](https://developer.android.com/jetpack/compose)

## ❓ Solución de Problemas

### Error: "SDK location not found"
Crea un archivo `local.properties` en la raíz del proyecto:
```properties
sdk.dir=/path/to/your/Android/Sdk
```

### Error: Gradle sync failed
```bash
# Limpiar cache de Gradle
./gradlew --stop
rm -rf ~/.gradle/caches
./gradlew clean
```

### Error: Emulador no inicia
- Verifica que tienes suficiente RAM (al menos 8GB recomendado)
- Habilita la virtualización en BIOS (VT-x/AMD-V)
- Actualiza HAXM o usa el emulador x86_64

### Error: "minSdk version" incompatible
La app requiere Android 8.0 (API 26) o superior. Asegúrate de que tu dispositivo o emulador cumpla este requisito.

## 🤝 Soporte

Para preguntas o problemas:
1. Revisa la documentación en `ARCHITECTURE.md`
2. Verifica los logs en Logcat de Android Studio
3. Contacta al equipo de desarrollo

## 📄 Licencia

Este proyecto es parte del sistema MotoDriver para gestión de moto-taxis.

---

**¡Listo para desarrollar! 🚀**
