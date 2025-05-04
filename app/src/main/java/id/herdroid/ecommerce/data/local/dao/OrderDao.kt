package id.herdroid.ecommerce.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.herdroid.ecommerce.data.local.entity.OrderDetailsEntity
import id.herdroid.ecommerce.data.local.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Query("SELECT * FROM orders ORDER BY orderDate DESC")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders WHERE orderId = :orderId LIMIT 1")
    suspend fun getOrderById(orderId: String): OrderEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderDetails(details: OrderDetailsEntity)

    @Query("SELECT * FROM order_details WHERE orderId = :orderId LIMIT 1")
    suspend fun getOrderDetailsById(orderId: String): OrderDetailsEntity?
}

