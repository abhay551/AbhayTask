package com.abhay.abhaytask.domain.repository

import com.abhay.abhaytask.domain.model.Holding
import kotlinx.coroutines.flow.Flow

interface PortfolioRepository {
    suspend fun fetchRemoteHoldings(): List<Holding>
    fun getCachedHoldings(): Flow<List<Holding>>
    suspend fun refreshHoldings(): List<Holding>
}