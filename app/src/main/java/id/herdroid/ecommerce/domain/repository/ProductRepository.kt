package id.herdroid.ecommerce.domain.repository

import androidx.paging.Pager
import id.herdroid.ecommerce.data.local.entity.CartProductEntity
import id.herdroid.ecommerce.domain.model.Product

interface ProductRepository {
    fun getProducts(): Pager<Int, Product>

    fun getProductsByCategory(category: String): Pager<Int, Product>

    suspend fun addProductToCart(product: CartProductEntity)
}
