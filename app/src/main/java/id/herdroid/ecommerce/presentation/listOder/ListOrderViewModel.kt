package id.herdroid.ecommerce.presentation.listOder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.herdroid.ecommerce.data.local.entity.OrderDetailsEntity
import id.herdroid.ecommerce.data.local.entity.OrderEntity
import id.herdroid.ecommerce.domain.usecase.OrderUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOrderViewModel @Inject constructor(
    private val orderUseCase: OrderUseCase
) : ViewModel() {

    private val _orders = MutableLiveData<List<OrderEntity>>()
    val orders: LiveData<List<OrderEntity>> = _orders
    private val _selectedOrder = MutableLiveData<OrderDetailsEntity?>()
    val selectedOrder: LiveData<OrderDetailsEntity?> = _selectedOrder

    fun fetchAllOrders() {
        viewModelScope.launch {
            orderUseCase.getAllOrders().collectLatest { orderList ->
                _orders.value = orderList
            }
        }
    }



}
