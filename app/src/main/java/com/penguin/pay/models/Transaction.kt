package com.penguin.pay.models

data class Transaction (
    val recipientName:String,
    val recipientPhone:String,
    val amount:String,
    val recipientCountryCode:String
)