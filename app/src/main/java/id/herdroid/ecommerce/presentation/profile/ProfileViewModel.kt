package id.herdroid.ecommerce.presentation.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.herdroid.ecommerce.data.local.PreferencesDataStore
import id.herdroid.ecommerce.domain.repository.CartRepository
import id.herdroid.ecommerce.domain.usecase.FavoriteUseCase
import id.herdroid.ecommerce.domain.usecase.OrderUseCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val preferencesDataStore : PreferencesDataStore,
    private val cartRepository: CartRepository,
    private val favoriteUseCase: FavoriteUseCase,
    private val orderUseCase: OrderUseCase
) : ViewModel() {


    private val _cartCount = MutableLiveData<Int>()
    val cartCount: LiveData<Int> get() = _cartCount

    private val _favoriteCount = MutableLiveData<Int>()
    val favoriteCount: LiveData<Int> get() = _favoriteCount

    private val _orderCount = MutableLiveData<Int>()
    val orderCount: LiveData<Int> get() = _orderCount

    val profileImage: LiveData<String?> = preferencesDataStore.profileImage.asLiveData()


    init {
        getCartCount()
        getFavoriteCount()
        getOrderCount()
    }

    private fun getCartCount() {
        viewModelScope.launch {
            cartRepository.getCartProducts().collect { cartList ->
                _cartCount.postValue(cartList.size)
            }
        }
    }

    private fun getFavoriteCount() {
        viewModelScope.launch {
            favoriteUseCase.getAllFavorites().collect { favoriteList ->
                _favoriteCount.postValue(favoriteList.size)
            }
        }
    }

    private fun getOrderCount() {
        viewModelScope.launch {
            orderUseCase.getAllOrders().collect { orderList ->
                _orderCount.postValue(orderList.size)
            }
        }
    }

    val username: LiveData<String> = preferencesDataStore.username
        .map { it ?: "" }
        .asLiveData()


    fun saveProfileImage(uri: Uri) {
        val uriString = uri.toString()
        viewModelScope.launch {
            preferencesDataStore.saveProfileImage(uriString)
        }
    }

    fun clearSession() {
        viewModelScope.launch {
            preferencesDataStore.clearToken()
            preferencesDataStore.clearUsername()
        }
    }

}
