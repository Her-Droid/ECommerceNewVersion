package id.herdroid.ecommerce.presentation.checkoutSummary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.herdroid.ecommerce.data.local.entity.OrderDetailsEntity
import id.herdroid.ecommerce.data.local.entity.OrderEntity
import id.herdroid.ecommerce.domain.repository.CartRepository
import id.herdroid.ecommerce.domain.usecase.OrderUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutSummaryViewModel @Inject constructor(
    private val orderUseCase: OrderUseCase,
    private val cartRepository: CartRepository
) : ViewModel() {

    fun insertOrder(order: OrderEntity, orderDetails: OrderDetailsEntity) {
        viewModelScope.launch {
            orderUseCase.insertOrder(order)
            orderUseCase.insertOrderDetails(orderDetails)
            cartRepository.clearCart()
        }
    }

}
