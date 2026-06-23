package uz.yuk24.driver.service

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import uz.yuk24.driver.R
import uz.yuk24.driver.data.local.TokenStore
import uz.yuk24.driver.data.repository.LocationRepository
import uz.yuk24.driver.domain.model.LatLng
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@AndroidEntryPoint
class LocationForegroundService : Service() {

    @Inject lateinit var locationRepository: LocationRepository
    @Inject lateinit var tokenStore: TokenStore

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var lastReported: LatLng? = null
    private var lastReportTime = 0L

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            val location = result.lastLocation ?: return
            val current = LatLng(location.latitude, location.longitude)
            val now = System.currentTimeMillis()
            val last = lastReported
            val movedEnough = last == null || distanceMeters(last, current) > 50.0
            val timeElapsed = now - lastReportTime > 20_000L
            if (movedEnough || timeElapsed) {
                lastReported = current
                lastReportTime = now
                serviceScope.launch {
                    if (tokenStore.getToken() != null) {
                        locationRepository.updateLocation(current.lat, current.lng)
                    }
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (!hasLocationPermission()) {
            stopSelf()
            return
        }
        createNotificationChannel()
        try {
            startForeground(NOTIFICATION_ID, buildNotification())
        } catch (_: SecurityException) {
            stopSelf()
            return
        }
        if (tokenStore.getToken() != null) {
            startLocationUpdates()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!hasLocationPermission()) {
            stopSelf()
            return START_NOT_STICKY
        }
        if (tokenStore.getToken() != null) {
            startLocationUpdates()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        LocationServices.getFusedLocationProviderClient(this)
            .removeLocationUpdates(locationCallback)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun startLocationUpdates() {
        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 15_000L)
            .setMinUpdateIntervalMillis(10_000L)
            .build()
        try {
            LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(request, locationCallback, Looper.getMainLooper())
        } catch (_: SecurityException) {
            stopSelf()
        }
    }

    private fun buildNotification(): Notification =
        NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.location_service_title))
            .setContentText(getString(R.string.location_service_text))
            .setSmallIcon(R.drawable.ic_logo)
            .setOngoing(true)
            .build()

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            getString(R.string.location_service_title),
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    private fun hasLocationPermission(): Boolean {
        val fine = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        val coarse = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        return fine || coarse
    }

    private fun distanceMeters(a: LatLng, b: LatLng): Double {
        val r = 6_371_000.0
        val dLat = Math.toRadians(b.lat - a.lat)
        val dLng = Math.toRadians(b.lng - a.lng)
        val lat1 = Math.toRadians(a.lat)
        val lat2 = Math.toRadians(b.lat)
        val h = sin(dLat / 2) * sin(dLat / 2) +
            cos(lat1) * cos(lat2) * sin(dLng / 2) * sin(dLng / 2)
        return 2 * r * atan2(sqrt(h), sqrt(1 - h))
    }

    companion object {
        private const val CHANNEL_ID = "yuk24_driver_location"
        private const val NOTIFICATION_ID = 1001
    }
}
