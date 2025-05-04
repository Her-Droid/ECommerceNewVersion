package id.herdroid.ecommerce.domain.usecase

import id.herdroid.ecommerce.data.local.entity.OrderDetailsEntity
import id.herdroid.ecommerce.data.local.entity.OrderEntity
import id.herdroid.ecommerce.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {

    suspend fun insertOrder(order: OrderEntity) {
        repository.insertOrder(order)
    }

    fun getAllOrders(): Flow<List<OrderEntity>> {
        return repository.getAllOrders()
    }

    suspend fun getOrderDetail(orderId: String): OrderDetailsEntity? {
        return repository.getOrderDetail(orderId)
    }

    suspend fun insertOrderDetails(details: OrderDetailsEntity) {
        repository.insertOrderDetails(details)
    }


}
