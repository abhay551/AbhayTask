package com.abhay.abhaytask.domain.usecase

import com.abhay.abhaytask.domain.model.Holding
import com.abhay.abhaytask.domain.model.PortfolioSummary

class CalculatePortfolioUseCase {
    operator fun invoke(holdings: List<Holding>): PortfolioSummary {
        var current = 0.0
        var invested = 0.0
        var today = 0.0

        for (h in holdings) {
            current += h.ltp * h.quantity
            invested += h.avgPrice * h.quantity
            today += (h.close - h.ltp) * h.quantity
        }
        val totalPnl = current - invested
        return PortfolioSummary(
            currentValue = current,
            totalInvestment = invested,
            totalPnl = totalPnl,
            todaysPnl = today
        )
    }
}