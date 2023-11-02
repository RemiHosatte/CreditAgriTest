package com.rh.creditagritest.api

import com.rh.creditagritest.BankData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository(private val apiClient: APIClient) {

    fun getBanks(): Flow<BankData> = flow {
        emit(apiClient.getBanks())
    }
}