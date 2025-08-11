package com.abhay.abhaytask.presentation.holdings

import androidx.compose.ui.graphics.Color

data class HoldingUiModel(
    val symbol: String,
    val netQuantityLabel: String,
    val ltpLabel: String,
    val pnlLabel: String,
    val pnlColor: Color
)
