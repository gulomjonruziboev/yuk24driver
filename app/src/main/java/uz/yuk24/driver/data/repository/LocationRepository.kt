package uz.yuk24.driver.data.repository

import uz.yuk24.driver.data.remote.ApiResult
import uz.yuk24.driver.data.remote.api.DriverApiService
import uz.yuk24.driver.data.remote.dto.LocationUpdateRequest
import uz.yuk24.driver.data.remote.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
    private val api: DriverApiService
) {
    suspend fun updateLocation(lat: Double, lng: Double): ApiResult<Unit> = when (
        val result = safeApiCall { api.updateLocation(LocationUpdateRequest(lat, lng)) }
    ) {
        is ApiResult.Success -> ApiResult.Success(Unit)
        is ApiResult.Error -> result
        is ApiResult.NetworkError -> result
    }
}
