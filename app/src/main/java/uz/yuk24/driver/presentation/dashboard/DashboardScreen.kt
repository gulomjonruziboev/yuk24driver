package uz.yuk24.driver.presentation.dashboard

import android.Manifest
import android.annotation.SuppressLint
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import uz.yuk24.driver.R
import uz.yuk24.driver.domain.model.DriverUiStatus
import uz.yuk24.driver.presentation.map.DriverMapView
import uz.yuk24.driver.presentation.order.CancelOrderDialog
import uz.yuk24.driver.presentation.order.OrderActionBar
import uz.yuk24.driver.presentation.order.OrderDetailsSheet
import uz.yuk24.driver.presentation.theme.AmberAccent
import uz.yuk24.driver.presentation.theme.SurfaceWhite
import uz.yuk24.driver.presentation.theme.TextPrimary
import uz.yuk24.driver.presentation.theme.TextSecondary
import uz.yuk24.driver.presentation.theme.GreenAccent
import uz.yuk24.driver.presentation.theme.Primary
import uz.yuk24.driver.presentation.theme.RedAccent
import uz.yuk24.driver.presentation.theme.SurfaceWhite
import uz.yuk24.driver.util.formatRating

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
@Composable
fun DashboardScreen(
    onNavigateToReviews: () -> Unit,
    onLogout: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val locationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(uiState.toastMessage) {
        uiState.toastMessage?.let { key ->
            val message = when (key) {
                "network" -> context.getString(R.string.network_error)
                "order_unavailable" -> context.getString(R.string.order_unavailable)
                "forbidden" -> context.getString(R.string.forbidden_order)
                else -> key
            }
            snackbarHostState.showSnackbar(message)
            viewModel.clearToast()
        }
    }

    LaunchedEffect(Unit) {
        if (!locationPermission.status.isGranted) {
            locationPermission.launchPermissionRequest()
        }
    }

    LaunchedEffect(locationPermission.status.isGranted) {
        if (locationPermission.status.isGranted) {
            viewModel.startLocationService()
        }
    }

    DisposableEffect(locationPermission.status.isGranted) {
        if (!locationPermission.status.isGranted) {
            viewModel.setGeoLoading(false)
            onDispose { }
        } else {
            val fused = LocationServices.getFusedLocationProviderClient(context)
            val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5_000L)
                .setMinUpdateIntervalMillis(3_000L)
                .build()
            val callback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    result.lastLocation?.let {
                        viewModel.updateDriverPosition(it.latitude, it.longitude)
                    }
                }
            }
            fused.requestLocationUpdates(request, callback, Looper.getMainLooper())
            onDispose { fused.removeLocationUpdates(callback) }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceWhite),
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocalShipping, contentDescription = null, tint = Primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("YUK ", fontWeight = FontWeight.Bold)
                        Text("24", color = Primary, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            stringResource(R.string.driver_mode),
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                },
                actions = {
                    BadgedBox(
                        badge = {
                            if (uiState.reviewCount > 0) {
                                Badge { Text(uiState.reviewCount.toString()) }
                            }
                        }
                    ) {
                        IconButton(onClick = onNavigateToReviews) {
                            Icon(Icons.Default.Star, contentDescription = stringResource(R.string.driver_my_reviews))
                        }
                    }
                    uiState.avgRating?.let { avg ->
                        Text(
                            text = formatRating(avg),
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                    IconButton(onClick = {
                        viewModel.logout()
                        onLogout()
                    }) {
                        Icon(Icons.Default.Logout, contentDescription = stringResource(R.string.driver_logout))
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            DriverMapView(
                driverPos = uiState.driverPos,
                order = uiState.order,
                routePoints = uiState.routeInfo?.points.orEmpty(),
                uiStatus = uiState.uiStatus,
                onMarkerClick = viewModel::showOrderDetails
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                if (uiState.isOffline) {
                    Text(
                        text = stringResource(R.string.offline_banner),
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(RedAccent)
                            .padding(8.dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                if (uiState.uiStatus != DriverUiStatus.IDLE) {
                    StatusBanner(uiStatus = uiState.uiStatus, orderId = uiState.order?.orderId)
                }
            }

            if (uiState.geoLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = Primary, modifier = Modifier.size(32.dp))
                        Text(
                            stringResource(R.string.detecting_location),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            if (uiState.uiStatus == DriverUiStatus.IDLE && !uiState.geoLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier.padding(horizontal = 32.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.driver_waiting),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                            Text(
                                text = stringResource(R.string.driver_waiting_desc),
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }

            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                OrderActionBar(
                    uiStatus = uiState.uiStatus,
                    isLoading = uiState.actionLoading,
                    onDetails = viewModel::showOrderDetails,
                    onPrimary = {
                        when (uiState.uiStatus) {
                            DriverUiStatus.NEW -> viewModel.acceptOrder()
                            DriverUiStatus.ACCEPTED -> viewModel.pickedUp()
                            DriverUiStatus.PICKED_UP -> viewModel.deliverOrder()
                            DriverUiStatus.IDLE -> Unit
                        }
                    },
                    onCancel = viewModel::showCancelDialog
                )
            }

            uiState.order?.let { order ->
                OrderDetailsSheet(
                    order = order,
                    routeInfo = uiState.routeInfo,
                    visible = uiState.showOrderDetails,
                    onDismiss = viewModel::hideOrderDetails
                )
                CancelOrderDialog(
                    order = order,
                    visible = uiState.showCancelDialog,
                    isLoading = uiState.actionLoading,
                    onConfirm = viewModel::cancelOrder,
                    onDismiss = viewModel::hideCancelDialog
                )
            }
        }
    }
}

@Composable
private fun StatusBanner(uiStatus: DriverUiStatus, orderId: String?) {
    val (color, textRes) = when (uiStatus) {
        DriverUiStatus.NEW -> RedAccent to R.string.driver_new_order
        DriverUiStatus.ACCEPTED -> AmberAccent to R.string.driver_go_to_pickup
        DriverUiStatus.PICKED_UP -> GreenAccent to R.string.driver_go_to_delivery
        DriverUiStatus.IDLE -> return
    }
    Text(
        text = buildString {
            append(stringResource(textRes))
            orderId?.let { append(" — $it") }
        },
        color = Color.White,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .fillMaxWidth()
            .background(color)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    )
}
