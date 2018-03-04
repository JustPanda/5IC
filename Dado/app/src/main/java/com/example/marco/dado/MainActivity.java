package com.example.marco.dado;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // portrait mode
        if(findViewById(R.id.frammento) != null){
            Frammento f1 = new Frammento();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.frammento, f1);
            ft.commit();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN | MotionEvent.ACTION_UP:
                cambiaFrammento();
                break;
        }
        return super.onTouchEvent(event);
    }

    public void cambiaFrammento() {
        // act only in portrait mode
        if (findViewById(R.id.frammento) != null) {
            FragmentManager fm = getFragmentManager();
            Fragment nextFragment = null;
            Fragment currentFragment = fm.findFragmentById(R.id.frammento);
            nextFragment = new Frammento();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frammento, nextFragment);
            ft.commit();
        }
    }
}
