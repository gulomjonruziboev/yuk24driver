package uz.yuk24.driver.data.repository

import uz.yuk24.driver.data.remote.ApiResult
import uz.yuk24.driver.data.remote.api.DriverApiService
import uz.yuk24.driver.data.remote.dto.CancelOrderRequest
import uz.yuk24.driver.data.remote.dto.toDomain
import uz.yuk24.driver.data.remote.safeApiCall
import uz.yuk24.driver.domain.model.Order
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(
    private val api: DriverApiService
) {
    suspend fun getAvailableOrders(): ApiResult<List<Order>> = when (
        val result = safeApiCall { api.getAvailableOrders() }
    ) {
        is ApiResult.Success -> ApiResult.Success(result.data.map { it.toDomain() })
        is ApiResult.Error -> result
        is ApiResult.NetworkError -> result
    }

    suspend fun acceptOrder(id: String): ApiResult<Order> = mapOrder { api.acceptOrder(id) }

    suspend fun pickedUp(id: String): ApiResult<Order> = mapOrder { api.pickedUp(id) }

    suspend fun delivered(id: String): ApiResult<Order> = mapOrder { api.delivered(id) }

    suspend fun cancelOrder(id: String, reason: String): ApiResult<Order> =
        mapOrder { api.cancelOrder(id, CancelOrderRequest(reason)) }

    private suspend fun mapOrder(call: suspend () -> uz.yuk24.driver.data.remote.dto.OrderDto): ApiResult<Order> =
        when (val result = safeApiCall { call() }) {
            is ApiResult.Success -> ApiResult.Success(result.data.toDomain())
            is ApiResult.Error -> result
            is ApiResult.NetworkError -> result
        }
}
