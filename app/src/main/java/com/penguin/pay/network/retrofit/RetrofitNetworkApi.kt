package com.penguin.pay.network.retrofit


import com.penguin.pay.network.models.NetworkExchange
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitNetworkApi {

    @GET(value = "latest.json")
    suspend fun getExchangeRate(
        @Query("base") base: String = "USD"
    ): NetworkExchange
}