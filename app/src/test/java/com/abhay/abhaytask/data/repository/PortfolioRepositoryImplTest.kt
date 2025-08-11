package com.abhay.abhaytask.data.repository

import com.abhay.abhaytask.data.local.HoldingDao
import com.abhay.abhaytask.data.local.HoldingEntity
import com.abhay.abhaytask.data.remote.HoldingDto
import com.abhay.abhaytask.data.remote.HoldingsData
import com.abhay.abhaytask.data.remote.HoldingsResponse
import com.abhay.abhaytask.data.remote.PortfolioApi
import com.abhay.abhaytask.domain.model.Holding
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PortfolioRepositoryImplTest {

    private lateinit var api: PortfolioApi
    private lateinit var dao: HoldingDao
    private lateinit var repository: PortfolioRepositoryImpl

    @BeforeTest
    fun setup() {
        api = mockk()
        dao = mockk()
        repository = PortfolioRepositoryImpl(api, dao)
    }

    @Test
    fun `fetchRemoteHoldings returns mapped domain objects`() = runBlocking {
        val dtoList = listOf(
            HoldingDto(
                "AAPL", 10, 150.0,
                avgPrice = 100.0,
                close = 100.0
            ),
            HoldingDto("GOOGL", 5, 2000.0, 100.0, 100.0)
        )
        val response = HoldingsResponse(
            data = HoldingsData(
                userHolding = dtoList
            )
        )
        coEvery { api.getHoldings() } returns response

        val result = repository.fetchRemoteHoldings()

        assertEquals(2, result.size)
        assertEquals("AAPL", result[0].symbol)
        coVerify { api.getHoldings() }
    }

    @Test
    fun `observeCachedHoldings maps entities to domain objects`() = runBlocking {
        val entityList = listOf(
            HoldingEntity("MSFT", 3, 300.0, 200.0, 100.0),
            HoldingEntity("TSLA", 1, 700.0, 200.0, 100.0)
        )
        every { dao.getAll() } returns flowOf(entityList)

        val resultFlow: Flow<List<Holding>> = repository.getCachedHoldings()
        val result = resultFlow.first()

        assertEquals(2, result.size)
        assertEquals("MSFT", result[0].symbol)
        verify { dao.getAll() }
    }

    @Test
    fun `refreshHoldings clears, inserts, and returns remote data`() = runTest {
        val remoteHoldings = listOf(
            Holding("NFLX", 2, 500.0, 300.0, 200.0),
            Holding("AMZN", 4, 3000.0, 200.0, 300.0)
        )
        coEvery { api.getHoldings() } returns HoldingsResponse(
            data = HoldingsData(
                userHolding = remoteHoldings.map {
                    HoldingDto(it.symbol, it.quantity, it.ltp, it.avgPrice, it.close)
                }
            )
        )
        coEvery { dao.clearAll() } just Runs
        coEvery { dao.insertAll(any()) } just Runs

        val result = repository.refreshHoldings()

        assertEquals(remoteHoldings, result)
        coVerifyOrder {
            dao.clearAll()
            dao.insertAll(match { it.size == 2 })
        }
    }

}


