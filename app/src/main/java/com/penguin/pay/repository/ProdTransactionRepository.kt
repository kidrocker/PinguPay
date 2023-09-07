package com.penguin.pay.repository

import com.penguin.pay.models.Country
import com.penguin.pay.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProdTransactionRepository(
    private val network: NetworkDataSource
) : TransactRepository {

    // ideally you would fetch this list from the backend
    override val countries: List<Country>
        get() = listOf(
            Country(name = "Kenya", abbreviation = "KES", extension = "+254", validator = 9),
            Country(name = "Uganda", abbreviation = "UGX", extension = "+256", validator = 7),
            Country(name = "Nigeria", abbreviation = "NGN", extension = "+234", validator = 7),
            Country(name = "Tanzania", abbreviation = "TZS", extension = "+255", validator = 9),
        )

    override suspend fun calculateReceiveAmount(binary: String, country: Country): Flow<String?> {
        return flow {
            var rate:Float? = null

            try {
            rate = network.getExchangeRate().rates[country.abbreviation]
            }catch (e:Exception){
                e.printStackTrace()
            }

            val integerValue = binary.toInt(2)
            val converted = rate?.let { integerValue * rate }
            emit(converted?.toUInt()?.toString(radix = 2))
        }
    }
}