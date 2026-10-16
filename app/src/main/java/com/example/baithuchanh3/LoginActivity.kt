package com.example.baithuchanh3

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.baithuchanh3.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            handleLogin()
        }
    }

    private fun handleLogin() {
        val username = binding.etUsername.text?.toString()?.trim().orEmpty()
        val password = binding.etPassword.text?.toString()?.trim().orEmpty()

        if (username.isEmpty() || password.isEmpty()) {
            binding.tvError.text = getString(R.string.error_login_empty)
            binding.tvError.visibility = View.VISIBLE
            return
        }

        // Hardcoded account comparison as required in Section 2.1:
        // admin / 123456
        if ((username == "admin") && (password == "123456")) {
            binding.tvError.visibility = View.GONE
            Toast.makeText(this, getString(R.string.toast_login_success), Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Prevent returning to Login screen when pressing Back
        } else {
            binding.tvError.text = getString(R.string.error_login_failed)
            binding.tvError.visibility = View.VISIBLE
        }
    }
}