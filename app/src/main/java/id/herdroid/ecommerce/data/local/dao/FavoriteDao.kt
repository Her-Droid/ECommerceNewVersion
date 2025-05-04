package id.herdroid.ecommerce.data.local.dao

import androidx.room.*
import id.herdroid.ecommerce.data.local.entity.FavoriteProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoriteProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(product: FavoriteProduct)

    @Delete
    suspend fun deleteFavorite(product: FavoriteProduct)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :productId)")
    suspend fun isFavorite(productId: Int): Boolean
}
