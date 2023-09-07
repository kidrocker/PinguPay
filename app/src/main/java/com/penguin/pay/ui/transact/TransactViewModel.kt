package com.penguin.pay.ui.transact

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.penguin.pay.R
import com.penguin.pay.models.Country
import com.penguin.pay.repository.TransactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactViewModel @Inject constructor(
    private val repository: TransactRepository
) : ViewModel() {
    val countries = repository.countries

    val uiState = MutableStateFlow(
        Transact(
            name = "",
            country = countries[0],
            phone = "",
            amount = "",
            errors = Errors(),
            receivingAmount = null,
            showSuccessDialog = false
        )
    )

    fun onNameTextChange(value: String) {
        if (value.length >= 250) {
            uiState.update {
                it.copy(
                    errors = it.errors.copy(
                        nameError = R.string.name_error
                    )
                )
            }
        } else {
            uiState.update {
                it.copy(
                    errors = it.errors.copy(
                        nameError = null
                    ),
                    name = value
                )
            }
        }
    }

    fun onPhoneTextChange(value: String) {
        uiState.update {
            it.copy(
                phone = if (value.length == 1 && value[0] == '0') "" else value.trim(),
                errors = it.errors.copy(
                    phoneError = if (validatePhone(value)) null else R.string.phone_error
                )
            )
        }
    }

    fun onCountrySelectionChange(value: Country) {
        uiState.update {
            it.copy(
                country = value,
            )
        }
    }

    fun onAmountTextChange(value: String) {

        val isBinary = isBinary(value)



        uiState.update {
            it.copy(
                amount = value,
                errors = it.errors.copy(
                    amountError = if (isBinary) null else R.string.amount_error
                )
            )
        }

        if (isBinary) {
            calculateExchangeRate(value.trim())
        }
    }

    private fun calculateExchangeRate(binary:String) {
        viewModelScope.launch {
            uiState.update {
                it.copy(
                    receivingAmount = repository.calculateReceiveAmount(
                        binary,
                        uiState.value.country
                    ).first()
                )
            }
        }
    }

    fun sendMoneyButtonClick() {
        val transact = uiState.value

        // validate final name input
        if (transact.name.isBlank() || transact.name.length > 255) {
            uiState.update {
                it.copy(
                    errors = it.errors.copy(
                        nameError = R.string.name_error
                    )
                )
            }
            return
        }

        // validate final phone input
        val phone = transact.phone
        if (phone.length != transact.country.validator || phone[0] == '0') {
            uiState.update {
                it.copy(
                    errors = it.errors.copy(
                        phoneError = R.string.phone_error
                    )
                )
            }
            return
        }

        // validate final amount input
        val amount = transact.amount
        if (!isBinary(amount)) {
            uiState.update {
                it.copy(
                    errors = it.errors.copy(
                        amountError = R.string.amount_error
                    )
                )
            }
            return
        }

        // everything is okay
        // should send the transaction to the backend

        uiState.update {
            it.copy(
                showSuccessDialog = true
            )
        }
    }

    fun toggleDialog(value:Boolean){
        uiState.update {
            it.copy(
                showSuccessDialog = value
            )
        }
    }

    private fun validatePhone(value: String) =
        value.trim().length <= uiState.value.country.validator


    private fun isBinary(value: String) = value.matches("^[0-1]+\$".toRegex())

    data class Transact(
        val name: String,
        val country: Country,
        val phone: String,
        val amount: String,
        val errors: Errors,
        val receivingAmount: String?,
        val showSuccessDialog: Boolean
    )

    data class Errors(
        @StringRes
        val nameError: Int? = null,
        @StringRes
        val phoneError: Int? = null,
        @StringRes
        val amountError: Int? = null
    )


}