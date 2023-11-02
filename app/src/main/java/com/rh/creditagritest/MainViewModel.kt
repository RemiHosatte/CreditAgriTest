package com.rh.creditagritest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rh.creditagritest.api.APIState
import com.rh.creditagritest.api.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _banks = MutableStateFlow(listOf<Banks>())
    val banks: StateFlow<List<Banks>> = _banks

    private val _apiState = MutableStateFlow(APIState.LOADING)
    val apiState: StateFlow<APIState> = _apiState
    fun getBanks() {
        _apiState.value = APIState.LOADING
        viewModelScope.launch {
            repository.getBanks().catch {
                //Error
                _apiState.value = APIState.ERROR

            }.collect {
                _banks.value = it
                _apiState.value = APIState.SUCCESS
            }
        }
    }


    fun getAccountByID(id: String): Accounts? {
        return _banks.value.flatMap { it.accounts }.find { it.id == id }
    }
}