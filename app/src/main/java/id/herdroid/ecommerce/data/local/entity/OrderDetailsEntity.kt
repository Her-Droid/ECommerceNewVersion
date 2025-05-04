package id.herdroid.ecommerce.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_details")
data class OrderDetailsEntity(
    @PrimaryKey val orderId: String,
    val orderDate: Long,
    val totalPrice: Double,
    val productSubtotal: Double,
    val deliveryFee: Double,
    val serviceFee: Double,
    val address: String,
    val paymentMethod: String,
    val deliveryMethod: String,
    val productItems: String
)
