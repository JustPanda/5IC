package com.example.greg.safe;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class VictoryActivity extends Activity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.victory);
        setupButtons();
    }

    private void setupButtons()
    {
        findViewById(R.id.buttonRestart).setOnClickListener(view->{
            Intent i=new Intent(this, MainActivity.class);
            finish();
            startActivity(i);
        });

        findViewById(R.id.buttonExit).setOnClickListener(view->{
            finish();
        });
    }
}
