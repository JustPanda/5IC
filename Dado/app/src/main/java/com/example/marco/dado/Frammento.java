package com.example.marco.dado;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

/**
 * Created by manue on 04/03/2018.
 */

public class Frammento extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        Random r = new Random();
        View v = inflater.inflate(R.layout.frammento, container, false);
        ImageView iv = (ImageView) v.findViewById(R.id.image);
        int out=0;
        if(1 + r.nextInt(6)==1)
        {
            iv.setImageResource(R.drawable.dado1);
        }
        else if(1 + r.nextInt(6)==2)
        {
            iv.setImageResource(R.drawable.dado2);
        }
        else if(1 + r.nextInt(6)==3)
        {
            iv.setImageResource(R.drawable.dado3);
        }
        else if(1 + r.nextInt(6)==4)
        {
            iv.setImageResource(R.drawable.dado4);
        }
        else if(1 + r.nextInt(6)==5)
        {
            iv.setImageResource(R.drawable.dado5);
        }
        else if(1 + r.nextInt(6)==6)
        {
            iv.setImageResource(R.drawable.dado6);
        }
        else
        {
            iv.setImageResource(R.drawable.dado6);
        }

        return v;
    }
}
