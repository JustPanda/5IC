package com.example.greg.safe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final static int LIMIT_FOR_HINT=3;

    private static int[] hintCount;
    private static String gameNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupButtonsListeners();
        startGame();
    }

    private void setupButtonsListeners()
    {
        TextView label=findViewById(R.id.resultLabel);

        label.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                checkResult(label);
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        findViewById(R.id.buttonClear).setOnClickListener(view->{
            label.setText("");
        });

        findViewById(R.id.buttonDel).setOnClickListener(view->{
            if(!label.getText().toString().equals(""))
            {
                String currentText=label.getText().toString();
                label.setText(currentText.substring(0, currentText.length()-1));
            }
        });

        findViewById(R.id.button0).setOnClickListener(view->{
            if(label.getText().toString().length()<4)
                label.setText(label.getText().toString()+"0");
            
        });

        findViewById(R.id.button1).setOnClickListener(view->{
            if(label.getText().toString().length()<4)
                label.setText(label.getText().toString()+"1");

        });

        findViewById(R.id.button2).setOnClickListener(view->{
            if(label.getText().toString().length()<4)
                label.setText(label.getText().toString()+"2");

        });

        findViewById(R.id.button3).setOnClickListener(view->{
            if(label.getText().toString().length()<4)
                label.setText(label.getText().toString()+"3");

        });

        findViewById(R.id.button4).setOnClickListener(view->{
            if(label.getText().toString().length()<4)
                label.setText(label.getText().toString()+"4");


        });

        findViewById(R.id.button5).setOnClickListener(view->{
            if(label.getText().toString().length()<4)
                label.setText(label.getText().toString()+"5");

        });

        findViewById(R.id.button6).setOnClickListener(view->{
            if(label.getText().toString().length()<4)
                label.setText(label.getText().toString()+"6");

        });

        findViewById(R.id.button7).setOnClickListener(view->{
            if(label.getText().toString().length()<4)
                label.setText(label.getText().toString()+"7");

        });

        findViewById(R.id.button8).setOnClickListener(view->{
            if(label.getText().toString().length()<4)
                label.setText(label.getText().toString()+"8");

        });

        findViewById(R.id.button9).setOnClickListener(view->{
            if(label.getText().toString().length()<4)
                label.setText(label.getText().toString()+"9");

        });
    }

    private void hint()
    {
        if(++hintCount[0]==LIMIT_FOR_HINT)
        {
            if(hintCount[1]<gameNumber.length())
            {
                String msg=getResources().getString(R.string.shortHint)+" "+String.valueOf(gameNumber.charAt(hintCount[1]++));
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                hintCount[0]=0;
                return;
            }
            else
            {
                hintCount[0]=-1;
            }
        }

        if(hintCount[0]==-1)
        {
            String msg=getResources().getString(R.string.longHint)+" "+gameNumber;
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
    }

    private void checkResult(TextView l)
    {
        if(l.getText().toString().length()==gameNumber.length())
        {
            hint();
            if (l.getText().toString().equals(gameNumber)) {
                l.setTextColor(getResources().getColor(R.color.green, null));
                l.setBackground(getResources().getDrawable(R.drawable.green_rectangle, null));
                Intent i = new Intent(this, VictoryActivity.class);
                finish();
                startActivity(i);
            }
        }
    }

    private void startGame()
    {
        gameNumber=String.valueOf((int)(Math.random()*10000+Math.random()*1000+Math.random()*100+Math.random()*10));
        hintCount=new int[]{0,0};
        //gameNumber=String.valueOf(1234);
    }
}
