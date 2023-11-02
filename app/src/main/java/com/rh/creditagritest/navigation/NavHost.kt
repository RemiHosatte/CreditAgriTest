package com.rh.creditagritest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rh.creditagritest.ui.screen.AccountScreen
import com.rh.creditagritest.ui.screen.BankScreen
import com.rh.creditagritest.MainViewModel
import com.rh.creditagritest.R
import com.rh.creditagritest.Utils


@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavRoute.BankList.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavRoute.BankList.route) {

            BankScreen(
                onNavigateToAccount = {
                    navController.navigate(NavRoute.AccountDetail.createRoute(it.id))
                },
            )

        }

        composable(
            NavRoute.AccountDetail.route,
            arguments = listOf(navArgument("idAccount") {
                defaultValue = ""
            })
        ) { backStackEntry ->

            //Get the same viewmodel as BankList
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(NavRoute.BankList.route)
            }
            val parentViewModel = hiltViewModel<MainViewModel>(parentEntry)

            val idAccount = backStackEntry.arguments?.getString("idAccount")
            if (!idAccount.isNullOrEmpty()) {
                AccountScreen(idAccount, parentViewModel) {
                    navController.navigateUp()
                }
            } else {
                navController.navigateUp()
                Utils.showToast(LocalContext.current, stringResource(R.string.cannot_redirect))
            }

        }
    }
}