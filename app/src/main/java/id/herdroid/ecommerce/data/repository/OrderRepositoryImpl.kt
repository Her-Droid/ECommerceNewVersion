package id.herdroid.ecommerce.data.repository

import id.herdroid.ecommerce.data.local.dao.OrderDao
import id.herdroid.ecommerce.data.local.entity.OrderDetailsEntity
import id.herdroid.ecommerce.data.local.entity.OrderEntity
import id.herdroid.ecommerce.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao
) : OrderRepository {
    override suspend fun insertOrder(order: OrderEntity) = orderDao.insertOrder(order)

    override fun getAllOrders() = orderDao.getAllOrders()

    override suspend fun insertOrderDetails(details: OrderDetailsEntity) {
        orderDao.insertOrderDetails(details)
    }

    override suspend fun getOrderDetail(orderId: String): OrderDetailsEntity? {
        return orderDao.getOrderDetailsById(orderId)
    }
}

