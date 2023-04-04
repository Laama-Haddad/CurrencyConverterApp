package com.example.currencyconverter

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    private val egyptianPound = "Egyptian Pound"
    private val americanDollar = "American Dollar"
    private val AED = "AED"
    private val GBP = "GBP"
    private val Euro = "Euro"

    val values = mapOf(
        americanDollar to 1.0, egyptianPound to 15.73, AED to 3.67, GBP to 0.74, Euro to 0.88
    )

    lateinit var toDropDownMenu: AutoCompleteTextView
    lateinit var fromDropDownMenu: AutoCompleteTextView
    lateinit var convertBtn: Button
    lateinit var amountET: TextInputEditText
    lateinit var resultET: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        populateDropDownMenu()
        amountET.addTextChangedListener {
            calculateResult()
        }
        fromDropDownMenu.setOnItemClickListener { adapterView, view, i, l ->
            calculateResult()
        }
        toDropDownMenu.setOnItemClickListener { parent, view, position, id ->
            calculateResult()
        }
    }

    private fun populateDropDownMenu() {
        val listOfCountry = listOf(egyptianPound, americanDollar, AED, GBP, Euro)
        val adapter = ArrayAdapter(this, R.layout.drop_down_list_item, listOfCountry)
        toDropDownMenu.setAdapter(adapter)
        fromDropDownMenu.setAdapter(adapter)
    }

    private fun initializeViews() {
        convertBtn = findViewById(R.id.convert_button)
        amountET = findViewById(R.id.amount_edit_text)
        resultET = findViewById(R.id.result_edit_text)
        fromDropDownMenu = findViewById(R.id.from_currency_menu)
        toDropDownMenu = findViewById(R.id.to_currency_menu)
    }

    private fun calculateResult() {
        if (amountET.text.toString().isNotEmpty()) {
            val amount = amountET.text.toString().toDouble()
            val toValue = values[toDropDownMenu.text.toString()]
            val fromValue = values[fromDropDownMenu.text.toString()]
            val result = amount.times(toValue!!.div(fromValue!!))
            val formattedResult = String.format("%.2f", result)
            resultET.setText(formattedResult.toString())
        } else {
            amountET.error = getString(R.string.error)
        }
    }
}