package com.abhay.abhaytask.di

import com.abhay.abhaytask.domain.usecase.CalculatePortfolioUseCase
import com.abhay.abhaytask.domain.usecase.MapHoldingToUiModelUseCase
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import kotlin.test.Test

class UseCaseModuleTest {
    private val module = UseCaseModule

    @Test
    fun `provideCalculatePortfolioUseCase returns instance`() {
        val useCase = module.provideCalculatePortfolioUseCase()

        assertNotNull("Use case should not be null", useCase)
        assertTrue("Returned instance should be of type CalculatePortfolioUseCase", useCase is CalculatePortfolioUseCase)
    }

    @Test
    fun `provideMapToHoldingUiModelUseCase returns instance`() {
        val useCase = module.providesMapToHoldingUiModelUseCase()

        assertNotNull("Use case should not be null", useCase)
        assertTrue("Returned instance should be of type MapHoldingToUiModelUseCase", useCase is MapHoldingToUiModelUseCase)
    }
}