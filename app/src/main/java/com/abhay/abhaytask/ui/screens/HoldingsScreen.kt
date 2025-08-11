@file:OptIn(ExperimentalMaterial3Api::class)

package com.abhay.abhaytask.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abhay.abhaytask.presentation.holdings.HoldingsIntent
import com.abhay.abhaytask.presentation.holdings.HoldingsViewModel
import com.abhay.abhaytask.ui.components.Error
import com.abhay.abhaytask.ui.components.HoldingRow
import com.abhay.abhaytask.ui.components.Loading
import com.abhay.abhaytask.ui.components.SummaryBottomBar

@Composable
fun HoldingsScreen(viewModel: HoldingsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text("Portfolio") }) },
        bottomBar = {
            SummaryBottomBar(
                summary = state.summary,
                isExpanded = state.isSummaryExpanded,
                onToggle = { viewModel.process(HoldingsIntent.ToggleSummaryExpanded(!state.isSummaryExpanded)) }
            )
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Loading(modifier = Modifier.padding(paddingValues))
        } else {
            if (state.error != null) {
                Error(errorMessage = state.error, modifier = Modifier.padding(paddingValues))
            } else {
                LazyColumn(contentPadding = paddingValues) {
                    items(state.holdings) { h ->
                        HoldingRow(h)
                        HorizontalDivider(
                            Modifier,
                            DividerDefaults.Thickness,
                            DividerDefaults.color
                        )
                    }
                }
            }
        }
    }
}
