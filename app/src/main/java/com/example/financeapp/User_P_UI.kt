package com.example.financeapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class User_P_UI : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        @SuppressLint("MissingInflatedId")
        fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_user_pui)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
            lateinit var sharedPreferences: SharedPreferences

            sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

            val firstName = sharedPreferences.getString("firstName", "Not Available")
            val lastName = sharedPreferences.getString("lastName", "Not Available")
            val email = sharedPreferences.getString("email", "Not Available")
            val password = sharedPreferences.getString("password", "Not Available")

            val userNameText: TextView = findViewById(R.id.profileFirstName)
            val userLastText: TextView = findViewById(R.id.profileLastName)
            val userEmailText: TextView = findViewById(R.id.profileEmail)
            val logoutButton: Button = findViewById(R.id.logoutButton)

            userNameText.text = firstName
            userLastText.text = lastName
            userEmailText.text = email

            logoutButton.setOnClickListener {
                val editor = sharedPreferences.edit()
                editor.clear()
                editor.apply()

                val intent = Intent(this, LoginUI::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}

