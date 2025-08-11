package com.abhay.abhaytask.data.mapper

import com.abhay.abhaytask.data.local.HoldingEntity
import com.abhay.abhaytask.data.remote.HoldingDto
import com.abhay.abhaytask.domain.model.Holding

fun HoldingDto.toDomain(): Holding =
    Holding(symbol, quantity, ltp, avgPrice, close)

fun HoldingEntity.toDomain() = Holding(symbol, quantity, ltp, avgPrice, close)

fun Holding.toEntity() = HoldingEntity(symbol, quantity, ltp, avgPrice, close)