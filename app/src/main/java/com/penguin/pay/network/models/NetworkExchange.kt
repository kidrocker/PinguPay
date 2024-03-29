package com.penguin.pay.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkExchange(

    @SerialName("disclaimer")
    val disclaimer: String,

    @SerialName("license")
    val license: String,

    @SerialName("timestamp")
    val timestamp: Long,

    @SerialName("base")
    val base: String,

    @SerialName("rates")
    val rates: Map<String, Float>
)
