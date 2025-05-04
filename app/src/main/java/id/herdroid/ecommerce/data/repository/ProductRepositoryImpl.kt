package id.herdroid.ecommerce.data.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import id.herdroid.ecommerce.data.local.dao.CartDao
import id.herdroid.ecommerce.data.local.entity.CartProductEntity
import id.herdroid.ecommerce.data.paging.CategoryPagingSource
import id.herdroid.ecommerce.data.remote.api.ProductApi
import id.herdroid.ecommerce.data.paging.ProductPagingSource
import id.herdroid.ecommerce.domain.model.Product
import id.herdroid.ecommerce.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val api: ProductApi,
    private val cartDao: CartDao
) : ProductRepository {
    override fun getProducts(): Pager<Int, Product> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { ProductPagingSource(api) }
        )
    }

    override fun getProductsByCategory(category: String): Pager<Int, Product> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { CategoryPagingSource(api, category) }
        )
    }

    override suspend fun addProductToCart(product: CartProductEntity) {
        val existing = cartDao.getProductById(product.id)
        if (existing != null) {
            existing.quantity += 1
            cartDao.updateProduct(existing)
        } else {
            cartDao.insertCartProduct(product)
        }
    }


}
