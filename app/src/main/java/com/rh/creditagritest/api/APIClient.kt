package com.rh.creditagritest.api

import com.rh.creditagritest.Banks
import retrofit2.http.GET

interface APIClient {
    @GET("banks.json")
    suspend fun getBanks(): List<Banks>
}