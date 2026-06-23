package uz.yuk24.driver.presentation.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.yuk24.driver.data.remote.ApiResult
import uz.yuk24.driver.data.repository.ReviewsRepository
import uz.yuk24.driver.domain.model.Review
import uz.yuk24.driver.domain.model.ReviewsSummary
import javax.inject.Inject

data class ReviewsUiState(
    val isLoading: Boolean = true,
    val reviews: List<Review> = emptyList(),
    val summary: ReviewsSummary = ReviewsSummary(avgRating = null, totalReviews = 0),
    val error: String? = null
)

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val reviewsRepository: ReviewsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReviewsUiState())
    val uiState: StateFlow<ReviewsUiState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = reviewsRepository.getReviews(page = 1, limit = 50)) {
                is ApiResult.Success -> {
                    val page = result.data
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            reviews = page.reviews,
                            summary = page.summary
                        )
                    }
                }
                is ApiResult.Error -> _uiState.update {
                    it.copy(isLoading = false, error = result.message)
                }
                is ApiResult.NetworkError -> _uiState.update {
                    it.copy(isLoading = false, error = "network")
                }
            }
        }
    }
}
