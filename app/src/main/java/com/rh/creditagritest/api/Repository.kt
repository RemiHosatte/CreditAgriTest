package com.rh.creditagritest.api

import com.rh.creditagritest.Banks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository(private val apiClient: APIClient) {

    fun getBanks(): Flow<List<Banks>> = flow {
        emit(apiClient.getBanks())
    }
}