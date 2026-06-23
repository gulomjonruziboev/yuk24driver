package uz.yuk24.driver.domain.model

enum class OrderStatus(val apiValue: String) {
    QUEUE("queue"),
    PROCESS("process"),
    PICKED_UP("pickedUp"),
    DELIVERED("delivered"),
    CANCELLED("cancelled");

    companion object {
        fun fromApi(value: String?): OrderStatus =
            entries.firstOrNull { it.apiValue == value } ?: QUEUE
    }
}

enum class DriverUiStatus {
    IDLE, NEW, ACCEPTED, PICKED_UP;

    companion object {
        fun fromBackend(status: OrderStatus): DriverUiStatus = when (status) {
            OrderStatus.QUEUE -> NEW
            OrderStatus.PROCESS -> ACCEPTED
            OrderStatus.PICKED_UP -> PICKED_UP
            else -> IDLE
        }
    }
}

data class LatLng(val lat: Double, val lng: Double)

data class LocationPoint(
    val label: String,
    val coords: List<Double>
) {
    val lat: Double get() = coords.getOrNull(0) ?: 0.0
    val lng: Double get() = coords.getOrNull(1) ?: 0.0
}

data class Order(
    val id: String,
    val orderId: String,
    val customerName: String?,
    val customerPhone: String,
    val notes: String?,
    val pickup: LocationPoint,
    val delivery: LocationPoint,
    val loadSize: String,
    val unloading: Boolean,
    val price: Int,
    val distanceKm: Double,
    val durationMin: Int?,
    val status: OrderStatus,
    val cancelReason: String? = null
)

data class DriverStats(
    val completedOrders: Int,
    val cancelledOrders: Int,
    val avgDeliveryMin: Int?
)

data class Review(
    val id: String = "",
    val rating: Int,
    val comment: String?,
    val customerName: String?,
    val orderId: String?,
    val createdAt: String
)

data class ReviewsSummary(
    val avgRating: Double?,
    val totalReviews: Int
)

data class ReviewsPagination(
    val page: Int,
    val limit: Int,
    val total: Int,
    val pages: Int
)

data class ReviewsPage(
    val reviews: List<Review>,
    val summary: ReviewsSummary,
    val pagination: ReviewsPagination?
)

data class DriverProfile(
    val id: String,
    val username: String,
    val name: String?,
    val phone: String?,
    val vehicleInfo: String?,
    val active: Boolean,
    val stats: DriverStats,
    val reviews: List<Review>
)

data class AuthSession(
    val token: String,
    val userId: String,
    val username: String,
    val name: String?,
    val loginAt: Long
)

data class RouteInfo(
    val distanceKm: Double,
    val durationMin: Double,
    val points: List<LatLng>
)
