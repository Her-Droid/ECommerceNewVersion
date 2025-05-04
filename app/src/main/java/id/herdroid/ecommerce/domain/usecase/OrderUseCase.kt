package id.herdroid.ecommerce.domain.usecase

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

    suspend fun getOrderDetail(orderId: String): OrderEntity? {
        return repository.getOrderDetail(orderId)
    }
}
