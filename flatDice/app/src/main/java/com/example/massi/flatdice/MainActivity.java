package com.example.massi.flatdice;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;



public class MainActivity extends AppCompatActivity {
    Fragment nextFragment;
    float x1;
    float x2;
    float y1;
    float y2;
    final static float MIN_DISTANCE = 150.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentUno sk = new FragmentUno();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, sk);
        ft.commit();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();


                float deltaX = x2 - x1;
                float deltaY = y2 - y1;

                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                        if (x2 > x1)
                        {          // Left to Right swipe action
                            changeFragment("aDestra");
                        }
                        else
                        {                  // Right to left swipe action
                            changeFragment("aSinistra");
                        }
                }
                else
                {
                    if(Math.abs(deltaY) > MIN_DISTANCE)
                    {
                        if (y2 > y1)
                        {          // Left to Right swipe action
                            Toast.makeText(this, "in alto", Toast.LENGTH_SHORT).show();
                            changeFragment("inSu");
                        }
                        else
                        {                  // Right to left swipe action
                            Toast.makeText(this, "in basso", Toast.LENGTH_SHORT).show();
                            changeFragment("inGiu");
                        }
                    }
                    //Toast.makeText(this, "TAP", Toast.LENGTH_SHORT).show();
                }

                break;
        }

   /*     if(nextFragment instanceof FragmentUno)
            ((FragmentUno) nextFragment).setText("dio boiade dio");
        else
            ((FragmentUno)(nextFragment)).setText("vigliacco il signore");
*/
        return true;
    }

    public void changeFragment(String dove) {

        Random r = new Random();
        int qualeF = r.nextInt(7);
        FragmentManager fm = getFragmentManager();
        Fragment currentFragment = fm.findFragmentById(R.id.fragment);

        switch(qualeF)
        {
            case 1 : nextFragment = new FragmentUno(); break;
            case 2 : nextFragment = new FragmentDue(); break;
            case 3 : nextFragment = new FragmentTre(); break;
            case 4 : nextFragment = new FragmentQuattro(); break;
            case 5 : nextFragment = new FragmentCinque(); break;
            case 6 : nextFragment = new FragmentSei(); break;
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        switch(dove)
        {
            case "aDestra" : ft.setCustomAnimations(R.animator.from_left, R.animator.to_right); break;
            case "aSinistra" : ft.setCustomAnimations(R.animator.from_right, R.animator.to_left); break;
            case "inGiu" : ft.setCustomAnimations(R.animator.from_su, R.animator.to_giu); break;
            case "inSu" : ft.setCustomAnimations(R.animator.from_giu, R.animator.to_su); break;
        }

        ft.replace(R.id.fragment, nextFragment);
        ft.commit();

    }
}


