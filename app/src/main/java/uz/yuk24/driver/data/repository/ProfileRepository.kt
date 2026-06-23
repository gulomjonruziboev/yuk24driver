package uz.yuk24.driver.data.repository

import uz.yuk24.driver.data.remote.ApiResult
import uz.yuk24.driver.data.remote.api.DriverApiService
import uz.yuk24.driver.data.remote.dto.toDomain
import uz.yuk24.driver.data.remote.safeApiCall
import uz.yuk24.driver.domain.model.DriverProfile
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val api: DriverApiService
) {
    suspend fun getProfile(): ApiResult<DriverProfile> = when (
        val result = safeApiCall { api.getMe() }
    ) {
        is ApiResult.Success -> ApiResult.Success(result.data.toDomain())
        is ApiResult.Error -> result
        is ApiResult.NetworkError -> result
    }
}
