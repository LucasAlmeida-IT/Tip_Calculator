package com.example.tipcalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var edtbill: EditText
    private lateinit var customInput: EditText
    private lateinit var tvTotal: TextView
    private lateinit var tvBill: TextView
    private lateinit var tvTip: TextView
    private lateinit var splitValueTxt: TextView

    private var selectedValue: Float = 0f
    private var splitValue: Int = 1

    private lateinit var allButtons: List<Button>
    private lateinit var buttonValueMap: Map<Button, Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        edtbill = findViewById(R.id.BillTotal)
        customInput = findViewById(R.id.etCustomTip)
        tvTotal = findViewById(R.id.tv_Total)
        tvBill = findViewById(R.id.tv_Bill)
        tvTip = findViewById(R.id.tv_Tip)
        splitValueTxt = findViewById(R.id.SplitValue)

        val addBtn = findViewById<ImageButton>(R.id.AddBtn)
        val removeBtn = findViewById<ImageButton>(R.id.RemoveBtn)


        val btn10 = findViewById<Button>(R.id.Tips10)
        val btn15 = findViewById<Button>(R.id.Tips15)
        val btn20 = findViewById<Button>(R.id.Tips20)
        val btn25 = findViewById<Button>(R.id.Tips25)
        val btnCustom = findViewById<Button>(R.id.CustomTips)

        buttonValueMap = mapOf(
            btn10 to 10,
            btn15 to 15,
            btn20 to 20,
            btn25 to 25
        )

        allButtons = buttonValueMap.keys.toList() + btnCustom

        addBtn.setOnClickListener {

            splitValue++
            splitValueTxt.text = splitValue.toString()
            updateCalculations()

        }

        removeBtn.setOnClickListener {

            if (splitValue > 1) {

                splitValue--
                splitValueTxt.text = splitValue.toString()
                updateCalculations()

            }

        }

        buttonValueMap.forEach { (button, value) ->

            button.setOnClickListener {

                resetButtons()
                button.setBackgroundColor(ContextCompat.getColor(this,R.color.selected))
                button.setTextColor(ContextCompat.getColor(this,R.color.texts))
                customInput.visibility = View.GONE
                btnCustom.visibility = View.VISIBLE
                selectedValue = value.toFloat()
                updateCalculations()

            }

        }

        btnCustom.setOnClickListener {

            resetButtons()
            btnCustom.visibility = View.GONE
            customInput.visibility = View.VISIBLE
            customInput.doAfterTextChanged { text ->
                selectedValue = text.toString().toFloatOrNull() ?: 0f
                updateCalculations()
            }

        }

        edtbill.doAfterTextChanged {
            updateCalculations()
        }

    }

    private fun updateCalculations() {
        val bill = edtbill.text.toString().toFloatOrNull() ?: 0f
        val percentTip = selectedValue / 100
        val totalTip = percentTip * bill
        val tipPerPerson = totalTip / splitValue
        val billPerPerson = bill / splitValue
        val totalPerPerson = tipPerPerson + billPerPerson

        tvTotal.text = "€ %.2f".format(totalPerPerson)
        tvBill.text = "€ %.2f".format(billPerPerson)
        tvTip.text = "€ %.2f".format(tipPerPerson)
    }

    private fun resetButtons() {
        allButtons.forEach {
            it.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            it.setTextColor(ContextCompat.getColor(this,R.color.bars))
        }
    }
}