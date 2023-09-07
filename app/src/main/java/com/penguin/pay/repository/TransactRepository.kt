package com.penguin.pay.repository

import com.penguin.pay.models.Country
import com.penguin.pay.models.Rate
import com.penguin.pay.models.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactRepository {

    val countries:List<Country>

    suspend fun calculateReceiveAmount(binary:String, country: Country): Flow<String?>
}