package com.example.baithuchanh3

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.baithuchanh3.databinding.ActivityExpenseDetailBinding

class ExpenseDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExpenseDetailBinding
    private var expenseId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpenseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        expenseId = intent.getStringExtra("EXPENSE_ID")
        val expense = expenseId?.let { ExpenseRepository.getExpenseById(it) }

        if (expense == null) {
            finish()
            return
        }

        setupToolbar()
        displayExpenseDetail(expense)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnDelete.setOnClickListener {
            deleteExpense()
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun displayExpenseDetail(expense: Expense) {
        binding.tvDetailName.text = expense.name
        binding.tvDetailAmount.text = expense.getFormattedAmount()
        binding.tvDetailCategory.text = expense.category
        binding.tvDetailDate.text = expense.date
        binding.tvDetailNote.text = expense.note.ifEmpty { getString(R.string.no_note) }
    }

    private fun deleteExpense() {
        expenseId?.let { id ->
            ExpenseRepository.deleteExpense(id)
            Toast.makeText(this, getString(R.string.toast_delete_success), Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}