package com.abhay.abhaytask.data.remote

data class HoldingDto(
    val symbol: String,
    val quantity: Int,
    val ltp: Double,
    val avgPrice: Double,
    val close: Double
)

data class HoldingsData(val userHolding: List<HoldingDto>)

data class HoldingsResponse(val data: HoldingsData)
