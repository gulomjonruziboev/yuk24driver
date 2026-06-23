package uz.yuk24.driver.presentation.order

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import uz.yuk24.driver.R
import uz.yuk24.driver.domain.model.Order
import uz.yuk24.driver.presentation.theme.GreenAccent
import uz.yuk24.driver.presentation.theme.RedAccent

private const val MIN_CHARS = 150

@Composable
fun CancelOrderDialog(
    order: Order?,
    visible: Boolean,
    isLoading: Boolean,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    if (!visible || order == null) return
    var explanation by remember(visible) { mutableStateOf("") }
    val trimmed = explanation.trim()
    val charsLeft = MIN_CHARS - trimmed.length
    val isValid = trimmed.length >= MIN_CHARS

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.driver_cancel_order), fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text(
                    text = "${stringResource(R.string.driver_order_id)}: ${order.orderId}",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(stringResource(R.string.driver_cancel_explanation))
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = explanation,
                    onValueChange = { explanation = it },
                    placeholder = { Text(stringResource(R.string.driver_cancel_explanation_placeholder)) },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 4
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = if (charsLeft > 0) {
                            "${stringResource(R.string.driver_cancel_min_chars)} ($charsLeft ${stringResource(R.string.driver_cancel_chars_left)})"
                        } else {
                            "✓ ${trimmed.length} / $MIN_CHARS"
                        },
                        color = if (charsLeft > 0) RedAccent else GreenAccent
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(trimmed) },
                enabled = isValid && !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = RedAccent)
            ) {
                Text(stringResource(R.string.driver_cancel_confirm))
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text(stringResource(R.string.dismiss))
            }
        }
    )
}
