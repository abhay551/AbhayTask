package com.abhay.abhaytask.domain.usecase

import com.abhay.abhaytask.domain.model.Holding
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculatePortfolioUseCaseTest {
    private val calculatePortfolioUseCaseTest = CalculatePortfolioUseCase()

    @Test
    fun `calculations are correct`() {
        val list = listOf(
            Holding("A", 10, 10.0, 8.0, 11.0),
            Holding("B", 5, 20.0, 25.0, 19.0)
        )
        val res = calculatePortfolioUseCaseTest(list)
        assertEquals(200.0, res.currentValue, 0.001)
        assertEquals(205.0, res.totalInvestment, 0.001)
        assertEquals(-5.0, res.totalPnl, 0.001)
        assertEquals(5.0, res.todaysPnl, 0.001)
    }
}