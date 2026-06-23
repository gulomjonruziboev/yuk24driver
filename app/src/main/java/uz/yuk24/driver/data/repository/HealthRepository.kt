package uz.yuk24.driver.data.repository

import uz.yuk24.driver.data.remote.ApiResult
import uz.yuk24.driver.data.remote.api.DriverApiService
import uz.yuk24.driver.data.remote.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthRepository @Inject constructor(
    private val api: DriverApiService
) {
    suspend fun checkHealth(): ApiResult<Boolean> = when (val result = safeApiCall { api.health() }) {
        is ApiResult.Success -> ApiResult.Success(result.data.ok == true)
        is ApiResult.Error -> result
        is ApiResult.NetworkError -> result
    }
}
