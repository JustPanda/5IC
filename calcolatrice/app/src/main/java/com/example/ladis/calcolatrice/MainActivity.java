package com.example.ladis.calcolatrice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    float n1,n2;
    boolean add, sub, per, div;

    TextView txtv2;
    TextView txtv1;

    Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, buttoncanc, buttonplus, buttonminus,
            buttonpoint, buttonper, buttondiv, buttonequals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        buttoncanc = (Button) findViewById(R.id.buttoncanc);
        buttonplus = (Button) findViewById(R.id.buttonplus);
        buttonminus = (Button) findViewById(R.id.buttonminus);
        buttonpoint = (Button) findViewById(R.id.buttonpoint);
        buttonper = (Button) findViewById(R.id.buttonper);
        buttondiv = (Button) findViewById(R.id.buttondiv);
        buttonequals = (Button) findViewById(R.id.buttonequals);
        txtv1 = (TextView) findViewById(R.id.txtv1);
        txtv2 = (TextView) findViewById(R.id.txtv2);

        button0.setOnClickListener((v)-> {txtv2.setText(txtv2.getText()+"0");});
        button1.setOnClickListener((v)-> {txtv2.setText(txtv2.getText()+"1");});
        button2.setOnClickListener((v)-> {txtv2.setText(txtv2.getText()+"2");});
        button3.setOnClickListener((v)-> {txtv2.setText(txtv2.getText()+"3");});
        button4.setOnClickListener((v)-> {txtv2.setText(txtv2.getText()+"4");});
        button5.setOnClickListener((v)-> {txtv2.setText(txtv2.getText()+"5");});
        button6.setOnClickListener((v)-> {txtv2.setText(txtv2.getText()+"6");});
        button7.setOnClickListener((v)-> {txtv2.setText(txtv2.getText()+"7");});
        button8.setOnClickListener((v)-> {txtv2.setText(txtv2.getText()+"8");});
        button9.setOnClickListener((v)-> {txtv2.setText(txtv2.getText()+"9");});
        buttoncanc.setOnClickListener((v)-> {txtv2.setText("");});
        buttonplus.setOnClickListener((v)-> {
            if(txtv2!=null){
                n1= Float.parseFloat(txtv2.getText()+"");
                txtv1.setText(txtv2.getText()+"+");
                add=true;
                sub=per=div=false;
                txtv2.setText("");
            }
        });
        buttonminus.setOnClickListener((v)-> {
            if(txtv2!=null){
                n1= Float.parseFloat(txtv2.getText()+"");
                txtv1.setText(txtv2.getText()+"-");
                sub=true;
                add=per=div=false;
                txtv2.setText("");
            }
        });
        buttonpoint.setOnClickListener((v)-> {txtv2.setText(txtv2.getText()+".");});
        buttonper.setOnClickListener((v)-> {
            if(txtv2!=null){
                n1= Float.parseFloat(txtv2.getText()+"");
                txtv1.setText(txtv2.getText()+"x");
                per=true;
                add=sub=div=false;
                txtv2.setText("");
            }
        });
        buttondiv.setOnClickListener((v)-> {
            if(txtv2!=null){
                n1= Float.parseFloat(txtv2.getText()+"");
                txtv1.setText(txtv2.getText()+"/");
                div=true;
                add=per=sub=false;
                txtv2.setText("");
            }
        });
        buttonequals.setOnClickListener((v)-> {
            if(txtv2!=null) {
                n2 = Float.parseFloat(txtv2.getText() + "");
                txtv1.setText(txtv1.getText()+""+txtv2.getText());
                if (add) {
                    add=false;
                    txtv2.setText(n1+n2+"");
                }
                if (sub){
                    sub=false;
                    txtv2.setText(n1-n2+"");
                }
                if(per) {
                    per=false;
                    txtv2.setText(n1*n2+"");
                }
                if(div){
                    div=false;
                    txtv2.setText(n1/n2+"");
                }
            }
        });

    }
}
