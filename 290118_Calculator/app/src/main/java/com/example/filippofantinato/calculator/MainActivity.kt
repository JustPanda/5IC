package com.example.filippofantinato.calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var error: Boolean=false
        val buttonsId: IntArray = intArrayOf(R.id.one, R.id.two, R.id.three,
                    R.id.four, R.id.five, R.id.six,
                    R.id.seven, R.id.eight, R.id.nine,
                    R.id.dot, R.id.zero, R.id.plus, R.id.minus,
                    R.id.mol, R.id.div)
        val inputText: TextView = findViewById<TextView>(R.id.inputText)
        val delete: Button = findViewById(R.id.delete)
        val equal: Button = findViewById(R.id.equal)
        delete.setOnClickListener({view ->
            inputText.text=""
        })
        equal.setOnClickListener({view ->
            val scanner = Scanner(inputText.text.toString())
            Log.d("Main", inputText.text.toString())
            try{
                val op1: Int = scanner.nextInt()
                val op2: String = scanner.next()
                val op3: Int = scanner.nextInt()
                var output: Int? = null
                when(op2)
                {
                    "+" -> {
                        output=op1+op3
                    }
                    "-" -> {
                        output=op1-op3
                    }
                    "*" -> {
                        output=op1*op3
                    }
                    "/" -> {
                        output=op1/op3
                    }
                }
                inputText.text=output.toString()
            }catch(e: InputMismatchException){
                inputText.text="Error"
                error=true
            }
        })
        for(buttonId in buttonsId)
        {
            findViewById<Button>(buttonId).setOnClickListener({view ->
                var actualText: String
                val pressedText: String = (view as Button).text.toString()
                var space: String = if (pressedText.equals("+")||pressedText.equals("-")||pressedText.equals("*")||pressedText.equals("/")) " " else ""
                if(error)
                {
                    actualText=""
                    error=false
                }else{
                    actualText=inputText.text.toString()
                }
                inputText.text=actualText+space+pressedText+space
            })
        }
    }
}
