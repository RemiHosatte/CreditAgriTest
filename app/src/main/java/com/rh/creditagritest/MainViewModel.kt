package com.rh.creditagritest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    fun getBanks() {
        viewModelScope.launch {
            repository.getBanks().catch {
                it.message
                Log.e("TAG", "getBanks: " + "error" + it.message)
                //Error
            }.collect {
                Log.e("TAG", "getBanks: ")
                _banks.value = it
            }
        }
    }


    fun getAccountByID(id: String): Accounts? {
        return _banks.value.flatMap { it.accounts }.find { it.id == id }
    }
}