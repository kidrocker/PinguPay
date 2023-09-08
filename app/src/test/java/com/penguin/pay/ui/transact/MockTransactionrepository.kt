package com.penguin.pay.ui.transact

import com.penguin.pay.models.Country
import com.penguin.pay.repository.TransactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockTransactionrepository():TransactRepository {
    override val countries: List<Country>
        get() = listOf(
            Country(name = "Kenya", abbreviation = "KES", extension = "+254", validator = 9),
            Country(name = "Uganda", abbreviation = "UGX", extension = "+256", validator = 7),
            Country(name = "Nigeria", abbreviation = "NGN", extension = "+234", validator = 7),
            Country(name = "Tanzania", abbreviation = "TZS", extension = "+255", validator = 9),
        )

    override suspend fun calculateReceiveAmount(binary: String, country: Country): Flow<String?> {
        return flow {
            emit(binary)
        }
    }
}