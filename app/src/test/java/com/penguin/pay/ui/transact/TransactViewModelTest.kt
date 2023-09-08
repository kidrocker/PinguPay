package com.penguin.pay.ui.transact

import com.penguin.pay.MainDispatcherRule
import com.penguin.pay.repository.TransactRepository
import kotlinx.coroutines.flow.update
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class TransactViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    private lateinit var viewModel: TransactViewModel
    private lateinit var repository: TransactRepository

    @Before
    fun setUp() {
        repository = MockTransactionrepository()

        viewModel = TransactViewModel(repository)
    }


    @Test
    fun `test onNameTextChange`() {
        val initialValue = viewModel.uiState.value.copy(name = "")
        viewModel.uiState.value = initialValue

        viewModel.onNameTextChange("John Doe")

        // Assert: Verify the expected changes
        val newState = viewModel.uiState.value
        assertEquals("John Doe", newState.name)
        assertNull(newState.errors.nameError)
    }

    @Test
    fun `test onNameTextChange with Name Too Large`() {
        val initialValue = viewModel.uiState.value.copy(name = "John Doe")
        viewModel.uiState.value = initialValue

        val tooLargeName = "A".repeat(256) // Creating a name that exceeds the maximum length
        viewModel.onNameTextChange(tooLargeName)

        // Assert: Verify that the name error is set
        val newState = viewModel.uiState.value
        assertNotNull(newState.errors.nameError)
    }

    @Test
    fun `test phone number provided is too large`() {
        val country = repository.countries[0]
        viewModel.uiState.update { it.copy(country = country) }

        val invalidPhoneNumber = buildString {
            for (i in 0..country.validator + 1) {
                append(Random.nextInt(1, 9))
            }
        }  // This phone number is larger than the required length

        viewModel.onPhoneTextChange(invalidPhoneNumber)
        assertNotNull(viewModel.uiState.value.errors.phoneError)
    }

    @Test
    fun `test phone number cannot start with a zero`() {
        viewModel.uiState.update { it.copy(phone = "") } // make sure phone is empty

        viewModel.onPhoneTextChange("0")
        assertTrue(viewModel.uiState.value.phone.isEmpty())
    }

    @Test
    fun `test amount entered is not valid`() {
        val invalidAmount = "012300033"
        viewModel.onAmountTextChange(invalidAmount)
        assertNotNull(viewModel.uiState.value.errors.amountError)
    }

    @Test
    fun `test expected amount is set if valid amount is set`() {
        val validAmount = "010110"

        viewModel.onAmountTextChange(validAmount)

        assertNotNull(viewModel.uiState.value.receivingAmount)
    }

    @Test
    fun `test dialog is shown only when valid values are provided`() {
        viewModel.uiState.update {
            it.copy(
                country = repository.countries[0],
                name = "John does",
                phone = "7000000002",
                amount = "010110"
            )
        }
        viewModel.sendMoneyButtonClick()

        assertFalse(viewModel.uiState.value.showSuccessDialog)

        viewModel.uiState.update {
            it.copy(
                phone = "702040302"
            )
        }
        viewModel.sendMoneyButtonClick()


        assertTrue(viewModel.uiState.value.showSuccessDialog)

    }

}