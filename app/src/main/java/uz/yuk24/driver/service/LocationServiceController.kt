package uz.yuk24.driver.service

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationServiceController @Inject constructor() {

    fun hasLocationPermission(context: Context): Boolean {
        val fine = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val coarse = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        return fine || coarse
    }

    fun start(context: Context) {
        if (!hasLocationPermission(context)) return
        val intent = Intent(context, LocationForegroundService::class.java)
        ContextCompat.startForegroundService(context, intent)
    }

    fun stop(context: Context) {
        context.stopService(Intent(context, LocationForegroundService::class.java))
    }
}
