# Notkert

Notkert es una aplicación Android desarrollada con Kotlin y Android Studio. Este proyecto está estructurado siguiendo las mejores prácticas de desarrollo móvil, utilizando Gradle para la gestión de dependencias y configuración.

## Características

- Arquitectura modular con carpetas separadas para frontend (`app/`) y backend (`backend-notkert/`).
- Integración con servicios de Google (ver `google-services.json`).
- Uso de Hilt para inyección de dependencias.
- Configuración de Proguard para ofuscación y optimización.
- Soporte para pruebas unitarias y de instrumentación.

## Requisitos

- Android Studio Electric Eel o superior
- JDK 11+
- Gradle 8+
- Dispositivo o emulador con Android 7.0 (API 24) o superior

## Instalación

1. Clona el repositorio:
   ```
   git clone https://github.com/JhonyAdr/notkert.git
   ```
2. Abre el proyecto en Android Studio.
3. Configura las credenciales necesarias en `local.properties` y `google-services.json`.
4. Sincroniza el proyecto con Gradle.
5. Ejecuta la aplicación en un emulador o dispositivo físico.

## Estructura del proyecto

- `app/`: Código fuente de la aplicación Android.
- `backend-notkert/`: Lógica de backend (si aplica).
- `gradle/`: Configuración de dependencias y wrapper de Gradle.
- `build.gradle.kts`, `settings.gradle.kts`: Archivos de configuración principal.

## Contribuir

Las contribuciones son bienvenidas. Por favor, abre un issue o envía un pull request.

## Licencia

Este proyecto está bajo la licencia