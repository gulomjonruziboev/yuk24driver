package uz.yuk24.driver.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import uz.yuk24.driver.domain.model.DriverProfile
import uz.yuk24.driver.domain.model.DriverStats
import uz.yuk24.driver.domain.model.LocationPoint
import uz.yuk24.driver.domain.model.Order
import uz.yuk24.driver.domain.model.OrderStatus
import uz.yuk24.driver.domain.model.Review
import uz.yuk24.driver.domain.model.ReviewsPage
import uz.yuk24.driver.domain.model.ReviewsPagination
import uz.yuk24.driver.domain.model.ReviewsSummary

@Serializable
data class LoginRequest(val username: String, val password: String)

@Serializable
data class LoginUserDto(
    val id: String,
    val username: String,
    val name: String? = null,
    val active: Boolean = true,
    val role: String? = null
)

@Serializable
data class LoginResponse(val token: String, val user: LoginUserDto)

@Serializable
data class LocationPointDto(
    val label: String = "",
    val coords: List<Double> = emptyList()
)

@Serializable
data class OrderDto(
    @SerialName("_id") val mongoId: String? = null,
    val orderId: String? = null,
    val customerName: String? = null,
    val customerPhone: String = "",
    val notes: String? = null,
    val pickup: LocationPointDto? = null,
    val delivery: LocationPointDto? = null,
    val loadSize: String = "medium",
    val unloading: Boolean = false,
    val price: Int = 0,
    val distanceKm: Double = 0.0,
    val durationMin: Int? = null,
    val status: String = "queue",
    val cancelReason: String? = null
)

@Serializable
data class CancelOrderRequest(val reason: String)

@Serializable
data class LocationUpdateRequest(val lat: Double, val lng: Double)

@Serializable
data class LocationUpdateResponse(val ok: Boolean, val lastSeenAt: String? = null)

@Serializable
data class RouteRequest(val start: List<Double>, val end: List<Double>)

@Serializable
data class RouteResponse(
    val distanceKm: Double,
    val durationMin: Double,
    val geometry: JsonElement? = null
)

@Serializable
data class HealthResponse(
    val ok: Boolean? = null,
    val db: String? = null,
    val timestamp: String? = null
)

@Serializable
data class DriverStatsDto(
    val completedOrders: Int = 0,
    val cancelledOrders: Int = 0,
    val avgDeliveryMin: Int? = null
)

@Serializable
data class ReviewDto(
    @SerialName("_id") val id: String? = null,
    val rating: Double = 0.0,
    val comment: String? = null,
    val customerName: String? = null,
    val customerPhone: String? = null,
    @SerialName("orderId") val orderIdRaw: JsonElement? = null,
    val createdAt: String? = null,
    val date: String? = null
)

@Serializable
data class ReviewsSummaryDto(
    val avgRating: Double? = null,
    val totalReviews: Int = 0
)

@Serializable
data class PaginationDto(
    val page: Int = 1,
    val limit: Int = 20,
    val total: Int = 0,
    val pages: Int = 1
)

@Serializable
data class DriverReviewsResponseDto(
    val reviews: List<ReviewDto> = emptyList(),
    val summary: ReviewsSummaryDto? = null,
    val pagination: PaginationDto? = null,
    val avgRating: Double? = null,
    val count: Int = 0
)

@Serializable
data class DriverProfileDto(
    val id: String? = null,
    @SerialName("_id") val mongoId: String? = null,
    val username: String = "",
    val name: String? = null,
    val phone: String? = null,
    val vehicleInfo: String? = null,
    val active: Boolean = true,
    val stats: DriverStatsDto? = null,
    val reviews: List<ReviewDto>? = null
)

fun OrderDto.toDomain(): Order {
    val id = mongoId ?: orderId.orEmpty()
    val displayId = orderId ?: mongoId.orEmpty()
    return Order(
        id = id,
        orderId = displayId,
        customerName = customerName,
        customerPhone = customerPhone,
        notes = notes,
        pickup = pickup?.toDomain() ?: LocationPoint("", emptyList()),
        delivery = delivery?.toDomain() ?: LocationPoint("", emptyList()),
        loadSize = loadSize,
        unloading = unloading,
        price = price,
        distanceKm = distanceKm,
        durationMin = durationMin,
        status = OrderStatus.fromApi(status),
        cancelReason = cancelReason
    )
}

private fun LocationPointDto.toDomain() = LocationPoint(label, coords)

fun DriverProfileDto.toDomain(): DriverProfile {
    val profileId = id ?: mongoId.orEmpty()
    return DriverProfile(
        id = profileId,
        username = username,
        name = name,
        phone = phone,
        vehicleInfo = vehicleInfo,
        active = active,
        stats = stats?.let {
            DriverStats(it.completedOrders, it.cancelledOrders, it.avgDeliveryMin)
        } ?: DriverStats(0, 0, null),
        reviews = reviews.orEmpty().map { it.toDomain() }
    )
}

private fun resolveReviewOrderId(orderIdRaw: JsonElement?): String? {
    if (orderIdRaw == null) return null
    return when (orderIdRaw) {
        is JsonPrimitive -> jsonPrimitiveContent(orderIdRaw)
        is JsonObject -> jsonPrimitiveContent(orderIdRaw["orderId"]?.jsonPrimitive)
            ?: jsonPrimitiveContent(orderIdRaw["_id"]?.jsonPrimitive)
        else -> null
    }
}

private fun jsonPrimitiveContent(primitive: JsonPrimitive?): String? =
    primitive?.let { runCatching { it.content }.getOrNull() }

fun ReviewDto.toDomain() = Review(
    id = id.orEmpty(),
    rating = rating.toInt().coerceIn(0, 5),
    comment = comment,
    customerName = customerName ?: customerPhone,
    orderId = resolveReviewOrderId(orderIdRaw),
    createdAt = createdAt ?: date.orEmpty()
)

fun DriverReviewsResponseDto.toDomain(): ReviewsPage {
    val mappedReviews = reviews.map { it.toDomain() }
    val total = summary?.totalReviews?.takeIf { it > 0 }
        ?: pagination?.total?.takeIf { it > 0 }
        ?: count.takeIf { it > 0 }
        ?: mappedReviews.size
    val avg = summary?.avgRating ?: avgRating
        ?: mappedReviews.takeIf { it.isNotEmpty() }?.map { it.rating }?.average()
    return ReviewsPage(
        reviews = mappedReviews,
        summary = ReviewsSummary(avgRating = avg, totalReviews = total),
        pagination = pagination?.let {
            ReviewsPagination(it.page, it.limit, it.total, it.pages)
        }
    )
}

@Serializable
data class ActiveOrderCache(val orderJson: String, val uiStatus: String)
