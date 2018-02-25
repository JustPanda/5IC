package com.example.manue.dice;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    float x1,x2;
    final static float MIN_DISTANCE = 150.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // portrait mode
        if(findViewById(R.id.fragment) != null){
            FragmentDice f1 = new FragmentDice();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment, f1);
            ft.commit();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    // Left to Right swipe action
                    if (x2 > x1)
                    {
                        changeFragmentToLeft();
                    }

                    // Right to left swipe action
                    else
                    {
                        changeFragmentToRight();
                        //Toast.makeText(this, "SWIPE", Toast.LENGTH_SHORT).show ();
                    }
                }
                else {
                    Toast.makeText(this, "TAP", Toast.LENGTH_SHORT).show ();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void changeFragmentToLeft() {
        // act only in portrait mode
        if(findViewById(R.id.fragment) != null) {
            FragmentManager fm = getFragmentManager();
            Fragment nextFragment = null;
            Fragment currentFragment = fm.findFragmentById(R.id.fragment);
            // if (currentFragment instanceof FragmentCoin) {
            nextFragment = new FragmentDice();
            // } else {
            //    nextFragment = new FragmentDice();
            // }

            FragmentTransaction ft = fm.beginTransaction();
            // (enter, exit)
            ft.setCustomAnimations(R.animator.slide_linear_left, R.animator.slide_linear_right);
            // ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.replace(R.id.fragment, nextFragment);
            ft.commit();
        }
    }

    public void changeFragmentToRight() {
        // act only in portrait mode
        if(findViewById(R.id.fragment) != null) {
            FragmentManager fm = getFragmentManager();
            Fragment nextFragment = null;
            Fragment currentFragment = fm.findFragmentById(R.id.fragment);
            // if (currentFragment instanceof FragmentCoin) {
            nextFragment = new FragmentCoin();
            // } else {
            //    nextFragment = new FragmentDice();
            // }

            FragmentTransaction ft = fm.beginTransaction();
            // (enter, exit)
            ft.setCustomAnimations(R.animator.slide_linear_left, R.animator.slide_linear_right);
            // ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.replace(R.id.fragment, nextFragment);
            ft.commit();
        }

    }
}
