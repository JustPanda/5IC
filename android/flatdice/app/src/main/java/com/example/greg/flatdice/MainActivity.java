package com.example.greg.flatdice;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity
{
    private float x1, x2, y1, y2;
    private boolean fingerDown=false;
    private final static float MIN_DISTANCE = 150.0f;
    private final static DiceFragment[] diceFaces=new DiceFragment[6];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartingFragment starting=new StartingFragment();
        FragmentTransaction ft=getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, starting);
        ft.commit();
        loadDiceFaces();
    }

    private void loadDiceFaces()
    {
        for(int i=1; i<=6; i++)
            diceFaces[i-1]=DiceFragment.newInstance(i);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction()==MotionEvent.ACTION_DOWN && !fingerDown)
        {
            x1=event.getX();
            y1=event.getY();
            fingerDown=true;
        }
        else if(event.getAction()==MotionEvent.ACTION_UP)
        {
            x2=event.getX();
            y2=event.getY();
            float dx=x2-x1, dy=y2-y1;
            if(Math.abs(dx)>MIN_DISTANCE || Math.abs(dy)>MIN_DISTANCE)
                rollDice();
            x1=x2=y1=y2=0;
            fingerDown=false;
        }

        return super.onTouchEvent(event);
    }

    private void rollDice()
    {
        int[] animation=setAnimation();
        FragmentTransaction ft=getFragmentManager().beginTransaction();
        int rand=(int)((Math.random()*6));
        ft.setCustomAnimations(animation[0], animation[1]);
        ft.replace(R.id.fragment, diceFaces[rand]);
        ft.commit();
    }

    private int[] setAnimation()
    {
        float dx=Math.abs(x2-x1), dy=Math.abs(y2-y1);
        if(dx>dy) //horizontal swipe
        {
            if(x2>x1) //left to right
            {
                return new int[]{R.animator.slide_in_left, R.animator.slide_out_right};
            }
            else    //right to left
            {
                return new int[]{R.animator.slide_in_right, R.animator.slide_out_left};
            }
        }
        else    //vertical swipe
        {
            if(y2>y1)   //up to down
            {
                return new int[]{R.animator.slide_in_bottom, R.animator.slide_out_top};
            }
            else        //down to up
            {
                return new int[]{R.animator.slide_in_top, R.animator.slide_out_bottom};
            }
        }
    }
}
