package com.example.ivaca

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val types = resources.getStringArray(R.array.tipos)
        val origen: Spinner = findViewById(R.id.sp_origen)
        val calculate: Button = findViewById(R.id.btnCalc)
        val amount: EditText = findViewById(R.id.ed_amount)
        val resultTextView: TextView = findViewById(R.id.tv_result)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, types
        )
        origen.adapter = adapter

        origen.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Toast.makeText(this@MainActivity, "Seleccionado: " + types[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No action needed
            }
        }

        calculate.setOnClickListener {
            calculateTax(amount, origen.selectedItem.toString(), resultTextView)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateTax(amount: EditText, origin: String, resultTextView: TextView) {
        val value = amount.text.toString()
        val doubleValue = value.toDoubleOrNull()

        if (doubleValue != null) {
            val taxRate = if (origin == "Fronterizo") 1.10 else 1.16
            val totalWithTax = doubleValue * taxRate
            resultTextView.text = "Total con IVA ($taxRate): $totalWithTax"
        } else {
            resultTextView.text = "Por favor, ingresa un valor válido."
        }
    }
}