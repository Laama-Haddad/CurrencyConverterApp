package com.example.currencyconverter

import android.content.Intent
import android.os.Bundle
import android.widget.*
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
    lateinit var toolBar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        populateDropDownMenu()
        toolBar.inflateMenu(R.menu.options_menu)
        toolBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.share_action -> {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    val message =
                        "${amountET.text.toString()} ${fromDropDownMenu.text}  " +
                                "is equal to ${resultET.text.toString()} ${toDropDownMenu.text}"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, message)
                    if (shareIntent.resolveActivity(packageManager) != null) {
                        startActivity(shareIntent)
                    } else {
                        Toast.makeText(
                            this, "No Activity found to handle this intent", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                else -> {}
            }
            true
        }

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
        toolBar = findViewById(R.id.toolbar)
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

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//    menuInflater.inflate(R.menu.options_menu,menu)
//        return true;
//    }
//
//     override fun onOptionsItemSelected(item: MenuItem): Boolean {
//         when(item.itemId){
//             R.id.share_action-> Toast.makeText(this,"Share", Toast.LENGTH_SHORT).show()
//             R.id.settings_action-> Toast.makeText(this,"Settings", Toast.LENGTH_SHORT).show()
//             R.id.contact_us_action-> Toast.makeText(this,"Contact Us", Toast.LENGTH_SHORT).show()
//         }
//         return super.onOptionsItemSelected(item)
//     }
}