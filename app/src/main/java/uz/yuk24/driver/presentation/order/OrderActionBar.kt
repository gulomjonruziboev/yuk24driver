package uz.yuk24.driver.presentation.order

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uz.yuk24.driver.R
import uz.yuk24.driver.domain.model.DriverUiStatus
import uz.yuk24.driver.presentation.theme.AmberAccent
import uz.yuk24.driver.presentation.theme.GreenAccent
import uz.yuk24.driver.presentation.theme.Primary
import uz.yuk24.driver.presentation.theme.RedAccent
import uz.yuk24.driver.presentation.theme.SurfaceWhite

@Composable
fun OrderActionBar(
    uiStatus: DriverUiStatus,
    isLoading: Boolean,
    onDetails: () -> Unit,
    onPrimary: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiStatus == DriverUiStatus.IDLE) return

    val (label, color, icon) = when (uiStatus) {
        DriverUiStatus.NEW -> Triple(
            stringResource(R.string.driver_accept),
            GreenAccent,
            Icons.Default.CheckCircle
        )
        DriverUiStatus.ACCEPTED -> Triple(
            stringResource(R.string.driver_picked_up),
            AmberAccent,
            Icons.Default.LocalShipping
        )
        DriverUiStatus.PICKED_UP -> Triple(
            stringResource(R.string.driver_finish),
            Primary,
            Icons.Default.Flag
        )
        DriverUiStatus.IDLE -> return
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(
            onClick = onDetails,
            modifier = Modifier.size(64.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(2.dp, Primary),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = SurfaceWhite,
                contentColor = Primary
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = stringResource(R.string.driver_order_details),
                modifier = Modifier.size(32.dp)
            )
        }

        Button(
            onClick = onPrimary,
            enabled = !isLoading,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = color)
        ) {
            Icon(icon, contentDescription = null, tint = Color.White)
            Text(label, modifier = Modifier.padding(start = 8.dp), color = Color.White)
        }

        IconButton(
            onClick = onCancel,
            modifier = Modifier.size(56.dp)
        ) {
            Icon(Icons.Default.Cancel, contentDescription = stringResource(R.string.driver_cancel_order), tint = RedAccent)
        }
    }
}
