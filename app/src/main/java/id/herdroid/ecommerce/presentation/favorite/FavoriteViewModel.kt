package id.herdroid.ecommerce.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.herdroid.ecommerce.data.local.entity.FavoriteProduct
import id.herdroid.ecommerce.domain.usecase.FavoriteUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteUseCase: FavoriteUseCase
) : ViewModel() {

    val favorites = favoriteUseCase.getAllFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun removeFromFavorite(favoriteProduct: FavoriteProduct) {
        viewModelScope.launch {
            favoriteUseCase.removeFromFavorite(favoriteProduct)
        }
    }
}
