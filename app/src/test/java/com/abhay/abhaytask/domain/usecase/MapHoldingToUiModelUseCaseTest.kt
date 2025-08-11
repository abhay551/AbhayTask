package com.abhay.abhaytask.domain.usecase

import androidx.compose.ui.graphics.Color
import com.abhay.abhaytask.domain.model.Holding
import org.junit.Assert.assertEquals
import org.junit.Test

class MapHoldingToUiModelUseCaseTest {

    private val useCase = MapHoldingToUiModelUseCase()

    @Test
    fun `should map holding to UI model with positive PnL`() {
        val holding = Holding(
            symbol = "TCS",
            quantity = 10,
            avgPrice = 2000.0,
            ltp = 2100.0,
            close = 2000.0
        )

        val result = useCase(holding)

        assertEquals("TCS", result.symbol)
        assertEquals("Net Qty: 10", result.netQuantityLabel)
        assertEquals("LTP: ₹ 2100.00", result.ltpLabel)
        assertEquals("P&L: ₹ 1000.00", result.pnlLabel)
        assertEquals(Color(0xFF2E7D32), result.pnlColor)
    }

    @Test
    fun `should map holding to UI model with negative PnL`() {
        val holding = Holding(
            symbol = "INFY",
            quantity = 5,
            avgPrice = 1500.0,
            ltp = 1400.0,
            close = 1500.0
        )

        val result = useCase(holding)

        assertEquals("INFY", result.symbol)
        assertEquals("Net Qty: 5", result.netQuantityLabel)
        assertEquals("LTP: ₹ 1400.00", result.ltpLabel)
        assertEquals("P&L: ₹ -500.00", result.pnlLabel)
        assertEquals(Color(0xFFC62828), result.pnlColor)
    }
}
