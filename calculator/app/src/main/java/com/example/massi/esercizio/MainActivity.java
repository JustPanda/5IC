package com.example.massi.esercizio;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class MainActivity extends Activity {

    List<String> operazioni = new ArrayList<>();
    boolean vai = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) this.findViewById(R.id.griglia);
        String[] dati = new String[]{
                "1","2","3","+","4","5","6","x","7","8","9","-","=","0","CE","/"
        };
        ListAdapter adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                dati
        );
        gridview.setBackgroundColor(Color.RED);
        gridview.setAdapter(adapter);
        gridview.setVerticalSpacing(30);

        final TextView tv = (TextView) findViewById(R.id.textview);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();


                if(!selectedItem.equals("+")&&!selectedItem.equals("x")&&!selectedItem.equals("-")&&!selectedItem.equals("CE")&&!selectedItem.equals("/")&&!selectedItem.equals("="))
                {
                    if(tv.getText().equals("0") || !vai){
                        tv.setText(selectedItem);
                        vai = true;
                    }
                    else {
                        tv.setText(tv.getText()+selectedItem);
                    }
                }
                else
                {
                    int a = 0;

                    if(selectedItem.equals("CE"))
                    {
                        operazioni.clear();
                        a=0;
                        tv.setText(R.string.valore);
                    }
                   if(selectedItem.equals("+"))
                    {
                        operazioni.add((String)tv.getText());
                        operazioni.add("+");
                        tv.setText(R.string.valore);
                    }
                    if(selectedItem.equals("-"))
                    {
                        operazioni.add((String)tv.getText());
                        operazioni.add("-");
                        tv.setText(R.string.valore);
                    }
                    if(selectedItem.equals("x"))
                    {
                        operazioni.add((String)tv.getText());
                        operazioni.add("*");
                        tv.setText(R.string.valore);
                    }
                    if(selectedItem.equals("/"))
                    {
                        operazioni.add((String)tv.getText());
                        operazioni.add("/");
                        tv.setText(R.string.valore);
                    }

                    if(selectedItem.equals("="))
                    {
                        operazioni.add((String)tv.getText());
                        switch (operazioni.get(1))
                        {
                            case "+" :a = parseInt(operazioni.get(0))+parseInt(operazioni.get(2)); break;
                            case "-" :a = parseInt(operazioni.get(0))-parseInt(operazioni.get(2)); break;
                            case "/" :a = parseInt(operazioni.get(0))/parseInt(operazioni.get(2)); break;
                            case "*" :a = parseInt(operazioni.get(0))*parseInt(operazioni.get(2)); break;
                        }
                        tv.setText(a+"");
                        operazioni.clear();
                        a=0;
                        vai = false;
                    }
                }
            }
        });

    }
}
