package id.herdroid.ecommerce.presentation.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.herdroid.ecommerce.data.local.entity.CartProductEntity
import id.herdroid.ecommerce.domain.repository.CartRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {

    val cartProducts = repository.getCartProducts().asLiveData()

    fun deleteProduct(id: Int) = viewModelScope.launch {
        repository.deleteProduct(id)
    }

    fun clearCart() = viewModelScope.launch {
        repository.clearCart()
    }

    fun updateQuantity(product: CartProductEntity) = viewModelScope.launch {
        repository.updateQuantity(product)
    }
}
