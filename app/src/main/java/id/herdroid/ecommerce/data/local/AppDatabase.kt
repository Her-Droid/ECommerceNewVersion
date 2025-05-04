package id.herdroid.ecommerce.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.herdroid.ecommerce.data.local.dao.CartDao
import id.herdroid.ecommerce.data.local.dao.FavoriteDao
import id.herdroid.ecommerce.data.local.dao.OrderDao
import id.herdroid.ecommerce.data.local.entity.CartProductEntity
import id.herdroid.ecommerce.data.local.entity.FavoriteProduct
import id.herdroid.ecommerce.data.local.entity.OrderDetailsEntity
import id.herdroid.ecommerce.data.local.entity.OrderEntity

@Database(
    entities = [
        FavoriteProduct::class,
        CartProductEntity::class,
        OrderEntity::class,
        OrderDetailsEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
}
