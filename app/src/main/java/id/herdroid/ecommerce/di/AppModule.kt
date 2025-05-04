package id.herdroid.ecommerce.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.herdroid.ecommerce.data.local.dao.CartDao
import id.herdroid.ecommerce.data.local.dao.OrderDao
import id.herdroid.ecommerce.data.remote.api.AuthApi
import id.herdroid.ecommerce.data.remote.api.ProductApi
import id.herdroid.ecommerce.data.repository.AuthRepositoryImpl
import id.herdroid.ecommerce.data.repository.CartRepositoryImpl
import id.herdroid.ecommerce.data.repository.OrderRepositoryImpl
import id.herdroid.ecommerce.data.repository.ProductRepositoryImpl
import id.herdroid.ecommerce.domain.repository.AuthRepository
import id.herdroid.ecommerce.domain.repository.CartRepository
import id.herdroid.ecommerce.domain.repository.OrderRepository
import id.herdroid.ecommerce.domain.repository.ProductRepository
import id.herdroid.ecommerce.domain.usecase.LoginUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
            level = HttpLoggingInterceptor.Level.HEADERS
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi): AuthRepository {
        return AuthRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProductRepository(api: ProductApi, cartDao: CartDao): ProductRepository {
        return ProductRepositoryImpl(api, cartDao)
    }


    @Provides
    @Singleton
    fun provideCartRepository(cartDao: CartDao): CartRepository {
        return CartRepositoryImpl(cartDao)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(orderDao: OrderDao): OrderRepository {
        return OrderRepositoryImpl(orderDao)
    }

}
