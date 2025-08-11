package com.abhay.abhaytask.data.remote

import retrofit2.http.GET

interface PortfolioApi {
    @GET("/")
    suspend fun getHoldings(): HoldingsResponse
}