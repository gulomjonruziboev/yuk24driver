package uz.yuk24.driver.util

import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatPriceUzs(price: Int): String {
    val formatter = NumberFormat.getNumberInstance(Locale.US)
    return "${formatter.format(price)}"
}

fun formatRating(avg: Double): String = String.format(Locale.US, "%.1f", avg)

fun formatReviewDate(iso: String): String {
    if (iso.isBlank()) return ""
    return runCatching {
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())
        formatter.format(Instant.parse(iso).atZone(ZoneId.systemDefault()))
    }.getOrElse { iso.take(10) }
}
