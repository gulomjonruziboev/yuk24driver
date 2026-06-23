package uz.yuk24.driver.presentation.order

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import uz.yuk24.driver.R
import uz.yuk24.driver.domain.model.Order
import uz.yuk24.driver.domain.model.RouteInfo
import uz.yuk24.driver.util.formatPriceUzs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsSheet(
    order: Order,
    routeInfo: RouteInfo?,
    visible: Boolean,
    onDismiss: () -> Unit
) {
    if (!visible) return
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
            Text(
                text = stringResource(R.string.driver_order_details),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            DetailRow(stringResource(R.string.driver_order_id), order.orderId)
            DetailRow(stringResource(R.string.driver_pickup), order.pickup.label)
            DetailRow(stringResource(R.string.driver_delivery), order.delivery.label)
            val distance = routeInfo?.distanceKm ?: order.distanceKm
            val duration = routeInfo?.durationMin?.toInt() ?: order.durationMin
            DetailRow(
                stringResource(R.string.driver_distance),
                "$distance ${stringResource(R.string.km_short)}"
            )
            duration?.let {
                DetailRow(
                    stringResource(R.string.driver_duration),
                    "$it ${stringResource(R.string.minutes_short)}"
                )
            }
            order.customerName?.let { DetailRow(stringResource(R.string.driver_customer), it) }
            DetailRow(stringResource(R.string.driver_phone), order.customerPhone)
            order.notes?.takeIf { it.isNotBlank() }?.let {
                DetailRow(stringResource(R.string.driver_notes), it)
            }
            DetailRow(
                stringResource(R.string.driver_price),
                "${formatPriceUzs(order.price)} ${stringResource(R.string.currency_uzs)}"
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${order.customerPhone}"))
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.driver_call))
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(text = "$label: ", fontWeight = FontWeight.Medium)
        Text(text = value)
    }
}
