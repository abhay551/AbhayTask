@file:OptIn(ExperimentalCoroutinesApi::class)

package com.abhay.abhaytask.presentation.holdings

import androidx.compose.ui.graphics.Color
import app.cash.turbine.test
import com.abhay.abhaytask.domain.model.Holding
import com.abhay.abhaytask.domain.model.PortfolioSummary
import com.abhay.abhaytask.domain.repository.PortfolioRepository
import com.abhay.abhaytask.domain.usecase.CalculatePortfolioUseCase
import com.abhay.abhaytask.domain.usecase.MapHoldingToUiModelUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import kotlin.test.Test

class HoldingsViewModelTest {

    private val repository: PortfolioRepository = mockk()
    private val calculatePortfolioUseCase: CalculatePortfolioUseCase = mockk()
    private val mapHoldingToUiModelUseCase: MapHoldingToUiModelUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: HoldingsViewModel

    private val sampleHoldings = listOf(
        Holding(symbol = "AAPL", quantity = 10, ltp = 150.0, avgPrice = 100.0, close = 200.0),
        Holding(symbol = "GOOG", quantity = 5, ltp = 2800.0, avgPrice = 100.0, close = 200.0)
    )

    private val summary = PortfolioSummary(2000.0, 400.0, 200.0, 100.0)

    private fun makeUiModel(h: Holding): HoldingUiModel {
        val pnl = (h.ltp - h.avgPrice) * h.quantity
        val color = if (pnl >= 0) Color(0xFF2E7D32) else Color(0xFFC62828)
        return HoldingUiModel(
            symbol = h.symbol,
            netQuantityLabel = "Net Qty: ${h.quantity}",
            ltpLabel = "LTP: ₹ ${"%.2f".format(h.ltp)}",
            pnlLabel = "P&L: ₹ ${"%.2f".format(pnl)}",
            pnlColor = color
        )
    }

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `load success updates state`() = runTest {
        coEvery { repository.refreshHoldings() } returns sampleHoldings
        coEvery { repository.getCachedHoldings() } returns flowOf(sampleHoldings)
        every { calculatePortfolioUseCase(any()) } returns summary
        every { mapHoldingToUiModelUseCase(any()) } answers {
            val holdingArg = arg<Holding>(0)
            makeUiModel(holdingArg)
        }

        viewModel = HoldingsViewModel(
            repository,
            calculatePortfolioUseCase,
            mapHoldingToUiModelUseCase,
            testDispatcher
        )

        viewModel.state.test {
            val initial = awaitItem()
            assert(initial.isLoading)

            advanceUntilIdle()

            val loaded = awaitItem()
            assert(!loaded.isLoading)
            assertEquals(sampleHoldings.size, loaded.holdings.size)
            assertEquals(sampleHoldings.map { it.symbol }, loaded.holdings.map { it.symbol })
            assertEquals(summary, loaded.summary)
        }
    }

    @Test
    fun `load falls back to cached data when remote fails`() = runTest {
        coEvery { repository.refreshHoldings() } throws RuntimeException("Network down")
        coEvery { repository.getCachedHoldings() } returns flowOf(sampleHoldings)
        every { calculatePortfolioUseCase(any()) } returns summary
        every { mapHoldingToUiModelUseCase(any()) } answers {
            val holdingArg = arg<Holding>(0)
            makeUiModel(holdingArg)
        }

        viewModel = HoldingsViewModel(
            repository,
            calculatePortfolioUseCase,
            mapHoldingToUiModelUseCase,
            testDispatcher
        )

        viewModel.state.test {
            val initial = awaitItem()
            assert(initial.isLoading)

            advanceUntilIdle()

            val loaded = awaitItem()
            assert(!loaded.isLoading)
            assertEquals(sampleHoldings.size, loaded.holdings.size)
            assertEquals(sampleHoldings.map { it.symbol }, loaded.holdings.map { it.symbol })
            assertEquals(summary, loaded.summary)
        }
    }

    @Test
    fun `emits error when both remote and cached fail`() = runTest {
        coEvery { repository.refreshHoldings() } throws RuntimeException("Remote fail")
        coEvery { repository.getCachedHoldings() } returns flow {
            emit(emptyList<Holding>())
            throw RuntimeException("Cache fail")
        }

        every { calculatePortfolioUseCase(any()) } returns summary
        every { mapHoldingToUiModelUseCase(any()) } answers { makeUiModel(arg(0)) }

        viewModel = HoldingsViewModel(
            repository,
            calculatePortfolioUseCase,
            mapHoldingToUiModelUseCase,
            testDispatcher
        )

        viewModel.state.test {
            val initial = awaitItem()
            assert(initial.isLoading)

            advanceUntilIdle()

            val errorState = awaitItem()
            assert(!errorState.isLoading)
            assert(errorState.error != null)
        }
    }

    @Test
    fun `toggle summary expanded updates state`() = runTest {
        coEvery { repository.refreshHoldings() } returns sampleHoldings
        coEvery { repository.getCachedHoldings() } returns flowOf(sampleHoldings)
        every { calculatePortfolioUseCase(any()) } returns summary
        every { mapHoldingToUiModelUseCase(any()) } answers { makeUiModel(arg(0)) }

        viewModel = HoldingsViewModel(
            repository,
            calculatePortfolioUseCase,
            mapHoldingToUiModelUseCase,
            testDispatcher
        )

        advanceUntilIdle()

        viewModel.process(HoldingsIntent.ToggleSummaryExpanded(true))

        viewModel.state.test {
            val state = awaitItem()
            assert(state.isSummaryExpanded)
        }
    }

    @Test
    fun `load success updates state with UI models`() = runTest {
        coEvery { repository.refreshHoldings() } returns sampleHoldings
        coEvery { repository.getCachedHoldings() } returns flowOf(sampleHoldings)
        every { calculatePortfolioUseCase(any()) } returns summary
        every { mapHoldingToUiModelUseCase(any()) } answers { makeUiModel(arg(0)) }

        viewModel = HoldingsViewModel(
            repository,
            calculatePortfolioUseCase,
            mapHoldingToUiModelUseCase,
            testDispatcher
        )

        viewModel.state.test {
            val initial = awaitItem()
            assert(initial.isLoading)

            advanceUntilIdle()

            val loaded = awaitItem()
            assert(!loaded.isLoading)
            assertEquals(sampleHoldings.size, loaded.holdings.size)
            assertEquals(sampleHoldings.map { it.symbol }, loaded.holdings.map { it.symbol })
            assertEquals(summary, loaded.summary)
        }
    }
}

