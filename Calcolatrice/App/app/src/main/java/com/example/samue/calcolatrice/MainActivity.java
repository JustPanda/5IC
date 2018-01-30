package com.example.samue.calcolatrice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.View;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView Calculator;
    TextView Res;
    String operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calculator = (GridView)findViewById(R.id.Buttons);
        Res = (TextView) findViewById(R.id.Cont);

        String[] numbers = new String[]{"1", "2", "3", "CE", "4", "5", "6", "+", "7", "8", "9", "-", "=", "0", "*", "/" };

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, numbers);
        Calculator.setAdapter(adapter);

        Calculator.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Selection = parent.getItemAtPosition(position).toString();

                if(!Selection.equals("+") && !Selection.equals("-") && !Selection.equals("*") && !Selection.equals("/") && !Selection.equals("CE") && !Selection.equals("="))
                {
                    if(Res.getText().equals(""))
                    {
                        Res.setText(Selection);
                    }
                    else
                    {
                        Res.setText(Res.getText() + Selection);
                    }
                }
                else
                {
                    float result = 0;
                    switch (Selection)
                    {
                        case "CE":
                            result = 0;
                            Res.setText("");
                            break;
                        case "+":
                            operator = Selection;
                            Res.setText(Res.getText() + Selection);
                            break;
                        case "-":
                            operator = Selection;
                            Res.setText(Res.getText() + Selection);
                            break;
                        case "*":
                            operator = Selection;
                            Res.setText(Res.getText() + Selection);
                            break;
                        case "/":
                            operator = Selection;
                            Res.setText(Res.getText() + Selection);
                            break;
                        case "=":
                            switch(operator)
                            {
                                case "+":
                                    result = parseInt("" + Res.getText().charAt(0)) + parseInt("" + Res.getText().charAt(Res.getText().length() - 1));
                                    break;
                                case "-":
                                    result = parseInt("" + Res.getText().charAt(0)) - parseInt("" + Res.getText().charAt(Res.getText().length() - 1));
                                    break;
                                case "*":
                                    result = parseInt("" + Res.getText().charAt(0)) * parseInt("" + Res.getText().charAt(Res.getText().length() - 1));
                                    break;
                                case "/":
                                    result = parseInt("" + Res.getText().charAt(0)) / parseInt("" + Res.getText().charAt(Res.getText().length() - 1));
                                    break;
                            }
                            Res.setText(String.valueOf(result));
                            result = 0;
                            break;
                    }
                }
            }
        });
    }
}
