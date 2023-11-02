package com.rh.creditagritest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rh.creditagritest.api.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _banks = MutableStateFlow(BankData())
    val banks: BankData = _banks.value



    init {
        getBanks()
    }
    fun getBanks() {
        viewModelScope.launch {
            repository.getBanks().catch {
                //Error
            }.collect {
                _banks.value = it
            }
        }
    }
}