package com.example.baithuchanh3

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baithuchanh3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.btnAddExpense.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        adapter = ExpenseAdapter(emptyList()) { expense ->
            val intent = Intent(this, ExpenseDetailActivity::class.java).apply {
                putExtra("EXPENSE_ID", expense.id)
            }
            startActivity(intent)
        }

        binding.rvExpenses.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    private fun refreshData() {
        val expenses = ExpenseRepository.getAllExpenses()
        adapter.updateData(expenses)

        binding.tvTotalAmount.text = ExpenseRepository.formatCurrency(ExpenseRepository.getTotalAmount())

        if (expenses.isEmpty()) {
            binding.tvEmpty.visibility = View.VISIBLE
            binding.rvExpenses.visibility = View.GONE
        } else {
            binding.tvEmpty.visibility = View.GONE
            binding.rvExpenses.visibility = View.VISIBLE
        }
    }
}