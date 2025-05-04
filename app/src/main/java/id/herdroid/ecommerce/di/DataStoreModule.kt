package id.herdroid.ecommerce.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.herdroid.ecommerce.data.local.PreferencesDataStore

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    fun providePreferencesDataStore(
        @ApplicationContext context: Context
    ): PreferencesDataStore {
        return PreferencesDataStore(context)
    }
}

