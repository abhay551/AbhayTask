package com.abhay.abhaytask.di

import com.abhay.abhaytask.data.repository.PortfolioRepositoryImpl
import io.mockk.mockk
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.Test

class RepositoryModuleTest {

    @Test
    fun `providePortfolioRepository returns correct type`() {
        val repositoryImpl = mockk<PortfolioRepositoryImpl>()
        val repo = RepositoryModule.providePortfolioRepository(repositoryImpl)
        assertNotNull(repo)
        assertTrue(repo is PortfolioRepositoryImpl)
    }
}