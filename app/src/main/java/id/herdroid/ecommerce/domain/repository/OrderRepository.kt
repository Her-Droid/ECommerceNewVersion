package id.herdroid.ecommerce.domain.repository

import id.herdroid.ecommerce.data.local.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun insertOrder(order: OrderEntity)
    fun getAllOrders(): Flow<List<OrderEntity>>
    suspend fun getOrderDetail(orderId: String): OrderEntity?
}


