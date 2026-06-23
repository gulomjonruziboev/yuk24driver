package uz.yuk24.driver.data.repository

import uz.yuk24.driver.data.remote.ApiResult
import uz.yuk24.driver.data.remote.api.DriverApiService
import uz.yuk24.driver.data.remote.dto.RouteRequest
import uz.yuk24.driver.data.remote.safeApiCall
import uz.yuk24.driver.domain.model.LatLng
import uz.yuk24.driver.domain.model.RouteInfo
import uz.yuk24.driver.util.RouteGeometryParser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RouteRepository @Inject constructor(
    private val api: DriverApiService
) {
    suspend fun getRoute(start: LatLng, end: LatLng): ApiResult<RouteInfo> = when (
        val result = safeApiCall {
            api.getRoute(
                RouteRequest(
                    start = listOf(start.lat, start.lng),
                    end = listOf(end.lat, end.lng)
                )
            )
        }
    ) {
        is ApiResult.Success -> {
            val response = result.data
            ApiResult.Success(
                RouteInfo(
                    distanceKm = response.distanceKm,
                    durationMin = response.durationMin,
                    points = RouteGeometryParser.parse(response.geometry)
                )
            )
        }
        is ApiResult.Error -> result
        is ApiResult.NetworkError -> result
    }
}
