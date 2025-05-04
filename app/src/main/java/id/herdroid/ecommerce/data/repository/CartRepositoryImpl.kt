package id.herdroid.ecommerce.data.repository

import id.herdroid.ecommerce.data.local.dao.CartDao
import id.herdroid.ecommerce.data.local.entity.CartProductEntity
import id.herdroid.ecommerce.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override fun getCartProducts(): Flow<List<CartProductEntity>> {
        return cartDao.getCartProducts()
    }

    override suspend fun deleteProduct(productId: Int) {
        cartDao.deleteProductById(productId)
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }

    override suspend fun updateQuantity(product: CartProductEntity) {
        cartDao.updateProduct(product)
    }
}

