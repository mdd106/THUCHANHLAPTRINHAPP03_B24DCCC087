package com.example.baithuchanh3

import java.text.DecimalFormat

object ExpenseRepository {
    private val expenses = mutableListOf<Expense>()

    init {
        // Sample initial data matching requirements (2.2 Dữ liệu mẫu ban đầu):
        // 1. Ăn sáng | 30.000 đ | Ăn uống | 12/10/2026
        // 2. Đổ xăng | 50.000 đ | Đi lại | 12/10/2026
        // 3. Mua sách | 120.000 đ | Học tập | 13/10/2026
        expenses.add(
            Expense(
                name = "Ăn sáng",
                amount = 30000.0,
                category = "Ăn uống",
                date = "12/10/2026",
                note = "Ăn sáng cùng đồng nghiệp"
            )
        )
        expenses.add(
            Expense(
                name = "Đổ xăng",
                amount = 50000.0,
                category = "Đi lại",
                date = "12/10/2026",
                note = "Xăng xe máy"
            )
        )
        expenses.add(
            Expense(
                name = "Mua sách",
                amount = 120000.0,
                category = "Học tập",
                date = "13/10/2026",
                note = "Sách Lập trình Android"
            )
        )
    }

    fun getAllExpenses(): List<Expense> = expenses.toList()

    fun addExpense(expense: Expense) {
        expenses.add(0, expense)
    }

    fun deleteExpense(id: String) {
        expenses.removeAll { it.id == id }
    }

    fun getExpenseById(id: String): Expense? {
        return expenses.find { it.id == id }
    }

    fun getTotalAmount(): Double {
        return expenses.sumOf { it.amount }
    }

    fun formatCurrency(amount: Double): String {
        val formatter = DecimalFormat("#,###")
        return "${formatter.format(amount).replace(",", ".")} đ"
    }
}