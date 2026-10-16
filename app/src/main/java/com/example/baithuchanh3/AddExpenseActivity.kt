package com.example.baithuchanh3

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.baithuchanh3.databinding.ActivityAddExpenseBinding
import java.util.Calendar

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddExpenseBinding

    private val categories = arrayOf("Ăn uống", "Đi lại", "Học tập", "Giải trí", "Khác")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupSpinner()
        setupInputListeners()

        binding.btnSaveExpense.setOnClickListener {
            saveExpense()
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categories
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCategory.adapter = adapter
    }

    private fun setupInputListeners() {
        // 1. Tên khoản chi listener
        binding.etExpenseName.doOnTextChanged { text, _, _, _ ->
            val input = text?.toString()?.trim().orEmpty()
            if (input.isNotEmpty()) {
                binding.tilExpenseName.error = null
                binding.tilExpenseName.helperText = getString(R.string.helper_valid_name)
            } else {
                binding.tilExpenseName.helperText = null
            }
        }

        // 2. Số tiền listener
        binding.etExpenseAmount.doOnTextChanged { text, _, _, _ ->
            val input = text?.toString()?.trim().orEmpty()
            if (input.isEmpty()) {
                binding.tilExpenseAmount.helperText = null
            } else if (input.any { it.isLetter() } || (input.toDoubleOrNull() == null)) {
                binding.tilExpenseAmount.error = getString(R.string.error_amount_contains_letters)
                binding.tilExpenseAmount.helperText = null
            } else {
                val amount = input.toDouble()
                if (amount <= 0) {
                    binding.tilExpenseAmount.error = getString(R.string.error_invalid_amount)
                    binding.tilExpenseAmount.helperText = null
                } else {
                    binding.tilExpenseAmount.error = null
                    binding.tilExpenseAmount.helperText = getString(R.string.helper_valid_amount)
                }
            }
        }

        // 3. Danh mục Spinner listener
        binding.spCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selected = categories.getOrNull(position) ?: categories[0]
                binding.tvCategorySuccess.text = getString(R.string.helper_valid_category_with_name, selected)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // 4. Ngày chi tiêu DatePicker listener
        updateDateSuccessText()
        val calendar = Calendar.getInstance()
        binding.dpExpenseDate.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ) { _, year, month, day ->
            val dateStr = String.format(java.util.Locale.getDefault(), "%02d/%02d/%d", day, month + 1, year)
            binding.tvDateSuccess.text = getString(R.string.helper_valid_date, dateStr)
        }

        // 5. Ghi chú listener
        binding.etExpenseNote.doOnTextChanged { text, _, _, _ ->
            val input = text?.toString()?.trim().orEmpty()
            if (input.isNotEmpty()) {
                binding.tilExpenseNote.helperText = getString(R.string.helper_valid_note)
            } else {
                binding.tilExpenseNote.helperText = getString(R.string.helper_optional_note)
            }
        }
    }

    private fun updateDateSuccessText() {
        val day = binding.dpExpenseDate.dayOfMonth
        val month = binding.dpExpenseDate.month + 1
        val year = binding.dpExpenseDate.year
        val dateStr = String.format(java.util.Locale.getDefault(), "%02d/%02d/%d", day, month, year)
        binding.tvDateSuccess.text = getString(R.string.helper_valid_date, dateStr)
    }

    private fun saveExpense() {
        val name = binding.etExpenseName.text?.toString()?.trim().orEmpty()
        val amountStr = binding.etExpenseAmount.text?.toString()?.trim().orEmpty()
        val note = binding.etExpenseNote.text?.toString()?.trim().orEmpty()

        // Case 1: Empty Expense Name
        if (name.isEmpty()) {
            val errorMsg = getString(R.string.error_empty_name)
            binding.tilExpenseName.error = errorMsg
            binding.tilExpenseName.helperText = null
            binding.etExpenseName.requestFocus()
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
            return
        }

        // Case 2: Empty Amount
        if (amountStr.isEmpty()) {
            val errorMsg = getString(R.string.error_empty_amount)
            binding.tilExpenseAmount.error = errorMsg
            binding.tilExpenseAmount.helperText = null
            binding.etExpenseAmount.requestFocus()
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
            return
        }

        // Case 3: Amount contains letters or invalid characters
        if (amountStr.any { it.isLetter() } || (amountStr.toDoubleOrNull() == null)) {
            val errorMsg = getString(R.string.error_amount_contains_letters)
            binding.tilExpenseAmount.error = errorMsg
            binding.tilExpenseAmount.helperText = null
            binding.etExpenseAmount.requestFocus()
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
            return
        }

        // Case 4: Non-positive amount (0 or negative)
        val amount = amountStr.toDouble()
        if (amount <= 0) {
            val errorMsg = getString(R.string.error_invalid_amount)
            binding.tilExpenseAmount.error = errorMsg
            binding.tilExpenseAmount.helperText = null
            binding.etExpenseAmount.requestFocus()
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
            return
        }

        val category = binding.spCategory.selectedItem?.toString() ?: categories[0]

        val day = binding.dpExpenseDate.dayOfMonth
        val month = binding.dpExpenseDate.month + 1
        val year = binding.dpExpenseDate.year
        val date = String.format(java.util.Locale.getDefault(), "%02d/%02d/%d", day, month, year)

        val newExpense = Expense(
            name = name,
            amount = amount,
            category = category,
            date = date,
            note = note
        )

        ExpenseRepository.addExpense(newExpense)

        Toast.makeText(this, getString(R.string.toast_add_success), Toast.LENGTH_SHORT).show()
        finish()
    }
}