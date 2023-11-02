package com.rh.creditagritest.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rh.creditagritest.MainViewModel
import com.rh.creditagritest.Operations
import com.rh.creditagritest.R
import com.rh.creditagritest.getDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    idAccount: String, viewModel: MainViewModel = hiltViewModel(), onNavigateBack: () -> Unit
) {

    val accountDetails = viewModel.getAccountByID(idAccount)

    if (accountDetails == null) {
        onNavigateBack()
        return
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {


            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.my_account),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 16.dp),
                )
            }, navigationIcon = {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "icon_back",
                    modifier = Modifier
                        .clickable { onNavigateBack() }
                        .fillMaxHeight()
                        .padding(start = 16.dp)
                        .width(25.dp))
            }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer //Add your own color here, just to clarify.
            )
            )

            Column(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(top = 50.dp, bottom = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = String.format("%.2f", accountDetails.balance) + " €",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Text(
                    text = accountDetails.label,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 25.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer


                )
            }
            val sortedOperations = accountDetails?.operations.orEmpty()
                .sortedWith(compareByDescending<Operations> { it.date }.thenBy { it.title })


            LazyColumn {
                itemsIndexed(sortedOperations) { index, operation ->
                    OperationItem(operation)
                    if (index < sortedOperations.lastIndex) {
                        Divider()
                    }
                }
            }
        }
    }


}

@Composable
fun OperationItem(operation: Operations) {

    Row(
        Modifier
            .clickable { }
            .padding(8.dp)) {
        Column(
            Modifier
                .height(50.dp)
                .weight(1f)
        ) {
            Text(text = operation.title ?: "", fontWeight = FontWeight.Bold)
            Text(text = operation.getDateTime() ?: "")
        }

        Text(
            text = operation.amount + " €",
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f),
            textAlign = TextAlign.End,
            color = Color.Gray
        )
    }
}
