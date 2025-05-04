package id.herdroid.ecommerce.data.remote.api

import id.herdroid.ecommerce.data.remote.dto.ProductDto
import id.herdroid.ecommerce.domain.model.Product
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApi {
    @GET("products")
    suspend fun getAllProducts(): List<ProductDto>

    @GET("products/categories")
    suspend fun getCategories(): List<String>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): List<ProductDto>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") productId: Int): Product

}
