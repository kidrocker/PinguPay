package com.penguin.pay.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkRate(
    @SerialName("KES")
    val kenyan:Float,

    @SerialName("UGX")
    val ugandan:Float,

    @SerialName("NGN")
    val nigerian:Float,

    @SerialName("TZS")
    val tanzanian:Float
)
