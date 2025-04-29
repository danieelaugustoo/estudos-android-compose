package com.example.voos.di

import android.content.Context
import androidx.test.espresso.core.internal.deps.dagger.Module
import androidx.test.espresso.core.internal.deps.dagger.Provides
import com.example.voos.data.AirportDao
import com.example.voos.data.FavoriteDao
import com.example.voos.data.FlightSearchDatabase
import com.example.voos.data.UserDataRepository
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    annotation class InstallIn(val value: Any)

    @Singleton
    @Provides
    fun provideFlightSearchDatabase(@ApplicationContext context: Context): FlightSearchDatabase {
        return FlightSearchDatabase.getDatabase(context)
    }

    @Provides
    fun provideAirportDao(database: FlightSearchDatabase): AirportDao {
        return database.airportDao()
    }

    @Provides
    fun provideFavoriteDao(database: FlightSearchDatabase): FavoriteDao {
        return database.favoriteDao()
    }

    @Singleton
    @Provides
    fun provideUserDataRepository(@ApplicationContext context: Context): UserDataRepository {
        return UserDataRepository(context)
    }
}