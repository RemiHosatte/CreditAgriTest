package com.rh.creditagritest.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rh.creditagritest.Accounts
import com.rh.creditagritest.Banks
import com.rh.creditagritest.MainViewModel
import com.rh.creditagritest.R
import com.rh.creditagritest.Utils
import com.rh.creditagritest.api.APIState

@Composable
fun BankScreen(
    onNavigateToAccount: (account: Accounts) -> Unit, viewModel: MainViewModel = hiltViewModel()
) {

    val banksList = viewModel.banks.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getBanks()
    }

    val apiState by viewModel.apiState.collectAsState()

    if (apiState == APIState.ERROR) {
        Utils.showToast(LocalContext.current, stringResource(id = R.string.cannot_get_data))
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.LightGray,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.my_account),
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp, start = 16.dp, end = 16.dp),

                )


            if (apiState.equals(APIState.LOADING)) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .padding(top = 50.dp),
                    color = MaterialTheme.colorScheme.primary,
                )
            } else {
                BankList(listBanks = banksList.value, onAccountClick = onNavigateToAccount)
            }
        }

    }


}


data class ExpandItem(var state: MutableState<Boolean>, val bank: Banks)

@Composable
fun BankList(listBanks: List<Banks>, onAccountClick: (account: Accounts) -> Unit) {

    val expandItems = remember { mutableStateListOf<ExpandItem>() }


    //Update expandItems list
    LaunchedEffect(listBanks) {
        for (bank in listBanks) {
            expandItems.add(ExpandItem(mutableStateOf(false), bank))
        }
    }

    //Divide list in two
    val (caBanks, otherBanks) = expandItems.partition { it.bank.isCA == 1 }
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            Text(
                text = stringResource(R.string.credit_agricole),
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .height(50.dp)
                    .wrapContentHeight(align = CenterVertically)
                    .padding(start = 16.dp, end = 16.dp),
                textAlign = TextAlign.Center

            )
        }

        itemsIndexed(caBanks) { index, item ->
            BankItem(item, onAccountClick)
            if (index < listBanks.lastIndex) {
                Divider()
            }
        }

        item {
            Text(
                text = stringResource(R.string.others_accounts),
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .height(50.dp)
                    .wrapContentHeight(align = CenterVertically)
                    .padding(start = 16.dp, end = 16.dp),
                textAlign = TextAlign.Center

            )
        }

        itemsIndexed(otherBanks) { index, item ->
            BankItem(item, onAccountClick)
            if (index < listBanks.lastIndex) {
                Divider()
            }
        }
    }
}

@Composable
private fun BankItem(item: ExpandItem, onAccountClick: (account: Accounts) -> Unit) {


    Column {

        Row(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { item.state.value = !item.state.value }
            .background(Color.White)
            .padding(start = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = CenterVertically

        ) {
            Text(
                text = item.bank.name, modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold
            )
            val sumOfAllAccounts =
                String.format("%.2f", item.bank.accounts.sumOf { it.balance }) + " €"
            Text(
                text = sumOfAllAccounts,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End,
                color = Color.Gray
            )
            Icon(
                imageVector = if (item.state.value) {
                    Icons.Default.KeyboardArrowUp
                } else {
                    Icons.Default.KeyboardArrowDown
                },
                contentDescription = "icon_down",
                modifier = Modifier.width(50.dp),
                tint = Color.Gray
            )
        }
        Divider(height = 2.dp)

        AnimatedVisibility(visible = item.state.value) {
            Column {
                //Sort by alphabet
                if (item.state.value) {
                    item.bank.accounts.sortedBy { it.label }.forEach { account ->
                        AccountItem(account, onAccountClick = onAccountClick)
                    }
                }
            }
        }
    }
}


@Composable
private fun AccountItem(account: Accounts, onAccountClick: (account: Accounts) -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .background(Color.White)
        .clickable { onAccountClick(account) }
        .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = CenterVertically

    ) {
        Text(
            text = account.label,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = account.balance.toString() + " €",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            color = Color.Gray
        )
    }
    Divider()
}

@Composable
private fun Divider(height: Dp = 1.dp, color: Color = Color.LightGray) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(color = color)
    )
}