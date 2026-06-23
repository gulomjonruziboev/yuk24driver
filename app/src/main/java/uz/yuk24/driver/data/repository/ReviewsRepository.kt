package uz.yuk24.driver.data.repository

import uz.yuk24.driver.data.remote.ApiResult
import uz.yuk24.driver.data.remote.api.DriverApiService
import uz.yuk24.driver.data.remote.dto.toDomain
import uz.yuk24.driver.data.remote.safeApiCall
import uz.yuk24.driver.domain.model.ReviewsPage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewsRepository @Inject constructor(
    private val api: DriverApiService
) {
    suspend fun getReviews(page: Int = 1, limit: Int = 50): ApiResult<ReviewsPage> = when (
        val result = safeApiCall { api.getMyReviews(page = page, limit = limit) }
    ) {
        is ApiResult.Success -> ApiResult.Success(result.data.toDomain())
        is ApiResult.Error -> result
        is ApiResult.NetworkError -> result
    }
}
