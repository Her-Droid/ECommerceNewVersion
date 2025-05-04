package id.herdroid.ecommerce.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.herdroid.ecommerce.data.local.PreferencesDataStore
import id.herdroid.ecommerce.data.local.entity.CartProductEntity
import id.herdroid.ecommerce.data.local.entity.FavoriteProduct
import id.herdroid.ecommerce.data.remote.api.ProductApi
import id.herdroid.ecommerce.domain.model.Product
import id.herdroid.ecommerce.domain.repository.ProductRepository
import id.herdroid.ecommerce.domain.usecase.FavoriteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.absoluteValue

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val productApi: ProductApi,
    private val favoriteUseCase: FavoriteUseCase,
    private val productRepository: ProductRepository,
    private val preferencesDataStore: PreferencesDataStore
) : ViewModel() {

    fun addToCart(productId: Int) {
        viewModelScope.launch {
            try {
                val product = productApi.getProductById(productId)
                val cartProductEntity = CartProductEntity(
                    id = product.id,
                    title = product.title,
                    price = product.price,
                    image = product.image,
                    quantity = 1
                )
                productRepository.addProductToCart(cartProductEntity)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "addToCart error: ${e.message}")
            }
        }
    }


    fun addToFavorites(product: Product) {
        viewModelScope.launch {
            favoriteUseCase.addToFavorite(FavoriteProduct.fromProduct(product))
        }
    }

    fun removeFromFavorites(productId: Int) {
        viewModelScope.launch {
            favoriteUseCase.removeFromFavorite(FavoriteProduct(id = productId, title = "", price = 0.0, description = "", category = "", image = ""))
        }
    }

    fun isFavorite(productId: Int): Flow<Boolean> {
        return flow {
            emit(favoriteUseCase.isFavorite(productId))
        }
    }


}
