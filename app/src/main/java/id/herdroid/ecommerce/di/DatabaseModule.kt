package id.herdroid.ecommerce.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.herdroid.ecommerce.data.local.AppDatabase
import id.herdroid.ecommerce.data.local.dao.FavoriteDao
import id.herdroid.ecommerce.domain.usecase.FavoriteUseCase
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext
import id.herdroid.ecommerce.data.local.dao.CartDao
import id.herdroid.ecommerce.data.local.dao.OrderDao
import id.herdroid.ecommerce.domain.repository.OrderRepository
import id.herdroid.ecommerce.domain.usecase.OrderUseCase

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideFavoriteDao(database: AppDatabase): FavoriteDao {
        return database.favoriteDao()
    }

    @Provides
    fun provideCartDao(database: AppDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    fun provideOrderDao(database: AppDatabase): OrderDao {
        return database.orderDao()
    }

    @Provides
    fun provideFavoriteUseCase(favoriteDao: FavoriteDao): FavoriteUseCase {
        return FavoriteUseCase(favoriteDao)
    }

    @Provides
    fun provideOrderUseCase(orderRepository: OrderRepository): OrderUseCase {
        return OrderUseCase(orderRepository)
    }
}
