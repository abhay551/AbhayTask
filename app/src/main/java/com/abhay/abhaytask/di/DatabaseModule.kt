package com.abhay.abhaytask.di

import android.content.Context
import androidx.room.Room
import com.abhay.abhaytask.data.local.AppDatabase
import com.abhay.abhaytask.data.local.HoldingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, "portfolio-db").build()

    @Provides
    @Singleton
    fun provideHoldingDao(db: AppDatabase): HoldingDao = db.holdingDao()
}