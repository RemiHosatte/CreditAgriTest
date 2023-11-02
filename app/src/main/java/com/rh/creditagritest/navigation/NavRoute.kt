package com.rh.creditagritest.navigation

sealed class NavRoute(val route: String) {
    object BankList : NavRoute("BankList")
    object AccountDetail : NavRoute("AccountDetail/{idAccount}") {
        fun createRoute(idAccount: String) = "AccountDetail/$idAccount"
    }
}