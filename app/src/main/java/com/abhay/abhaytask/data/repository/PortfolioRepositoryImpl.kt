package com.abhay.abhaytask.data.repository

import com.abhay.abhaytask.data.local.HoldingDao
import com.abhay.abhaytask.data.mapper.toDomain
import com.abhay.abhaytask.data.mapper.toEntity
import com.abhay.abhaytask.data.remote.PortfolioApi
import com.abhay.abhaytask.domain.model.Holding
import com.abhay.abhaytask.domain.repository.PortfolioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PortfolioRepositoryImpl @Inject constructor(
    private val api: PortfolioApi,
    private val dao: HoldingDao
) : PortfolioRepository {

    override suspend fun fetchRemoteHoldings(): List<Holding> =
        api.getHoldings().data.userHolding.map { it.toDomain() }

    override fun getCachedHoldings(): Flow<List<Holding>> =
        dao.getAll().map { list -> list.map { it.toDomain() } }

    override suspend fun refreshHoldings(): List<Holding> {
        val remote = fetchRemoteHoldings()
        dao.clearAll()
        dao.insertAll(remote.map { it.toEntity() })
        return remote
    }
}