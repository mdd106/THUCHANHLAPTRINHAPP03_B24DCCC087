package com.example.baithuchanh3

import java.io.Serializable
import java.text.DecimalFormat
import java.util.UUID

data class Expense(
    val id: String = UUID.randomUUID().toString(),
    var name: String,
    var amount: Double,
    var category: String,
    var date: String,
    var note: String = ""
) : Serializable {
    fun getFormattedAmount(): String {
        val formatter = DecimalFormat("#,###")
        return "${formatter.format(amount).replace(",", ".")} đ"
    }
}