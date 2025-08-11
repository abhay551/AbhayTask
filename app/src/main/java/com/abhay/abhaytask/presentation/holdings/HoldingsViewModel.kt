package com.abhay.abhaytask.presentation.holdings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.abhaytask.domain.repository.PortfolioRepository
import com.abhay.abhaytask.domain.usecase.CalculatePortfolioUseCase
import com.abhay.abhaytask.domain.usecase.MapHoldingToUiModelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HoldingsViewModel @Inject constructor(
    private val repository: PortfolioRepository,
    private val calculatePortfolioUseCase: CalculatePortfolioUseCase,
    private val mapHoldingToUiModelUseCase: MapHoldingToUiModelUseCase,
    @Named("IO") private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(HoldingsState(isLoading = true))
    val state: StateFlow<HoldingsState> = _state.asStateFlow()

    init {
        process(HoldingsIntent.Load)
    }

    fun process(intent: HoldingsIntent) {
        when (intent) {
            HoldingsIntent.Load -> load()
            is HoldingsIntent.ToggleSummaryExpanded ->
                _state.update { it.copy(isSummaryExpanded = intent.expand) }
        }
    }

    private fun load() {
        viewModelScope.launch(ioDispatcher) {
            _state.update { it.copy(isLoading = true, error = null) }

            val holdings = runCatching { repository.refreshHoldings() }
                .getOrElse {
                    repository.getCachedHoldings().firstOrNull() ?: emptyList()
                }

            if (holdings.isEmpty()) {
                _state.update {
                    it.copy(isLoading = false, error = "No holdings available")
                }
                return@launch
            }

            val summary = calculatePortfolioUseCase(holdings)
            val uiHoldings = holdings.map { mapHoldingToUiModelUseCase(it) }

            _state.update {
                it.copy(
                    isLoading = false,
                    holdings = uiHoldings,
                    summary = summary,
                    error = null
                )
            }
        }
    }
}

