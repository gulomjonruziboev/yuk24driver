# YUK24 Driver — Architecture

## Package layout

```
uz.yuk24.driver/
├── data/
│   ├── local/       TokenStore, ActiveOrderStore, SessionManager
│   ├── remote/      DriverApiService, interceptors, DTOs
│   └── repository/  Auth, Order, Profile, Location, Route, Health
├── domain/model/    Order, DriverProfile, DriverUiStatus, etc.
├── presentation/
│   ├── splash/      Session restore + health check
│   ├── login/
│   ├── dashboard/   Map-centric main screen
│   ├── map/         OSMDroid composable
│   ├── order/       Action bar, details sheet, cancel dialog
│   ├── reviews/
│   └── navigation/
├── service/         LocationForegroundService
└── di/              Hilt NetworkModule
```

## Data flow

```
Compose UI → ViewModel → Repository → Retrofit → YUK24 API
                ↓
         TokenStore / ActiveOrderStore
```

Authenticated requests attach `Authorization: Bearer <jwt>` via `AuthInterceptor`. HTTP 401 clears the session and emits a global logout event.

## Polling

| Data | Interval | Condition |
|------|----------|-----------|
| Available orders | 10s | UI status `IDLE`, GPS ready |
| Driver profile | 30s | While dashboard active |

## Location reporting

`LocationForegroundService` runs while logged in. Updates are sent to `PATCH /api/driver/location` when the driver moves >50m or every ~20s.

## Order UI state machine

| Local state | Backend status | Primary action |
|-------------|----------------|----------------|
| `IDLE` | — | Wait for queue |
| `NEW` | `queue` | Accept |
| `ACCEPTED` | `process` | Picked up |
| `PICKED_UP` | `pickedUp` | Finish |

Active orders are cached in DataStore for restore after app kill (no `GET /orders/active` on backend).

## Map

OSMDroid with OpenStreetMap tiles. Route geometry from backend GeoJSON (`RouteGeometryParser`). Polyline red when order is new, green otherwise.
