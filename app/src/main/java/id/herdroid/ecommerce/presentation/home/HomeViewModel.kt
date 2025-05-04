package id.herdroid.ecommerce.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.herdroid.ecommerce.data.local.PreferencesDataStore
import id.herdroid.ecommerce.data.local.entity.CartProductEntity
import id.herdroid.ecommerce.data.remote.api.ProductApi
import id.herdroid.ecommerce.domain.model.Product
import id.herdroid.ecommerce.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.absoluteValue

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val productApi: ProductApi,
    private val preferencesDataStore: PreferencesDataStore
) : ViewModel() {

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _categories

    private val selectedCategory = MutableStateFlow<String>("All")

    val profileImage: LiveData<String?> = preferencesDataStore.profileImage.asLiveData()
    val username: LiveData<String?> = preferencesDataStore.username.asLiveData()

    val products: Flow<PagingData<Product>> = selectedCategory
        .flatMapLatest { category ->
            if (category == "All") {
                productRepository.getProducts().flow
            } else {
                productRepository.getProductsByCategory(category).flow
            }
        }.cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            try {
                val result = productApi.getCategories().toMutableList()
                result.add(0, "All")
                _categories.value = result
            } catch (e: Exception) {
                e.printStackTrace()
                _categories.value = listOf("All")
            }
        }
    }

    fun filterByCategory(category: String) {
        selectedCategory.value = category
    }

    fun addToCart(productId: Int, onComplete: () -> Unit) {
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
                onComplete()  // Call the callback after adding to the cart
            } catch (e: Exception) {
                Log.e("HomeViewModel", "addToCart error: ${e.message}")
                onComplete()  // Ensure we call the callback even in case of error
            }
        }
    }




}
