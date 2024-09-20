package com.example.temp

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.GridLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.util.rangeTo

class MainActivity : ComponentActivity() {
    lateinit var fromRG: RadioGroup
    lateinit var toRG:RadioGroup
    lateinit var convertB:Button
    lateinit var display:EditText
    lateinit var resultD:TextView

    var from:String="C"
    var to:String="K"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.conversor)

        inicializarComponents()

        setListeners()

    }

    fun inicializarComponents(){
        fromRG = findViewById(R.id.from_group_RB)
        toRG = findViewById(R.id.to_group_RB)
        convertB = findViewById(R.id.convert)
        display = findViewById(R.id.value)
        resultD = findViewById(R.id.result)

        (fromRG.getChildAt(0) as RadioButton).isChecked=true
        (toRG.getChildAt(0) as RadioButton).isChecked=true
    }

    fun setListeners(){
        var number = display.text.toString().toDouble()

        setConvertButtonListener(convertB,number)

        for(i in 0 until fromRG.childCount){
            var radioButton = fromRG.getChildAt(i)
            var radioButtonId = resources.getResourceName(radioButton.id)
            var medida = radioButtonId.split('_')[1]
            setRadioFromClickListener(medida,radioButton as RadioButton)
        }

        for (i in 0 until toRG.childCount){
            var radioButton = toRG.getChildAt(i)
            var radioButtonId = resources.getResourceName(radioButton.id)
            var medida = radioButtonId.split('_')[1]
            setRadioToClickListener(medida,radioButton as RadioButton)
        }


    }

    fun setRadioFromClickListener(conv:String,radioButton: RadioButton){
        radioButton.setOnClickListener{
            this.from=conv
        }
    }

    fun setRadioToClickListener(conv:String,radioButton: RadioButton){
        radioButton.setOnClickListener{
            this.to=conv
        }
    }
    fun setConvertButtonListener(button:Button,number:Double){
        button.setOnClickListener{
            var result=calcular(number)
            resultD.setText("${result.toString()} $to")
        }
    }
    fun calcular(number:Double):Double{
        return when(from){
            "C"->{
                when(to){
                    "F"->(number*1.8)+32
                    "K"->number+273.15
                    else->number*1
                }
            }
            "F"->{
                when(to){
                    "C"->((number-32.0)*(5.0/9.0))
                    "K"->((number-32.0)*(5.0/9.0))+273.15
                    else->number*1
                }
            }
            "K"->{
                when(to){
                    "C"->number-273.15
                    "F"->((number-273.15)*1.8)+32
                    else->number*1
                }
            }
            else->number
        }
    }
}