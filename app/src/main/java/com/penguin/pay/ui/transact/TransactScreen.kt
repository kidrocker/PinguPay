package com.penguin.pay.ui.transact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.penguin.pay.R
import com.penguin.pay.models.Country
import com.penguin.pay.ui.components.PenguinOutlinedSpinner
import com.penguin.pay.ui.components.PenguinOutlinedTextField
import com.penguin.pay.ui.theme.PenguinPayTheme

@Composable
fun TransactScreen(
    transactViewModel: TransactViewModel
) {

    val state = transactViewModel.uiState.collectAsState()

    TransactScreenContent(
        transact = state.value,
        onNameValueChanged = transactViewModel::onNameTextChange,
        onPhoneValueChanged = transactViewModel::onPhoneTextChange,
        onAmountValueChange = transactViewModel::onAmountTextChange,
        onCountryValueChange = transactViewModel::onCountrySelectionChange,
        onSendMoneyButtonClick = transactViewModel::sendMoneyButtonClick,
        countries = transactViewModel.countries
    )

    if (state.value.showSuccessDialog) {
        TransactionSuccessDialog(
            transact = state.value, setShowDialog = transactViewModel::toggleDialog
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactScreenContent(
    transact: TransactViewModel.Transact,
    onNameValueChanged: (value: String) -> Unit,
    onPhoneValueChanged: (value: String) -> Unit,
    onAmountValueChange: (value: String) -> Unit,
    onCountryValueChange: (value: Country) -> Unit,
    onSendMoneyButtonClick: () -> Unit,
    countries: List<Country>
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = stringResource(R.string.transact)) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                titleContentColor = MaterialTheme.colorScheme.primary,
            )
        )
    }) { innerPadding ->
        Column(

            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            PenguinOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = transact.name,
                isError = transact.errors.nameError != null,
                onTextValueChanged = onNameValueChanged,
                label = {
                    Text(text = stringResource(R.string.enter_the_recipient_names))
                },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )

            if (transact.errors.nameError != null) {
                Text(
                    text = stringResource(id = transact.errors.nameError),
                    color = Color.Red
                )
            }

            PenguinOutlinedSpinner(
                title = stringResource(R.string.select_country),
                options = countries.map { "${it.name} (${it.extension})" },
                onItemSelected = { pos ->
                    onCountryValueChange(countries[pos])
                },
                selected = "${transact.country.name} (${transact.country.extension})"
            )

            PenguinOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = transact.phone,
                onTextValueChanged = onPhoneValueChanged,
                label = {
                    Text(text = stringResource(R.string.enter_phone_number))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            if (transact.errors.phoneError != null) {
                Text(
                    text = stringResource(id = transact.errors.phoneError),
                    color = Color.Red
                )
            }

            PenguinOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = transact.amount,
                onTextValueChanged = onAmountValueChange,
                label = {
                    Text(text = stringResource(R.string.enter_amount_in_binary))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            if (transact.errors.amountError != null) {
                Text(
                    text = stringResource(id = transact.errors.amountError),
                    color = Color.Red
                )
            }

            if (transact.errors.amountError == null && transact.receivingAmount != null) {

                Text(text = stringResource(
                    R.string.the_recipient_will_receive_in,
                    transact.receivingAmount,
                    transact.country.abbreviation
                ))

            }

            Button(modifier = Modifier.fillMaxWidth(), onClick = onSendMoneyButtonClick) {
                Text(text = stringResource(R.string.complete_transaction))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TransactScreenPreview() {
    PenguinPayTheme {
        TransactScreenContent(
            TransactViewModel.Transact(
                "",
                Country(name = "Kenya", abbreviation = "KES", extension = "+254", validator = 9),
                "",
                "",
                TransactViewModel.Errors(),
                receivingAmount = null,
                showSuccessDialog = false
            ),
            {},
            {},
            {},
            {},
            {},
            emptyList()
        )
    }
}