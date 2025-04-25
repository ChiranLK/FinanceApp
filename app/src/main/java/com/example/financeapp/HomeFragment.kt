package com.example.financeapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var btnAddTransaction: Button
    private lateinit var btnViewBudget: Button
    private lateinit var btnSetBudget: Button
    private lateinit var btnSummary: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize buttons
        btnAddTransaction = view.findViewById(R.id.btnAddTransaction)
        btnViewBudget = view.findViewById(R.id.btnViewBudget)
        btnSetBudget = view.findViewById(R.id.btnSetBudget)
        btnSummary = view.findViewById(R.id.btnSummary)

        // Add Transaction button
        btnAddTransaction.setOnClickListener {
            startActivity(Intent(requireContext(), AddTransactionActivity::class.java))
        }

        // View Budget button
        btnViewBudget.setOnClickListener {
            val user = UserManager.getCurrentUser()
            if (user != null) {
                val budget = BudgetManager.getBudget(user.accountNumber)
                if (budget != null) {
                    startActivity(Intent(requireContext(), BudgetViewActivity::class.java))
                } else {
                    Toast.makeText(requireContext(), "No budget set yet", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
            }
        }

        // Set Budget button
        btnSetBudget.setOnClickListener {
            startActivity(Intent(requireContext(), BudgetActivity::class.java))
        }

        // Summary button
        btnSummary.setOnClickListener {
            startActivity(Intent(requireContext(), SummaryActivity::class.java))
        }
        return view
    }

    private fun showCurrentBudgetNotification() {
        val user = UserManager.getCurrentUser()
        if (user != null) {
            val budget = BudgetManager.getBudget(user.accountNumber)
            if (budget != null) {
                // Calculate remaining amount
                val transactions = TransactionManager.getTransactions(user.accountNumber)
                val spentAmount = transactions
                    .filter { it.type == "expense" }
                    .sumOf { it.amount }

                val incomeAmount = transactions
                    .filter { it.type == "income" }
                    .sumOf { it.amount }

                val formatter = NumberFormat.getCurrencyInstance(Locale("en", "LK"))
                formatter.currency = Currency.getInstance("LKR")

                // Create and show a custom dialog with the remaining amount
            }
        }
    }


    override fun onResume() {
        super.onResume()
        showCurrentBudgetNotification()
    }
}