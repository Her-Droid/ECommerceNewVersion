package id.herdroid.ecommerce.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import id.herdroid.ecommerce.data.local.entity.CartProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_products")
    fun getCartProducts(): Flow<List<CartProductEntity>>

    @Query("SELECT * FROM cart_products WHERE id = :productId LIMIT 1")
    suspend fun getProductById(productId: Int): CartProductEntity?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProduct(product: CartProductEntity)

    @Query("DELETE FROM cart_products WHERE id = :productId")
    suspend fun deleteProductById(productId: Int)

    @Query("DELETE FROM cart_products")
    suspend fun clearCart()

    @Update
    suspend fun updateProduct(product: CartProductEntity)
}

