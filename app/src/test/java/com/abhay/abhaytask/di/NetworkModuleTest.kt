package com.abhay.abhaytask.di

import com.abhay.abhaytask.data.remote.PortfolioApi
import com.google.gson.Gson
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Test
import retrofit2.Retrofit

class NetworkModuleTest {

    @Test
    fun `provideLoggingInterceptor returns interceptor with BODY level`() {
        val interceptor = NetworkModule.provideLoggingInterceptor()
        assertEquals(HttpLoggingInterceptor.Level.BODY, interceptor.level)
    }

    @Test
    fun `provideOkHttpClient adds logging interceptor`() {
        val loggingInterceptor = mockk<HttpLoggingInterceptor>()
        val okHttpClient = NetworkModule.provideOkHttpClient(loggingInterceptor)

        assertTrue(okHttpClient.interceptors.contains(loggingInterceptor))
    }

    @Test
    fun `provideGson returns non-null Gson`() {
        val gson = NetworkModule.provideGson()
        assertNotNull(gson)
        assertEquals(Gson::class, gson::class)
    }

    @Test
    fun `provideRetrofit returns retrofit with correct baseUrl`() {
        val okHttpClient = mockk<OkHttpClient>()
        val gson = mockk<Gson>()
        val retrofit = NetworkModule.provideRetrofit(okHttpClient, gson)
        assertTrue(retrofit.baseUrl().toString().contains("mockbin.io"))
    }

    @Test
    fun `provideApi creates PortfolioApi instance`() {
        val retrofit = mockk<Retrofit>()
        val apiMock = mockk<PortfolioApi>()
        every { retrofit.create(PortfolioApi::class.java) } returns apiMock

        val api = NetworkModule.provideApi(retrofit)

        assertEquals(apiMock, api)
    }
}