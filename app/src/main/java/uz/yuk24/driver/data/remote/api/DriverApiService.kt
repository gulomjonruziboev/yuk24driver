package uz.yuk24.driver.data.remote.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import uz.yuk24.driver.data.remote.dto.CancelOrderRequest
import uz.yuk24.driver.data.remote.dto.DriverProfileDto
import uz.yuk24.driver.data.remote.dto.DriverReviewsResponseDto
import uz.yuk24.driver.data.remote.dto.HealthResponse
import uz.yuk24.driver.data.remote.dto.LocationUpdateRequest
import uz.yuk24.driver.data.remote.dto.LocationUpdateResponse
import uz.yuk24.driver.data.remote.dto.LoginRequest
import uz.yuk24.driver.data.remote.dto.LoginResponse
import uz.yuk24.driver.data.remote.dto.OrderDto
import uz.yuk24.driver.data.remote.dto.RouteRequest
import uz.yuk24.driver.data.remote.dto.RouteResponse

interface DriverApiService {

    @GET("api/health")
    suspend fun health(): HealthResponse

    @POST("api/auth/driver/login")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    @GET("api/driver/orders/available")
    suspend fun getAvailableOrders(): List<OrderDto>

    @POST("api/driver/orders/{id}/accept")
    suspend fun acceptOrder(@Path("id") id: String): OrderDto

    @POST("api/driver/orders/{id}/picked-up")
    suspend fun pickedUp(@Path("id") id: String): OrderDto

    @POST("api/driver/orders/{id}/delivered")
    suspend fun delivered(@Path("id") id: String): OrderDto

    @POST("api/driver/orders/{id}/cancel")
    suspend fun cancelOrder(
        @Path("id") id: String,
        @Body body: CancelOrderRequest
    ): OrderDto

    @GET("api/driver/me")
    suspend fun getMe(): DriverProfileDto

    @GET("api/driver/reviews")
    suspend fun getMyReviews(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): DriverReviewsResponseDto

    @PATCH("api/driver/location")
    suspend fun updateLocation(@Body body: LocationUpdateRequest): LocationUpdateResponse

    @POST("api/route")
    suspend fun getRoute(@Body body: RouteRequest): RouteResponse
}
