package com.abhay.abhaytask.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlin.test.Test
import kotlin.test.assertEquals

class CoroutineModuleTest {

    @Test
    fun `provideIoDispatcher returns Dispatchers_IO`() {
        val dispatcher: CoroutineDispatcher = CoroutineModule.provideIoDispatcher()
        assertEquals(Dispatchers.IO, dispatcher)
    }
}