package id.herdroid.ecommerce.domain.repository

import id.herdroid.ecommerce.data.local.entity.CartProductEntity
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartProducts(): Flow<List<CartProductEntity>>
    suspend fun deleteProduct(productId: Int)
    suspend fun clearCart()
    suspend fun updateQuantity(product: CartProductEntity)
}

