# YUK24 Driver — Android App

Native Kotlin driver application for the YUK24 on-demand cargo platform. Connects exclusively to the production REST API — no mock or demo mode.

## Requirements

- Android Studio Ladybug or newer (bundled JDK 17 — use this for Gradle builds)
- JDK 17 for command-line builds (`JAVA_HOME` must not point at JDK 21+)
- Android SDK 35
- Min device: API 26 (Android 8.0)

## Setup

1. Open this folder in Android Studio (`yuk24driver/`).
2. Create `local.properties` with your Android SDK path (Android Studio usually generates this):

```properties
sdk.dir=C\:\\Users\\YourName\\AppData\\Local\\Android\\Sdk
```

3. Optional — override API base URL for local backend:

```properties
yuk24.base.url.debug=http://10.0.2.2:5000/
```

Production default: `https://yuk24-backend.onrender.com/`

## Build & Run

```bash
./gradlew assembleDebug
```

Or use **Run** in Android Studio on a device/emulator with Google Play Services (required for location).

## Driver test account

Driver accounts are created by an admin via `POST /api/admin/drivers`. Use those credentials on the login screen.

## Features

- JWT login with secure token storage
- Full-screen OSMDroid map with live GPS
- Order queue polling (10s), accept / picked-up / delivered / cancel workflow
- Route polylines via `POST /api/route`
- Background location reporting via foreground service (`PATCH /api/driver/location`)
- Reviews and stats from `GET /api/driver/me`
- Uzbek (default) and English strings

## Documentation

- [DRIVER_TECHNICAL_MISSION.md](DRIVER_TECHNICAL_MISSION.md) — full specification
- [backendDocs.md](backendDocs.md) — API reference
- [ARCHITECTURE.md](ARCHITECTURE.md) — app structure and data flow
# yuk24driver
