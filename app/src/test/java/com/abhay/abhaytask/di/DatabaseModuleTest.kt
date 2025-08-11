package com.abhay.abhaytask.di

import com.abhay.abhaytask.data.local.AppDatabase
import com.abhay.abhaytask.data.local.HoldingDao
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class DatabaseModuleTest {
    @Test
    fun provideHoldingDao_returnsDaoFromDatabase() {
        val mockDao = mockk<HoldingDao>()
        val mockDb = mockk<AppDatabase> {
            every { holdingDao() } returns mockDao
        }

        val dao = DatabaseModule.provideHoldingDao(mockDb)

        assertNotNull(dao)
        assertEquals(mockDao, dao)
    }
}