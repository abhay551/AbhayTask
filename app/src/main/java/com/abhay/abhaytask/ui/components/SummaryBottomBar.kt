package com.abhay.abhaytask.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abhay.abhaytask.domain.model.PortfolioSummary

@Composable
fun SummaryBottomBar(summary: PortfolioSummary?, isExpanded: Boolean, onToggle: () -> Unit) {
    val height by animateDpAsState(
        targetValue = if (isExpanded) 170.dp else 70.dp,
        animationSpec = tween(350)
    )
    val toggleIcon =
        if (isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            if (isExpanded && summary != null) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Current value*")
                        Text(
                            "₹ ${"%.2f".format(summary.currentValue)}",
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total investment*")
                        Text(
                            "₹ ${"%.2f".format(summary.totalInvestment)}",
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val todaysPnlColor =
                            if (summary.todaysPnl >= 0) Color(0xFF2E7D32) else Color(0xFFC62828)
                        Text("Today's Profit & Loss*")
                        Text("₹ ${"%.2f".format(summary.todaysPnl)}", color = todaysPnlColor)
                    }
                }
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(
                    Modifier,
                    DividerDefaults.Thickness,
                    DividerDefaults.color
                )
                Spacer(Modifier.height(8.dp))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Profit & Loss*"
                    )
                    IconButton(onClick = onToggle) {
                        Icon(toggleIcon, contentDescription = "toggle")
                    }
                }

                if (summary != null) {
                    val totalPnlColor =
                        if (summary.totalPnl >= 0) Color(0xFF2E7D32) else Color(0xFFC62828)
                    Text("₹ ${"%.2f".format(summary.totalPnl)}", color = totalPnlColor)
                }
            }
        }
    }
}
