package com.abhay.abhaytask.data.mapper

import com.abhay.abhaytask.data.local.HoldingEntity
import com.abhay.abhaytask.data.remote.HoldingDto
import com.abhay.abhaytask.domain.model.Holding
import kotlin.test.Test
import kotlin.test.assertEquals

class HoldingMapperTest {

    @Test
    fun `HoldingDto maps correctly to Holding`() {
        val dto = HoldingDto(
            symbol = "TCS",
            quantity = 10,
            ltp = 3500.5,
            avgPrice = 3000.0,
            close = 3450.0
        )

        val domain = dto.toDomain()

        assertEquals(dto.symbol, domain.symbol)
        assertEquals(dto.quantity, domain.quantity)
        assertEquals(dto.ltp, domain.ltp)
        assertEquals(dto.avgPrice, domain.avgPrice)
        assertEquals(dto.close, domain.close)
    }

    @Test
    fun `HoldingEntity maps correctly to Holding`() {
        val entity = HoldingEntity(
            symbol = "INFY",
            quantity = 5,
            ltp = 1500.0,
            avgPrice = 1400.0,
            close = 1490.0
        )

        val domain = entity.toDomain()

        assertEquals(entity.symbol, domain.symbol)
        assertEquals(entity.quantity, domain.quantity)
        assertEquals(entity.ltp, domain.ltp)
        assertEquals(entity.avgPrice, domain.avgPrice)
        assertEquals(entity.close, domain.close)
    }

    @Test
    fun `Holding maps correctly to HoldingEntity`() {
        val domain = Holding(
            symbol = "HDFC",
            quantity = 2,
            ltp = 2700.0,
            avgPrice = 2600.0,
            close = 2680.0
        )

        val entity = domain.toEntity()

        assertEquals(domain.symbol, entity.symbol)
        assertEquals(domain.quantity, entity.quantity)
        assertEquals(domain.ltp, entity.ltp)
        assertEquals(domain.avgPrice, entity.avgPrice)
        assertEquals(domain.close, entity.close)
    }
}
