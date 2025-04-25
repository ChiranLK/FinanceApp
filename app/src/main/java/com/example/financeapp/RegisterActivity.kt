package com.example.financeapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.financeapp.LoginActivity
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {
    private lateinit var etNicPassport: TextInputEditText
    private lateinit var etAccountNumber: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText
    private lateinit var etPhone: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize UserManager
        UserManager.initialize(applicationContext)

        etNicPassport = findViewById(R.id.etNICPassport)
        etAccountNumber = findViewById(R.id.etAccountNumber)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        etPhone = findViewById(R.id.etPhone)


        findViewById<android.widget.TextView>(R.id.tvAlreadyRegistered).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        findViewById<android.widget.Button>(R.id.btnRegister).setOnClickListener {
            val nicPassport = etNicPassport.text.toString().trim()
            val accountNumber = etAccountNumber.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()
            val phone = etPhone.text.toString().trim()


            if (nicPassport.isEmpty() || accountNumber.isEmpty() || password.isEmpty() ||
                confirmPassword.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidPassword(password)) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidPhone(phone)) {
                Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidNicPassport(nicPassport)) {
                Toast.makeText(this, "Please enter a valid Email Address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (UserManager.registerUser(accountNumber, nicPassport, password, phone)) {
                Toast.makeText(this, "Registration successful! Please login.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Registration failed. User may already exist.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidPhone(phone: String): Boolean {
        return Patterns.PHONE.matcher(phone).matches() && phone.length == 10
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    private fun isValidNicPassport(nicPassport: String): Boolean {
        return "@" in nicPassport
    }

}
