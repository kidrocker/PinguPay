package com.penguin.pay.network

import com.penguin.pay.network.models.NetworkExchange

interface NetworkDataSource {

  suspend fun getExchangeRate():NetworkExchange

}