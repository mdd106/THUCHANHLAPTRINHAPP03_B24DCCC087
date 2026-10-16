package com.example.baithuchanh3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baithuchanh3.databinding.ItemExpenseBinding

class ExpenseAdapter(
    private var expenseList: List<Expense>,
    private val onItemClick: (Expense) -> Unit
) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(val binding: ItemExpenseBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ItemExpenseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenseList[position]
        holder.binding.apply {
            tvExpenseName.text = expense.name
            tvExpenseAmount.text = expense.getFormattedAmount()
            tvExpenseCategory.text = expense.category
            tvExpenseDate.text = expense.date

            root.setOnClickListener {
                onItemClick(expense)
            }
        }
    }

    override fun getItemCount(): Int = expenseList.size

    fun updateData(newList: List<Expense>) {
        expenseList = newList
        notifyDataSetChanged()
    }
}