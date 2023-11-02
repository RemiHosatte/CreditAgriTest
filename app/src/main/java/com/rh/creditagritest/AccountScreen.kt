package com.rh.creditagritest

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AccountScreen(idAccount: String, viewModel: MainViewModel = hiltViewModel()) {

    val accountDetails = viewModel.getAccountByID(idAccount)

    Log.e("TAG", "AccountScreen: " + accountDetails)
}