package uz.yuk24.driver.presentation.dashboard

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import uz.yuk24.driver.data.local.ActiveOrderStore
import uz.yuk24.driver.data.local.SessionManager
import uz.yuk24.driver.data.remote.ApiResult
import uz.yuk24.driver.data.repository.OrderRepository
import uz.yuk24.driver.data.repository.ProfileRepository
import uz.yuk24.driver.data.repository.ReviewsRepository
import uz.yuk24.driver.data.repository.RouteRepository
import uz.yuk24.driver.domain.model.DriverProfile
import uz.yuk24.driver.domain.model.ReviewsSummary
import uz.yuk24.driver.domain.model.DriverUiStatus
import uz.yuk24.driver.domain.model.LatLng
import uz.yuk24.driver.domain.model.Order
import uz.yuk24.driver.domain.model.RouteInfo
import uz.yuk24.driver.service.LocationServiceController
import javax.inject.Inject

data class DashboardUiState(
    val driverPos: LatLng? = null,
    val geoLoading: Boolean = true,
    val order: Order? = null,
    val uiStatus: DriverUiStatus = DriverUiStatus.IDLE,
    val routeInfo: RouteInfo? = null,
    val profile: DriverProfile? = null,
    val reviewSummary: ReviewsSummary? = null,
    val isOffline: Boolean = false,
    val toastMessage: String? = null,
    val showOrderDetails: Boolean = false,
    val showCancelDialog: Boolean = false,
    val actionLoading: Boolean = false
) {
    val avgRating: Double?
        get() = reviewSummary?.avgRating
            ?: profile?.reviews?.takeIf { it.isNotEmpty() }?.map { it.rating }?.average()

    val reviewCount: Int
        get() = reviewSummary?.totalReviews?.takeIf { it > 0 }
            ?: profile?.reviews?.size
            ?: 0
}

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val routeRepository: RouteRepository,
    private val profileRepository: ProfileRepository,
    private val reviewsRepository: ReviewsRepository,
    private val activeOrderStore: ActiveOrderStore,
    private val sessionManager: SessionManager,
    private val locationServiceController: LocationServiceController,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private var orderPollJob: Job? = null
    private var profilePollJob: Job? = null

    init {
        viewModelScope.launch {
            restoreActiveOrder()
            refreshProfile()
            startProfilePolling()
            startOrderPolling()
        }
    }

    fun startLocationService() {
        locationServiceController.start(context)
    }

    fun updateDriverPosition(lat: Double, lng: Double) {
        val wasAccepted = _uiState.value.uiStatus == DriverUiStatus.ACCEPTED
        _uiState.update {
            it.copy(
                driverPos = LatLng(lat, lng),
                geoLoading = false
            )
        }
        if (wasAccepted && _uiState.value.order != null) {
            rebuildRoute()
        }
    }

    fun setGeoLoading(loading: Boolean) {
        _uiState.update { it.copy(geoLoading = loading) }
    }

    fun showOrderDetails() = _uiState.update { it.copy(showOrderDetails = true) }
    fun hideOrderDetails() = _uiState.update { it.copy(showOrderDetails = false) }
    fun showCancelDialog() = _uiState.update { it.copy(showCancelDialog = true) }
    fun hideCancelDialog() = _uiState.update { it.copy(showCancelDialog = false) }
    fun clearToast() = _uiState.update { it.copy(toastMessage = null) }

    fun logout() {
        viewModelScope.launch {
            activeOrderStore.clear()
            locationServiceController.stop(context)
            sessionManager.logout()
        }
    }

    fun acceptOrder() {
        val order = _uiState.value.order ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(actionLoading = true) }
            when (val result = orderRepository.acceptOrder(order.id)) {
                is ApiResult.Success -> {
                    val updated = result.data
                    _uiState.update {
                        it.copy(
                            order = updated,
                            uiStatus = DriverUiStatus.ACCEPTED,
                            actionLoading = false
                        )
                    }
                    activeOrderStore.save(updated, DriverUiStatus.ACCEPTED)
                    rebuildRoute()
                }
                is ApiResult.Error -> handleActionError(result.code, result.message)
                is ApiResult.NetworkError -> showToast("network")
            }
        }
    }

    fun pickedUp() {
        val order = _uiState.value.order ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(actionLoading = true) }
            when (val result = orderRepository.pickedUp(order.id)) {
                is ApiResult.Success -> {
                    val updated = result.data
                    _uiState.update {
                        it.copy(
                            order = updated,
                            uiStatus = DriverUiStatus.PICKED_UP,
                            actionLoading = false
                        )
                    }
                    activeOrderStore.save(updated, DriverUiStatus.PICKED_UP)
                    rebuildRoute()
                }
                is ApiResult.Error -> handleActionError(result.code, result.message)
                is ApiResult.NetworkError -> showToast("network")
            }
        }
    }

    fun deliverOrder() {
        val order = _uiState.value.order ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(actionLoading = true) }
            when (val result = orderRepository.delivered(order.id)) {
                is ApiResult.Success -> resetToIdle()
                is ApiResult.Error -> handleActionError(result.code, result.message)
                is ApiResult.NetworkError -> showToast("network")
            }
        }
    }

    fun cancelOrder(reason: String) {
        val order = _uiState.value.order ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(actionLoading = true) }
            when (val result = orderRepository.cancelOrder(order.id, reason)) {
                is ApiResult.Success -> {
                    resetToIdle()
                    _uiState.update { it.copy(showCancelDialog = false) }
                }
                is ApiResult.Error -> handleActionError(result.code, result.message)
                is ApiResult.NetworkError -> showToast("network")
            }
        }
    }

    private suspend fun resetToIdle() {
        activeOrderStore.clear()
        _uiState.update {
            it.copy(
                order = null,
                uiStatus = DriverUiStatus.IDLE,
                routeInfo = null,
                actionLoading = false,
                showOrderDetails = false,
                showCancelDialog = false
            )
        }
        startOrderPolling()
    }

    private suspend fun restoreActiveOrder() {
        val cached = activeOrderStore.load() ?: return
        val (order, uiStatus) = cached
        if (uiStatus == DriverUiStatus.IDLE) return
        _uiState.update {
            it.copy(order = order, uiStatus = uiStatus)
        }
        orderPollJob?.cancel()
        rebuildRoute()
    }

    private fun startOrderPolling() {
        orderPollJob?.cancel()
        orderPollJob = viewModelScope.launch {
            while (isActive) {
                val status = _uiState.value.uiStatus
                if (status == DriverUiStatus.IDLE && _uiState.value.driverPos != null) {
                    pollAvailableOrders()
                }
                delay(10_000)
            }
        }
    }

    private suspend fun pollAvailableOrders() {
        when (val result = orderRepository.getAvailableOrders()) {
            is ApiResult.Success -> {
                _uiState.update { it.copy(isOffline = false) }
                val first = result.data.firstOrNull()
                if (first != null && _uiState.value.order == null) {
                    _uiState.update {
                        it.copy(order = first, uiStatus = DriverUiStatus.NEW)
                    }
                    activeOrderStore.save(first, DriverUiStatus.NEW)
                    orderPollJob?.cancel()
                    rebuildRoute()
                }
            }
            is ApiResult.NetworkError -> _uiState.update { it.copy(isOffline = true) }
            is ApiResult.Error -> _uiState.update { it.copy(isOffline = true) }
        }
    }

    private fun startProfilePolling() {
        profilePollJob?.cancel()
        profilePollJob = viewModelScope.launch {
            while (isActive) {
                refreshProfile()
                delay(30_000)
            }
        }
    }

    private suspend fun refreshProfile() {
        when (val result = profileRepository.getProfile()) {
            is ApiResult.Success -> _uiState.update { it.copy(profile = result.data, isOffline = false) }
            is ApiResult.NetworkError -> _uiState.update { it.copy(isOffline = true) }
            is ApiResult.Error -> Unit
        }
        when (val reviews = reviewsRepository.getReviews(limit = 1)) {
            is ApiResult.Success -> _uiState.update { it.copy(reviewSummary = reviews.data.summary) }
            else -> Unit
        }
    }

    private fun rebuildRoute() {
        val state = _uiState.value
        val order = state.order ?: return
        val driver = state.driverPos
        viewModelScope.launch {
            val (start, end) = when (state.uiStatus) {
                DriverUiStatus.NEW -> order.pickup.toLatLng() to order.delivery.toLatLng()
                DriverUiStatus.ACCEPTED -> {
                    val d = driver ?: return@launch
                    d to order.pickup.toLatLng()
                }
                DriverUiStatus.PICKED_UP -> order.pickup.toLatLng() to order.delivery.toLatLng()
                DriverUiStatus.IDLE -> return@launch
            }
            when (val result = routeRepository.getRoute(start, end)) {
                is ApiResult.Success -> _uiState.update { it.copy(routeInfo = result.data) }
                else -> Unit
            }
        }
    }

    private fun handleActionError(code: Int, message: String) {
        _uiState.update { it.copy(actionLoading = false) }
        when (code) {
            400, 404 -> {
                showToast("order_unavailable")
                viewModelScope.launch { resetToIdle() }
            }
            403 -> showToast("forbidden")
            else -> showToast(message.ifBlank { "network" })
        }
    }

    private fun showToast(key: String) {
        _uiState.update { it.copy(toastMessage = key) }
    }

    override fun onCleared() {
        orderPollJob?.cancel()
        profilePollJob?.cancel()
        super.onCleared()
    }
}

private fun uz.yuk24.driver.domain.model.LocationPoint.toLatLng() = LatLng(lat, lng)
