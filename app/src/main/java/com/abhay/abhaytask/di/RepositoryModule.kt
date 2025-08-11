package com.abhay.abhaytask.di

import com.abhay.abhaytask.data.repository.PortfolioRepositoryImpl
import com.abhay.abhaytask.domain.repository.PortfolioRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides @Singleton
    fun providePortfolioRepository(impl: PortfolioRepositoryImpl): PortfolioRepository = impl
}