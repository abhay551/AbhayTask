package com.abhay.abhaytask.domain.usecase

import androidx.compose.ui.graphics.Color
import com.abhay.abhaytask.domain.model.Holding
import com.abhay.abhaytask.presentation.holdings.HoldingUiModel

class MapHoldingToUiModelUseCase {
    operator fun invoke(holding: Holding): HoldingUiModel {
        val pnl = (holding.ltp - holding.avgPrice) * holding.quantity
        return HoldingUiModel(
            symbol = holding.symbol,
            netQuantityLabel = "Net Qty: ${holding.quantity}",
            ltpLabel = "LTP: ₹ ${"%.2f".format(holding.ltp)}",
            pnlLabel = "P&L: ₹ ${"%.2f".format(pnl)}",
            pnlColor = if (pnl >= 0) Color(0xFF2E7D32) else Color(0xFFC62828)
        )
    }
}
