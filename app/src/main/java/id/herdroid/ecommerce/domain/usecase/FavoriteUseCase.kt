package id.herdroid.ecommerce.domain.usecase

import id.herdroid.ecommerce.data.local.dao.FavoriteDao
import id.herdroid.ecommerce.data.local.entity.FavoriteProduct
import kotlinx.coroutines.flow.Flow

class FavoriteUseCase(private val favoriteDao: FavoriteDao) {

    fun getAllFavorites(): Flow<List<FavoriteProduct>> {
        return favoriteDao.getAllFavorites()
    }


    suspend fun addToFavorite(product: FavoriteProduct) {
        favoriteDao.insertFavorite(product)
    }

    suspend fun removeFromFavorite(product: FavoriteProduct) {
        favoriteDao.deleteFavorite(product)
    }

    suspend fun isFavorite(productId: Int): Boolean {
        return favoriteDao.isFavorite(productId)
    }
}