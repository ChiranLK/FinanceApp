package com.example.financeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.financeapp.databinding.ActivityProfileBinding
import com.example.personalfinancetracker.LoginActivity

class ProfileActivity : AppCompatActivity() {

    private val TAG = "ProfileActivity"
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "ProfileActivity onCreate called")

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get user data from intent
        val nicPassport = intent.getStringExtra("NIC_PASSPORT")
        val accountNumber = intent.getStringExtra("ACCOUNT_NUMBER")
        val phone = intent.getStringExtra("PHONE")

        if (nicPassport.isNullOrEmpty() || accountNumber.isNullOrEmpty() || phone.isNullOrEmpty()) {
            // Try to load from UserManager if data is not passed via intent
            val user = UserManager.getCurrentUser()
            if (user != null) {
                binding.tvName.text = "NIC/Passport: ${user.nicPassport}"
                binding.tvEmail.text = "Account Number: ${user.accountNumber}"
                binding.tvPhone.text = "Phone: ${user.phone}"
            } else {
                Toast.makeText(this, "No user data available", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                return
            }
        } else {
            // Display data from intent
            binding.tvName.text = "NIC/Passport: $nicPassport"
            binding.tvEmail.text = "Account Number: $accountNumber"
            binding.tvPhone.text = "Phone: $phone"
        }

        // Logout functionality
        binding.btnLogout.setOnClickListener {
            UserManager.logout()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
