package com.abhay.abhaytask.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abhay.abhaytask.presentation.holdings.HoldingUiModel

@Composable
fun HoldingRow(holdingUiModel: HoldingUiModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = holdingUiModel.symbol,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = holdingUiModel.netQuantityLabel,
                style = MaterialTheme.typography.labelMedium
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(text = holdingUiModel.ltpLabel, style = MaterialTheme.typography.labelMedium)
            Text(
                text = holdingUiModel.pnlLabel,
                style = MaterialTheme.typography.labelMedium,
                color = holdingUiModel.pnlColor
            )
        }
    }
}

