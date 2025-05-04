package id.herdroid.ecommerce.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val orderId: String,
    val orderDate: Long,
    val totalPrice: Double,
    val itemCount: Int,
    val address: String,
    val paymentMethod: String,
    val productItems: String
)


