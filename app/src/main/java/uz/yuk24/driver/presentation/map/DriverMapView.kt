package uz.yuk24.driver.presentation.map

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import uz.yuk24.driver.R
import uz.yuk24.driver.domain.model.DriverUiStatus
import uz.yuk24.driver.domain.model.LatLng
import uz.yuk24.driver.domain.model.Order

private const val TASHKENT_LAT = 41.2995
private const val TASHKENT_LNG = 69.2401

@SuppressLint("ClickableViewAccessibility")
@Composable
fun DriverMapView(
    driverPos: LatLng?,
    order: Order?,
    routePoints: List<LatLng>,
    uiStatus: DriverUiStatus,
    onMarkerClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val mapView = remember {
        MapView(context).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            controller.setZoom(13.0)
            controller.setCenter(GeoPoint(TASHKENT_LAT, TASHKENT_LNG))
        }
    }

    val driverMarker = remember {
        Marker(mapView).apply {
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
            icon = ContextCompat.getDrawable(context, R.drawable.ic_driver_dot)
            title = "Driver"
        }
    }

    val pickupMarker = remember {
        Marker(mapView).apply {
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            icon = ContextCompat.getDrawable(context, R.drawable.ic_pin_green)
            title = "Pickup"
            setOnMarkerClickListener { _, _ ->
                onMarkerClick()
                true
            }
        }
    }

    val deliveryMarker = remember {
        Marker(mapView).apply {
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            icon = ContextCompat.getDrawable(context, R.drawable.ic_pin_red)
            title = "Delivery"
            setOnMarkerClickListener { _, _ ->
                onMarkerClick()
                true
            }
        }
    }

    val polyline = remember {
        Polyline(mapView).apply {
            outlinePaint.strokeWidth = 10f
            outlinePaint.isAntiAlias = true
        }
    }

    LaunchedEffect(driverPos, order, routePoints, uiStatus) {
        mapView.overlays.clear()

        driverPos?.let {
            driverMarker.position = GeoPoint(it.lat, it.lng)
            mapView.overlays.add(driverMarker)
        }

        order?.let { o ->
            if (o.pickup.coords.size >= 2) {
                pickupMarker.position = GeoPoint(o.pickup.lat, o.pickup.lng)
                mapView.overlays.add(pickupMarker)
            }
            if (o.delivery.coords.size >= 2) {
                deliveryMarker.position = GeoPoint(o.delivery.lat, o.delivery.lng)
                mapView.overlays.add(deliveryMarker)
            }
        }

        if (routePoints.size >= 2) {
            polyline.setPoints(routePoints.map { GeoPoint(it.lat, it.lng) })
            val color = if (uiStatus == DriverUiStatus.NEW) {
                android.graphics.Color.parseColor("#EF4444")
            } else {
                android.graphics.Color.parseColor("#22C55E")
            }
            polyline.outlinePaint.color = color
            mapView.overlays.add(polyline)
        }

        val points = buildList {
            driverPos?.let { add(GeoPoint(it.lat, it.lng)) }
            order?.pickup?.takeIf { it.coords.size >= 2 }?.let {
                add(GeoPoint(it.lat, it.lng))
            }
            order?.delivery?.takeIf { it.coords.size >= 2 }?.let {
                add(GeoPoint(it.lat, it.lng))
            }
        }

        when {
            points.size >= 2 -> {
                val lats = points.map { it.latitude }
                val lngs = points.map { it.longitude }
                val box = BoundingBox(
                    lats.maxOrNull()!!,
                    lngs.maxOrNull()!!,
                    lats.minOrNull()!!,
                    lngs.minOrNull()!!
                )
                mapView.zoomToBoundingBox(box, true, 120)
            }
            points.size == 1 -> mapView.controller.animateTo(points.first())
            driverPos == null -> mapView.controller.setCenter(GeoPoint(TASHKENT_LAT, TASHKENT_LNG))
        }

        mapView.invalidate()
    }

    DisposableEffect(mapView) {
        mapView.onResume()
        onDispose { mapView.onPause() }
    }

    AndroidView(
        factory = { mapView },
        modifier = modifier.fillMaxSize()
    )
}
