package com.abhay.abhaytask.domain.model

data class PortfolioSummary(
    val currentValue: Double,
    val totalInvestment: Double,
    val totalPnl: Double,
    val todaysPnl: Double
)