package com.example.davide.flatdice;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity{

    float x1, x2, y1, y2;
    final static float MIN_DISTANCE = 150.0f;
    Display display = getWindowManager().getDefaultDisplay();

    Point size = new Point();
    int width = size.x;
    int height = size.y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // portrait mode
        if (findViewById(R.id.fragment) != null) {
            Face1 f1 = new Face1();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment, f1);
            ft.commit();
        }

    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                float deltaX = x2 - x1;
                float deltaY = y2 - y1;

                if (Math.abs(deltaX) > MIN_DISTANCE || Math.abs(deltaY) > MIN_DISTANCE) {

                    Random rn = new Random();
                    int number = rn.nextInt(6) + 1;

                    changeFragment(number, deltaX, deltaY);

                }
        }
        return super.onTouchEvent(event);
    }

    public void changeFragment(int number, float deltaX, float deltaY) {
        // act only in portrait mode
        if (findViewById(R.id.fragment) != null) {
            FragmentManager fm = getFragmentManager();
            Fragment nextFragment = null;
            //Fragment currentFragment = fm.findFragmentById(R.id.fragment);

            switch (number) {
                case 1:
                    nextFragment = new Face1();
                    break;
                case 2:
                    nextFragment = new Face2();
                    break;
                case 3:
                    nextFragment = new Face3();
                    break;
                case 4:
                    nextFragment = new Face4();
                    break;
                case 5:
                    nextFragment = new Face5();
                    break;
                case 6:
                    nextFragment = new Face6();
                    break;
            }
            /*if (currentFragment instanceof Face1) {
                nextFragment = new Face2();
            } else {
                nextFragment = new Face1();
            }*/

            FragmentTransaction ft = fm.beginTransaction();
            boolean sideSlide=false;
            boolean topSlide=false;

            if(deltaY<-100 || deltaY>100)
            {
                topSlide=true;
            }
            if(deltaX<-100||deltaX>100)
            {
                sideSlide=true;
            }
            // (enter, exit)
            if (deltaX > 100 && !topSlide) {
                ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
            }
            if(deltaX<-100 && !topSlide){
                ft.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left);
            }
            if (deltaY > 100 && !sideSlide) {
                ft.setCustomAnimations(R.animator.slide_in_down, R.animator.slide_out_up);
            }
            if(deltaY<-100 && !sideSlide){
                ft.setCustomAnimations(R.animator.slide_in_up, R.animator.slide_out_down);
            }
            if (deltaX > 100 && deltaY < -100) {

                ft.setCustomAnimations(R.animator.slide_in_bottom_left, R.animator.slide_out_top_right);
            }
            if (deltaX < -100 && deltaY < -100) {

                ft.setCustomAnimations(R.animator.slide_in_bottom_right, R.animator.slide_out_top_left);
            }
            if (deltaX > 100 && deltaY > 100) {

                ft.setCustomAnimations(R.animator.slide_in_top_left, R.animator.slide_out_bottom_right);
            }
            if (deltaX < -100 && deltaY > 100) {

                ft.setCustomAnimations(R.animator.slide_in_top_right, R.animator.slide_out_bottom_left);
            }
            //ft.setCustomAnimations(R.animator.slide_linear_left, R.animator.slide_linear_right);
            // ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.replace(R.id.fragment, nextFragment);
            ft.commit();
        } else {
            Toast.makeText(this, "LANDSCAPE", Toast.LENGTH_SHORT).show();
        }
    }
}
