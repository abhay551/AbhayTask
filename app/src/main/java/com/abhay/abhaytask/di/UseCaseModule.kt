package com.abhay.abhaytask.di

import com.abhay.abhaytask.domain.usecase.CalculatePortfolioUseCase
import com.abhay.abhaytask.domain.usecase.MapHoldingToUiModelUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideCalculatePortfolioUseCase(): CalculatePortfolioUseCase =
        CalculatePortfolioUseCase()

    @Provides
    @Singleton
    fun providesMapToHoldingUiModelUseCase(): MapHoldingToUiModelUseCase =
        MapHoldingToUiModelUseCase()
}