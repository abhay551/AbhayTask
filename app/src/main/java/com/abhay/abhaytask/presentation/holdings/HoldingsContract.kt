package com.abhay.abhaytask.presentation.holdings

import com.abhay.abhaytask.domain.model.PortfolioSummary

sealed interface HoldingsIntent {
    object Load : HoldingsIntent
    data class ToggleSummaryExpanded(val expand: Boolean) : HoldingsIntent
}

data class HoldingsState(
    val isLoading: Boolean = false,
    val holdings: List<HoldingUiModel> = emptyList(),
    val summary: PortfolioSummary? = null,
    val isSummaryExpanded: Boolean = false,
    val error: String? = null
)