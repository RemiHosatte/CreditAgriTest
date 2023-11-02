package com.rh.creditagritest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rh.creditagritest.ui.theme.CreditAgriTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreditAgriTestTheme {
                // A surface container using the 'background' color from the theme
                MyAppNavHost()
            }
        }
    }
}


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
                //Set error
            }

        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    CreditAgriTestTheme {
//    }
//}