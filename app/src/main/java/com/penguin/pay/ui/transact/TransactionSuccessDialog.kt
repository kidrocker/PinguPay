package com.penguin.pay.ui.transact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.penguin.pay.R
import com.penguin.pay.models.Country
import com.penguin.pay.ui.theme.PenguinPayTheme

@Composable
fun TransactionSuccessDialog(
    transact: TransactViewModel.Transact,
    setShowDialog: (Boolean) -> Unit,
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .padding(horizontal = 8.dp, vertical = 16.dp),

            ) {

                Row {
                    Icon(
                        Icons.Filled.CheckCircle,
                        contentDescription = "success",
                        tint = MaterialTheme.colorScheme.tertiary
                    )

                    Text(
                        text = stringResource(R.string.transaction_success),
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
                Divider(thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
                Text(
                    modifier = Modifier.padding(top = 6.dp),
                    text = stringResource(R.string.will_receive, transact.name, transact.amount),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary

                )


                Text(
                    text = stringResource(R.string.recipient_details),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = stringResource(
                        R.string.phone,
                        transact.country.extension,
                        transact.phone
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = stringResource(R.string.country, transact.country.name),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = stringResource(R.string.currency, transact.country.abbreviation),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.size(12.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { setShowDialog(false) }) {
                        Text(text = stringResource(R.string.close), color = MaterialTheme.colorScheme.secondary)

                    }
                    Button(onClick = { }) {
                        Text(text = stringResource(R.string.print_receipt))
                    }
                }

            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun TransactionSuccessDialogPreview() {
    PenguinPayTheme {
        TransactionSuccessDialog(
            TransactViewModel.Transact(
                name = "Denis Kiuras",
                country = Country(
                    name = "Uganda",
                    abbreviation = "UGX",
                    extension = "+256",
                    validator = 7
                ),
                amount = "01001110110",
                phone = "706856744",
                errors = TransactViewModel.Errors(),
                receivingAmount = null,
                showSuccessDialog = false
            )
        ) {}

    }
}