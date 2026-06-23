package uz.yuk24.driver.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import uz.yuk24.driver.data.remote.dto.ActiveOrderCache
import uz.yuk24.driver.data.remote.dto.OrderDto
import uz.yuk24.driver.data.remote.dto.toDomain
import uz.yuk24.driver.domain.model.DriverUiStatus
import uz.yuk24.driver.domain.model.Order
import javax.inject.Inject
import javax.inject.Singleton

private val Context.activeOrderDataStore: DataStore<Preferences> by preferencesDataStore("active_order")

@Singleton
class ActiveOrderStore @Inject constructor(
    @ApplicationContext private val context: Context,
    private val json: Json
) {
    private val cacheKey = stringPreferencesKey("active_order_cache")

    suspend fun save(order: Order, uiStatus: DriverUiStatus) {
        val dto = OrderDto(
            mongoId = order.id,
            orderId = order.orderId,
            customerName = order.customerName,
            customerPhone = order.customerPhone,
            notes = order.notes,
            pickup = uz.yuk24.driver.data.remote.dto.LocationPointDto(order.pickup.label, order.pickup.coords),
            delivery = uz.yuk24.driver.data.remote.dto.LocationPointDto(order.delivery.label, order.delivery.coords),
            loadSize = order.loadSize,
            unloading = order.unloading,
            price = order.price,
            distanceKm = order.distanceKm,
            durationMin = order.durationMin,
            status = order.status.apiValue,
            cancelReason = order.cancelReason
        )
        val cache = ActiveOrderCache(
            orderJson = json.encodeToString(OrderDto.serializer(), dto),
            uiStatus = uiStatus.name
        )
        context.activeOrderDataStore.edit { prefs ->
            prefs[cacheKey] = json.encodeToString(ActiveOrderCache.serializer(), cache)
        }
    }

    suspend fun load(): Pair<Order, DriverUiStatus>? {
        val raw = context.activeOrderDataStore.data.map { it[cacheKey] }.first() ?: return null
        return runCatching<Pair<Order, DriverUiStatus>> {
            val cache = json.decodeFromString(ActiveOrderCache.serializer(), raw)
            val order = json.decodeFromString(OrderDto.serializer(), cache.orderJson).toDomain()
            val uiStatus = DriverUiStatus.valueOf(cache.uiStatus)
            Pair(order, uiStatus)
        }.getOrNull()
    }

    suspend fun clear() {
        context.activeOrderDataStore.edit { it.remove(cacheKey) }
    }
}
